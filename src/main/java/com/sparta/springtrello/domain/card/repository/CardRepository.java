package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c JOIN FETCH c.taskColumn tc JOIN FETCH tc.board WHERE tc.board.id = :boardId")
    List<Card> findAllByBoardId(Long boardId);

    @Query("SELECT c FROM Card c JOIN FETCH c.taskColumn WHERE c.taskColumn.id = :columnId")
    List<Card> findAllByColumnId(Long columnId);

    @Query("SELECT c FROM Card c JOIN FETCH c.cardUsers cu JOIN FETCH cu.user WHERE cu.user.id = :userId")
    List<Card> findAllByUserId(Long userId);
}
