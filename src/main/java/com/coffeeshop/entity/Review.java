package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


 //Отзыв клиента на посылку

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_package", nullable = false)
    private Package aPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    protected Review() {}

    public Review(String comment, BigDecimal grade, Package aPackage, Client client) {
        this.comment = comment;
        this.grade = grade;
        this.aPackage = aPackage;
        this.client = client;
    }

    @PrePersist
    private void onCreate() {
        if (date == null) date = LocalDate.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
    public Package getPackage() { return aPackage; }
    public void setPackage(Package aPackage) { this.aPackage = aPackage; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review r)) return false;
        return Objects.equals(id, r.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Review{id=%d, date=%s, grade=%s, comment='%s'}", id, date, grade, comment);
    }
}
