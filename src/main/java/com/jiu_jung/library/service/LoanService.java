package com.jiu_jung.library.service;

import com.jiu_jung.library.domain.Loan;
import com.jiu_jung.library.dto.LoanResponse;
import com.jiu_jung.library.repository.BookRepository;
import com.jiu_jung.library.repository.LoanRepository;
import com.jiu_jung.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Loan loanBook(Long userId, Long bookId) {
        // Check if the user exists
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("User with ID " + userId + " does not exist.");
        }

        // Check if the book exists
        if (!bookRepository.existsById(bookId)) {
            throw new IllegalStateException("Book with ID " + bookId + " does not exist.");
        }

        // Check if the loan exists
        boolean isAlreadyLoaned = loanRepository.existsByBookId(bookId);
        if (isAlreadyLoaned) {
            throw new IllegalStateException("This book is already loaned.");
        }

        Loan loan = new Loan(bookId, userId);
        return loanRepository.save(loan);
    }

    public void returnBook(Long userId, Long bookId) {
        // Check if the user exists
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("User with ID " + userId + " does not exist.");
        }

        // Check if the book exists
        if (!bookRepository.existsById(bookId)) {
            throw new IllegalStateException("Book with ID " + bookId + " does not exist.");
        }

        // Check if the loan exists
        Optional<Loan> loan = loanRepository.findByUserIdAndBookId(userId, bookId);
        if (loan.isPresent()) {
            loanRepository.delete(loan.get());
        } else {
            throw new IllegalStateException("No loan record found for the given user and book.");
        }
    }

    public List<LoanResponse> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(loan -> {
            var user = userRepository.findById(loan.getUserId()).orElseThrow();
            var book = bookRepository.findById(loan.getBookId()).orElseThrow();
            return new LoanResponse(
                    loan.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    loan.getBookId(),
                    book.getTitle(),
                    book.getAuthor()
            );
        }).collect(Collectors.toList());
    }

    public List<Loan> getLoansByUser(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public List<Loan> getLoansByTitle(String title) {
        return loanRepository.findByBookTitle(title);
    }

    public boolean checkLoanExists(Long userId, Long bookId) {
        return loanRepository.existsByUserIdAndBookId(userId, bookId);
    }
}