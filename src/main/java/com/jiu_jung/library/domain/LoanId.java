package com.jiu_jung.library.domain;

import java.io.Serializable;
import java.util.Objects;


public class LoanId implements Serializable {
    private Long bookId;
    private Long userId;

    public LoanId() {}

    public LoanId(Long bookId, Long userId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanId loanId = (LoanId) o;
        return Objects.equals(bookId, loanId.bookId) && Objects.equals(userId, loanId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userId);
    }
}