package com.example.blob.service;

import com.example.blob.model.BookRecord;
import com.example.blob.repository.BookRecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class BookRecordService {

 private final BookRecordRepository bookRecordRepository;
 private final ObjectMapper objectMapper;

 public BookRecordService(BookRecordRepository bookRecordRepository, ObjectMapper objectMapper) {
     this.bookRecordRepository = bookRecordRepository;
     this.objectMapper = objectMapper;
 }

 /**
  * Saves a new BookRecord with the current timestamp and provided JSON data.
  * @param jsonData JSON data as a String
  * @return Saved BookRecord entity
  */
 public BookRecord saveRecord(String jsonData) {
     // Convert JSON string to bytes using UTF-8 encoding
     byte[] dataBytes = jsonData.getBytes(StandardCharsets.UTF_8);
     BookRecord record = new BookRecord(LocalDateTime.now(), dataBytes);
     return bookRecordRepository.save(record);
 }

 /**
  * Retrieves a BookRecord by its ID.
  * @param id ID of the record
  * @return BookRecord entity
  */
 public BookRecord getRecordById(Long id) {
     return bookRecordRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
 }

 /**
  * Writes the JSON data of a record to a file named {id}.json.
  * @param id ID of the record
  * @throws IOException If file writing fails
  */
 public void writeRecordDataToFile(Long id) throws IOException {
     BookRecord record = getRecordById(id);
     byte[] blobData = record.getData();
     String jsonStr = new String(blobData, StandardCharsets.UTF_8);
     try (FileWriter fileWriter = new FileWriter(id + ".json")) {
         fileWriter.write(jsonStr);
     }
 }
}
