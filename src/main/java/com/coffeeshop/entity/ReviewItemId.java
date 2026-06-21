package com.coffeeshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Составной первичный ключ для ReviewItem (review_item: id_review + id_finished_product).
 */
@Embeddable
public class ReviewItemId implements Serializable {

    @Column(name = "id_review")
    private Integer reviewId;

    @Column(name = "id_finished_product")
    private Integer finishedProductId;

    protected ReviewItemId() {}

    public ReviewItemId(Integer reviewId, Integer finishedProductId) {
        this.reviewId = reviewId;
        this.finishedProductId = finishedProductId;
    }

    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) { this.reviewId = reviewId; }
    public Integer getFinishedProductId() { return finishedProductId; }
    public void setFinishedProductId(Integer finishedProductId) { this.finishedProductId = finishedProductId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewItemId r)) return false;
        return Objects.equals(reviewId, r.reviewId) && Objects.equals(finishedProductId, r.finishedProductId);
    }

    @Override
    public int hashCode() { return Objects.hash(reviewId, finishedProductId); }

    @Override
    public String toString() {
        return String.format("ReviewItemId{review=%d, product=%d}", reviewId, finishedProductId);
    }
}
