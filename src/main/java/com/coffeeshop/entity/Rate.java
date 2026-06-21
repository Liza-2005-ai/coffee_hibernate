package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Тариф подписки на кофе
 * volume — объём кофе за раз (кг), price — цена тарифа, frequency — периодичность доставки в днях.
 */
@Entity
@Table(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal volume;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer frequency;

    protected Rate() {}

    public Rate(BigDecimal volume, BigDecimal price, Integer frequency) {
        this.volume = volume;
        this.price = price;
        this.frequency = frequency;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public BigDecimal getVolume() { return volume; }
    public void setVolume(BigDecimal volume) { this.volume = volume; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getFrequency() { return frequency; }
    public void setFrequency(Integer frequency) { this.frequency = frequency; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rate r)) return false;
        return Objects.equals(id, r.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Rate{id=%d, volume=%s kg, price=%s, frequency=%d days}",
                id, volume, price, frequency);
    }
}
