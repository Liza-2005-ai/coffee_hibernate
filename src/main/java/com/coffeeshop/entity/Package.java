package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

//Посылка, отправленная клиенту по подписке

@Entity
@Table(name = "package")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "package_composition", nullable = false, columnDefinition = "TEXT")
    private String packageComposition;

    @Column(name = "trec_number", nullable = false, length = 50)
    private String trecNumber;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subscription", nullable = false)
    private Subscription subscription;

    protected Package() {}

    public Package(LocalDateTime date, String packageComposition, String trecNumber,
                   String status, Subscription subscription) {
        this.date = date;
        this.packageComposition = packageComposition;
        this.trecNumber = trecNumber;
        this.status = status;
        this.subscription = subscription;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getPackageComposition() { return packageComposition; }
    public void setPackageComposition(String packageComposition) { this.packageComposition = packageComposition; }
    public String getTrecNumber() { return trecNumber; }
    public void setTrecNumber(String trecNumber) { this.trecNumber = trecNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Subscription getSubscription() { return subscription; }
    public void setSubscription(Subscription subscription) { this.subscription = subscription; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Package p)) return false;
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Package{id=%d, date=%s, trec='%s', status='%s'}", id, date, trecNumber, status);
    }
}
