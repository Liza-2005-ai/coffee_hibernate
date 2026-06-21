package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

//Готовый продукт — упаковка обжаренного кофе конкретного сорта

@Entity
@Table(name = "finished_product")
public class FinishedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variety", nullable = false)
    private GrainVariety variety;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_roasting_profile", nullable = false)
    private RoastingProfile roastingProfile;

    protected FinishedProduct() {}

    public FinishedProduct(BigDecimal weight, BigDecimal price, String name,
                            GrainVariety variety, RoastingProfile roastingProfile) {
        this.weight = weight;
        this.price = price;
        this.name = name;
        this.variety = variety;
        this.roastingProfile = roastingProfile;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public GrainVariety getVariety() { return variety; }
    public void setVariety(GrainVariety variety) { this.variety = variety; }
    public RoastingProfile getRoastingProfile() { return roastingProfile; }
    public void setRoastingProfile(RoastingProfile roastingProfile) { this.roastingProfile = roastingProfile; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinishedProduct f)) return false;
        return Objects.equals(name, f.name);
    }

    @Override
    public int hashCode() { return Objects.hashCode(name); }

    @Override
    public String toString() {
        return String.format("FinishedProduct{id=%d, name='%s', weight=%s, price=%s}", id, name, weight, price);
    }
}
