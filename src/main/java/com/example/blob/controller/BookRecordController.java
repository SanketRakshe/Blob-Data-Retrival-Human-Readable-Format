package com.example.blob.controller;

import com.example.blob.model.BookRecord;
import com.example.blob.service.BookRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/records")
public class BookRecordController {

 private final BookRecordService bookRecordService;

 public BookRecordController(BookRecordService bookRecordService) {
     this.bookRecordService = bookRecordService;
 }

 /**
  * POST /records
  * Accepts JSON data and saves it as a new record.
  * 
  * Example Request Body:
  * {
  *   "title": "The Adventures of Sherlock Holmes",
  *   "author": {
  *     "firstName": "Arthur",
  *     "lastName": "Conan Doyle"
  *   },
  *   "publicationYear": 1892,
  *   "genres": ["Mystery", "Detective Fiction"],
  *   "availableFormats": ["Hardcover", "Paperback", "E-book"],
  *   "isbn": "978-1-56619-909-4"
  * }
  */
 @PostMapping
 public ResponseEntity<BookRecord> createRecord(@RequestBody String jsonData) {
     BookRecord savedRecord = bookRecordService.saveRecord(jsonData);
     return ResponseEntity.ok(savedRecord);
 }

 /**
  * GET /records/{id}
  * Retrieves a record by ID and returns the JSON data.
  */
 @GetMapping("/{id}")
 public ResponseEntity<String> getRecord(@PathVariable Long id) {
     BookRecord record = bookRecordService.getRecordById(id);
     // Convert the byte[] back to JSON string
     String jsonStr = new String(record.getData(), StandardCharsets.UTF_8);
     return ResponseEntity.ok(jsonStr);
 }

 /**
  * GET /records/{id}/file
  * Writes the JSON data of the specified record to a {id}.json file.
  */
 @GetMapping("/{id}/file")
 public ResponseEntity<String> writeRecordToFile(@PathVariable Long id) {
     try {
         bookRecordService.writeRecordDataToFile(id);
         return ResponseEntity.ok("Data written to file " + id + ".json");
     } catch (IOException e) {
         return ResponseEntity.internalServerError().body("Error writing file: " + e.getMessage());
     }
 }
}