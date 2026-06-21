package com.coffeeshop.repository;

import com.coffeeshop.entity.Plantation;
import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/** Repository для плантаций. */
public class PlantationRepository extends GenericRepository<Plantation, Integer> {

    public PlantationRepository() { super(Plantation.class); }

    public List<Plantation> findByFarmer(int farmerId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Plantation p WHERE p.farmer.id = :farmerId ORDER BY p.id",
                    Plantation.class).setParameter("farmerId", farmerId).getResultList();
        }
    }
}
