package com.jiu_jung.library.controller;

import com.jiu_jung.library.domain.Book;
import com.jiu_jung.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET: Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // GET: Get book by book ID
    @GetMapping("/{book_id}")
    public ResponseEntity<Book> getBookById(@PathVariable("book_id") Long book_id) {
        return bookService.getBookById(book_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET: Get book by book title
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable("title") String title) {
        return bookService.getBookByTitle(title)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET: Get book by book author
    @GetMapping("/author/{author}")
    public ResponseEntity<Book> getBookByAuthor(@PathVariable("author") String author) {
        return bookService.getBookByAuthor(author)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    // PUT: Update an existing bok
    @PutMapping("/{book_id}")
    public ResponseEntity<Book> updateBook(@PathVariable("book_id") Long book_id, @RequestBody Book book) {
        Optional<Book> updatedBook = bookService.updateBook(book_id, book);
        return updatedBook.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETE: delete a book by book ID
    @DeleteMapping("/{book_id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("book_id") Long book_id) {
        if (bookService.deleteBookById(book_id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
