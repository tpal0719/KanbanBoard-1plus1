package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardUserRepository extends JpaRepository<CardUser, Long> {
}
