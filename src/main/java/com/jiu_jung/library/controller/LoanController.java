package com.jiu_jung.library.controller;

import com.jiu_jung.library.domain.Loan;
import com.jiu_jung.library.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // GET: Get all loans
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    // GET: Get loan by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable("userId") Long userId) {
        List<Loan> loans = loanService.getLoansByUser(userId);
        return ResponseEntity.ok(loans);
    }

    // GET: Get loan by book title
    @GetMapping("/book/{title}")
    public ResponseEntity<List<Loan>> getLoansByTitle(@PathVariable("title") String title) {
        List<Loan> loans = loanService.getLoansByTitle(title);
        return ResponseEntity.ok(loans);
    }

    // POST: Create a new loan
    @PostMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<Loan> loanBook(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId) {
        Loan newLoan = loanService.loanBook(bookId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
    }

    // DELETE: Return a book by userId and bookId
    @DeleteMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId) {
        boolean loanExists = loanService.checkLoanExists(userId, bookId);

        if (loanExists) {
            boolean deleted = loanService.returnBook(userId, bookId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
