package com.coffeeshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

//Составной первичный ключ для PackageComposition (package_composition: id_package + id_finished_product).

@Embeddable
public class PackageCompositionId implements Serializable {

    @Column(name = "id_package")
    private Integer packageId;

    @Column(name = "id_finished_product")
    private Integer finishedProductId;

    protected PackageCompositionId() {}

    public PackageCompositionId(Integer packageId, Integer finishedProductId) {
        this.packageId = packageId;
        this.finishedProductId = finishedProductId;
    }

    public Integer getPackageId() { return packageId; }
    public void setPackageId(Integer packageId) { this.packageId = packageId; }
    public Integer getFinishedProductId() { return finishedProductId; }
    public void setFinishedProductId(Integer finishedProductId) { this.finishedProductId = finishedProductId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PackageCompositionId p)) return false;
        return Objects.equals(packageId, p.packageId) && Objects.equals(finishedProductId, p.finishedProductId);
    }

    @Override
    public int hashCode() { return Objects.hash(packageId, finishedProductId); }

    @Override
    public String toString() {
        return String.format("PackageCompositionId{package=%d, product=%d}", packageId, finishedProductId);
    }
}
