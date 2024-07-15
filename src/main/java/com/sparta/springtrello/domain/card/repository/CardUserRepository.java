package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CardUserRepository extends JpaRepository<CardUser, Long> {

    Optional<CardUser> findByCardIdAndUserId(Long cardId, Long userId);

}
