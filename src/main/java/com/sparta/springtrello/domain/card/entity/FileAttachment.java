package com.sparta.springtrello.domain.card.entity;

import com.sparta.springtrello.common.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "file_attachments")
public class FileAttachment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String fileUrl;

    @Column
    private String fileDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Builder
    public FileAttachment(String fileUrl, String fileDescription, Card card) {
        this.fileUrl = fileUrl;
        this.fileDescription = fileDescription;
        this.card = card;
    }
}
