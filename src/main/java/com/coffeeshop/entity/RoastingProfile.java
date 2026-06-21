package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Профиль обжарки кофейных зёрен.
 * temperature — температура обжарки, time — продолжительность обжарки.
 */
@Entity
@Table(name = "roasting_profile")
public class RoastingProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 10, scale = 2)
    private BigDecimal temperature;

    @Column(name = "time")
    private LocalTime time;

    @Column(nullable = false, length = 100)
    private String name;

    protected RoastingProfile() {}

    public RoastingProfile(BigDecimal temperature, LocalTime time, String name) {
        this.temperature = temperature;
        this.time = time;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public BigDecimal getTemperature() { return temperature; }
    public void setTemperature(BigDecimal temperature) { this.temperature = temperature; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoastingProfile r)) return false;
        return Objects.equals(name, r.name);
    }

    @Override
    public int hashCode() { return Objects.hashCode(name); }

    @Override
    public String toString() {
        return String.format("RoastingProfile{id=%d, name='%s', temp=%s°C, time=%s}", id, name, temperature, time);
    }
}
