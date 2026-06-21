package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.util.Objects;

//Плантация, принадлежащая фермеру

@Entity
@Table(name = "plantation")
public class Plantation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_farmer", nullable = false)
    private Farmer farmer;

    protected Plantation() {}

    public Plantation(String region, String name, Farmer farmer) {
        this.region = region;
        this.name = name;
        this.farmer = farmer;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Farmer getFarmer() { return farmer; }
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plantation p)) return false;
        return Objects.equals(name, p.name);
    }

    @Override
    public int hashCode() { return Objects.hashCode(name); }

    @Override
    public String toString() {
        return String.format("Plantation{id=%d, name='%s', region='%s'}", id, name, region);
    }
}
