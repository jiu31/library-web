package com.jiu_jung.library.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "loan")
@IdClass(LoanId.class)
public class Loan {
    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Loan() {}

    public Loan(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
