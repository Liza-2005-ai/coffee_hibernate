package com.coffeeshop.entity;

import jakarta.persistence.*;
import java.util.Objects;

//Состав посылки

@Entity
@Table(name = "package_composition")
public class PackageComposition {

    @EmbeddedId
    private PackageCompositionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("packageId")
    @JoinColumn(name = "id_package")
    private Package aPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("finishedProductId")
    @JoinColumn(name = "id_finished_product")
    private FinishedProduct finishedProduct;

    @Column(nullable = false)
    private Integer quantity;

    protected PackageComposition() {}

    public PackageComposition(Package aPackage, FinishedProduct finishedProduct, Integer quantity) {
        this.id = new PackageCompositionId(aPackage.getId(), finishedProduct.getId());
        this.aPackage = aPackage;
        this.finishedProduct = finishedProduct;
        this.quantity = quantity;
    }

    public PackageCompositionId getId() { return id; }
    public void setId(PackageCompositionId id) { this.id = id; }
    public Package getPackage() { return aPackage; }
    public void setPackage(Package aPackage) { this.aPackage = aPackage; }
    public FinishedProduct getFinishedProduct() { return finishedProduct; }
    public void setFinishedProduct(FinishedProduct finishedProduct) { this.finishedProduct = finishedProduct; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PackageComposition p)) return false;
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("PackageComposition{%s, quantity=%d}", id, quantity);
    }
}
