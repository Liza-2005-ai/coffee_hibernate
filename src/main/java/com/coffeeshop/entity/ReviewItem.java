package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

//Детализация отзыва по конкретному продукту

@Entity
@Table(name = "review_item")
public class ReviewItem {

    @EmbeddedId
    private ReviewItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "id_review")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("finishedProductId")
    @JoinColumn(name = "id_finished_product")
    private FinishedProduct finishedProduct;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal grade;

    @Column(columnDefinition = "TEXT")
    private String comment;

    protected ReviewItem() {}

    public ReviewItem(Review review, FinishedProduct finishedProduct, BigDecimal grade, String comment) {
        this.id = new ReviewItemId(review.getId(), finishedProduct.getId());
        this.review = review;
        this.finishedProduct = finishedProduct;
        this.grade = grade;
        this.comment = comment;
    }

    public ReviewItemId getId() { return id; }
    public void setId(ReviewItemId id) { this.id = id; }
    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }
    public FinishedProduct getFinishedProduct() { return finishedProduct; }
    public void setFinishedProduct(FinishedProduct finishedProduct) { this.finishedProduct = finishedProduct; }
    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewItem r)) return false;
        return Objects.equals(id, r.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("ReviewItem{%s, grade=%s}", id, grade);
    }
}
