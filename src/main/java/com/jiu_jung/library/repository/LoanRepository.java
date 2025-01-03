package com.jiu_jung.library.repository;

import com.jiu_jung.library.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository  extends JpaRepository<Loan, Long> {
    Optional<Loan> findByBookIdAndUserId(@Param("bookId") long bookId, @Param("userId") long userId);

    @Query("SELECT l FROM Loan l JOIN User u ON l.userId = u.user_id WHERE u.user_id = :userId")
    List<Loan> findByUserId(@Param("userId") Long userId);

    @Query("SELECT l FROM Loan l JOIN Book b ON l.bookId = b.book_id WHERE b.title = :title")
    List<Loan> findByBookTitle(@Param("title") String bookTitle);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    Optional<Loan> findByUserIdAndBookId(Long userId, Long bookId);

    boolean existsByBookId(Long bookId);
}
