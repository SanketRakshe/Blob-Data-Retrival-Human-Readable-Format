package com.example.blob.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRecord {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 // Store date-time as TIMESTAMP in DB, format it on JSON output.
 @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SS")
 @Column(name = "record_timestamp", nullable = false)
 private LocalDateTime recordTimestamp;

 // Storing JSON as BLOB
 @Lob
 @Column(name = "data_blob", columnDefinition = "BLOB", nullable = false)
 private byte[] data;

 // Convenience constructor for service layer
 public BookRecord(LocalDateTime recordTimestamp, byte[] data) {
     this.recordTimestamp = recordTimestamp;
     this.data = data;
 }
}