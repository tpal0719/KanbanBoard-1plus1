package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
}
