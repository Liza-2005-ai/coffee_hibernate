package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.util.Objects;

//Фермер, поставляющий кофейные зёрна.

@Entity
@Table(name = "farmer")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, unique = true, length = 100)
    private String number;

    @Column(nullable = false, length = 100)
    private String name;

    protected Farmer() {}

    public Farmer(String country, String number, String name) {
        this.country = country;
        this.number = number;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Farmer f)) return false;
        return Objects.equals(number, f.number);
    }

    @Override
    public int hashCode() { return Objects.hashCode(number); }

    @Override
    public String toString() { return String.format("Farmer{id=%d, name='%s', country='%s'}", id, name, country); }
}
