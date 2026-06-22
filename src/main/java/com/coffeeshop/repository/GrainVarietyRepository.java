package com.coffeeshop.repository;

import com.coffeeshop.entity.GrainVariety;
import com.coffeeshop.entity.TasteDescriptor;
import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

/**
 * Repository для сорта зерна.
 */
public class GrainVarietyRepository extends GenericRepository<GrainVariety, Integer> {

    public GrainVarietyRepository() { super(GrainVariety.class); }

    public List<GrainVariety> findAllWithTasteDescriptors() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT v FROM GrainVariety v LEFT JOIN FETCH v.tasteDescriptors ORDER BY v.id",
                    GrainVariety.class).getResultList();
        }
    }

    public Optional<GrainVariety> findByIdWithTasteDescriptors(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<GrainVariety> result = em.createQuery(
                    "SELECT v FROM GrainVariety v LEFT JOIN FETCH v.tasteDescriptors WHERE v.id = :id",
                    GrainVariety.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public void addTasteDescriptor(int varietyId, int descriptorId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            GrainVariety variety = em.find(GrainVariety.class, varietyId);
            TasteDescriptor descriptor = em.find(TasteDescriptor.class, descriptorId);
            if (variety != null && descriptor != null) variety.addTasteDescriptor(descriptor);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void removeTasteDescriptor(int varietyId, int descriptorId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            GrainVariety variety = em.find(GrainVariety.class, varietyId);
            TasteDescriptor descriptor = em.find(TasteDescriptor.class, descriptorId);
            if (variety != null && descriptor != null) variety.removeTasteDescriptor(descriptor);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
