package com.jiu_jung.library.service;

import com.jiu_jung.library.domain.Book;
import com.jiu_jung.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookByTitle(String title){
        return bookRepository.findByTitle(title);
    }

    public Optional<Book> getBookByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long book_id, Book updatedBook) {
        return bookRepository.findById(book_id)
                .map(existingBook -> {
                    existingBook.setTitle(updatedBook.getTitle());
                    existingBook.setAuthor(updatedBook.getAuthor());
                    return bookRepository.save(existingBook);
                });
    }

    public boolean deleteBookById(Long book_id) {
        if (bookRepository.existsById(book_id)) {
            bookRepository.deleteById(book_id);
            return true;
        }
        return false;
    }

}
