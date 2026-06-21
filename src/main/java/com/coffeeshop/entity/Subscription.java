package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Подписка клиента на тариф (таблица subscription).
 * status принимает одно из значений: 'not_active', 'active', 'expired'.
 */
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, length = 100)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rate", nullable = false)
    private Rate rate;

    protected Subscription() {}

    public Subscription(LocalDateTime date, String status, Client client, Rate rate) {
        this.date = date;
        this.status = status;
        this.client = client;
        this.rate = rate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Rate getRate() { return rate; }
    public void setRate(Rate rate) { this.rate = rate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription s)) return false;
        return Objects.equals(id, s.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Subscription{id=%d, date=%s, status='%s'}", id, date, status);
    }
}
