package com.coffeeshop.repository;

import com.coffeeshop.entity.Package;
import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/** Repository для посылок. */
public class PackageRepository extends GenericRepository<Package, Integer> {

    public PackageRepository() { super(Package.class); }

    public List<Package> findBySubscription(int subscriptionId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Package p WHERE p.subscription.id = :subId ORDER BY p.date DESC",
                    Package.class).setParameter("subId", subscriptionId).getResultList();
        }
    }


    public List<Package> findByClientId(int clientId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Package p JOIN FETCH p.subscription s JOIN FETCH s.client " +
                            "WHERE s.client.id = :clientId ORDER BY p.date DESC",
                    Package.class).setParameter("clientId", clientId).getResultList();
        }
    }

    public boolean updateStatus(int id, String newStatus) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Package pkg = em.find(Package.class, id);
            if (pkg == null) {
                tx.rollback();
                return false;
            }
            pkg.setStatus(newStatus);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
