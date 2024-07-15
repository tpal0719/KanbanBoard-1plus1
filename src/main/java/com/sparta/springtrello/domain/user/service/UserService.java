package com.sparta.springtrello.domain.user.service;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.common.S3Uploader;
import com.sparta.springtrello.domain.user.dto.*;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.domain.user.entity.UserStatusEnum;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import com.sparta.springtrello.exception.custom.common.UploadException;
import com.sparta.springtrello.exception.custom.user.PasswordException;
import com.sparta.springtrello.exception.custom.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    @Value("${manager-password}")
    private String managerPassword;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(ResponseCodeEnum.USER_NOT_FOUND));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ResponseCodeEnum.USER_NOT_FOUND));
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User createKakaoUser(Long kakaoId, String nickname, String pictureUrl) {
        return userRepository.findByKakaoId(kakaoId).orElseGet(() -> {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            User newUser = new User(kakaoId, "kakao_" + kakaoId, pictureUrl, encodedPassword);
            newUser.setNickname(nickname);
            return userRepository.save(newUser);
        });
    }

    // 회원가입
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        if (existsByUsername(requestDto.getUsername())) {
            throw new UserException(ResponseCodeEnum.USER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(
                requestDto.getUsername(),
                encodedPassword,
                UserStatusEnum.STATUS_NORMAL
        );

        UserRoleEnum role = UserRoleEnum.ROLE_USER;
        if (requestDto.getManagerPassword() != null && !requestDto.getManagerPassword().isEmpty()) {
            if (!managerPassword.equals(requestDto.getManagerPassword())) {
                throw new PasswordException(ResponseCodeEnum.INVALID_MANAGER_PASSWORD);
            }
            role = UserRoleEnum.ROLE_MANAGER;
        }
        user.setUserRole(role);
        save(user);
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(User loginUser, PasswordUpdateRequestDto requestDto) {
        User user = findById(loginUser.getId());

        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new PasswordException(ResponseCodeEnum.PASSWORD_INCORRECT);
        }

        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())) {
            throw new PasswordException(ResponseCodeEnum.SAME_AS_OLD_PASSWORD);
        }

        String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        save(user);
    }

    // 프로필 업로드
    @Transactional
    public void updateProfile(User user, ProfileUpdateRequestDto requestDto, MultipartFile profilePicture) {
        user.setNickname(requestDto.getNickname());
        user.setIntroduce(requestDto.getIntroduce());

        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                String pictureUrl = s3Uploader.upload(profilePicture, "profile-pictures");
                user.setPictureUrl(pictureUrl);
            } catch (IOException e) {
                throw new UploadException(ResponseCodeEnum.UPLOAD_FAILED);
            }
        }
        save(user);
    }

    // 프로필 조회
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(Long userId) {
        User user = findById(userId);
        return new ProfileResponseDto(user.getNickname(), user.getIntroduce(), user.getPictureUrl());
    }

    // 로그아웃
    @Transactional
    public void logout(User user) {
        user.setRefreshToken(null);
        save(user);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(User user, AccountDeleteRequestDto requestDto) {
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new PasswordException(ResponseCodeEnum.PASSWORD_INCORRECT);
        }

        user.setUserStatus(UserStatusEnum.STATUS_DELETED);
        user.setRefreshToken(null);
        save(user);
    }
}