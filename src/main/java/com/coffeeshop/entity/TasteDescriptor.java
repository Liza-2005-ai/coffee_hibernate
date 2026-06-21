package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Вкусовой дескриптор кофе,
 * например "Шоколад", "Цитрус" с категорией "вкус", "кислотность" и т.д.
 */
@Entity
@Table(name = "taste_descriptor")
public class TasteDescriptor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String category;

    protected TasteDescriptor() {}

    public TasteDescriptor(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TasteDescriptor t)) return false;
        return Objects.equals(name, t.name);
    }

    @Override
    public int hashCode() { return Objects.hashCode(name); }

    @Override
    public String toString() {
        return String.format("TasteDescriptor{id=%d, name='%s', category='%s'}", id, name, category);
    }
}
