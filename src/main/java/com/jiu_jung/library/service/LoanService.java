package com.jiu_jung.library.service;

import com.jiu_jung.library.domain.Book;
import com.jiu_jung.library.domain.Loan;
import com.jiu_jung.library.domain.User;
import com.jiu_jung.library.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan loanBook(Long userId, Long bookId) {
        Optional<Loan> existingLoan = loanRepository.findByBookIdAndUserId(bookId, userId);
        if (existingLoan.isPresent()) {
            throw new IllegalStateException("This book is already loaned.");
        }

        Loan loan = new Loan();
        loan.setBookId(bookId);
        loan.setUserId(userId);

        return loanRepository.save(loan);
    }

    public boolean returnBook(Long userId, Long bookId) {
        Optional<Loan> loan = loanRepository.findByUserIdAndBookId(userId, bookId);
        if (loan.isPresent()) {
            loanRepository.delete(loan.get());
        } else {
            throw new IllegalStateException("No loan record found for the given user and book.");
        }
        return false;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
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