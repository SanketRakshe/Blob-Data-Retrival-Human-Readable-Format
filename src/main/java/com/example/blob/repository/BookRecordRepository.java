package com.example.blob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blob.model.BookRecord;

public interface BookRecordRepository extends JpaRepository<BookRecord, Long> {
}