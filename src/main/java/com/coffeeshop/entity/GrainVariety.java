package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.util.*;

//Сорт кофейного зерна

@Entity
@Table(name = "grain_variety")
public class GrainVariety {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "taste_of_the_variety",
            joinColumns = @JoinColumn(name = "id_variety"),
            inverseJoinColumns = @JoinColumn(name = "id_descriptor")
    )
    private Set<TasteDescriptor> tasteDescriptors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "plantation_varieties",
            joinColumns = @JoinColumn(name = "id_variety"),
            inverseJoinColumns = @JoinColumn(name = "id_plantation")
    )
    private Set<Plantation> plantations = new HashSet<>();

    protected GrainVariety() {}

    public GrainVariety(String name) {
        this.name = name;
    }

    public void addTasteDescriptor(TasteDescriptor descriptor) { tasteDescriptors.add(descriptor); }
    public void removeTasteDescriptor(TasteDescriptor descriptor) { tasteDescriptors.remove(descriptor); }
    public void addPlantation(Plantation plantation) { plantations.add(plantation); }
    public void removePlantation(Plantation plantation) { plantations.remove(plantation); }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<TasteDescriptor> getTasteDescriptors() { return tasteDescriptors; }
    public void setTasteDescriptors(Set<TasteDescriptor> tasteDescriptors) { this.tasteDescriptors = tasteDescriptors; }
    public Set<Plantation> getPlantations() { return plantations; }
    public void setPlantations(Set<Plantation> plantations) { this.plantations = plantations; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrainVariety g)) return false;
        return Objects.equals(name, g.name);
    }

    @Override
    public int hashCode() { return Objects.hashCode(name); }

    @Override
    public String toString() { return String.format("GrainVariety{id=%d, name='%s'}", id, name); }
}
