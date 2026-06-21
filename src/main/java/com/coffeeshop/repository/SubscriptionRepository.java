package com.coffeeshop.repository;

import com.coffeeshop.entity.Client;
import com.coffeeshop.entity.Package;
import com.coffeeshop.entity.Subscription;
import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository для подписки
 */
public class SubscriptionRepository extends GenericRepository<Subscription, Integer> {

    public SubscriptionRepository() { super(Subscription.class); }

    public List<Subscription> findByClient(int clientId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Subscription s WHERE s.client.id = :clientId ORDER BY s.date DESC",
                    Subscription.class).setParameter("clientId", clientId).getResultList();
        }
    }

    public int activateSubscriptionWithFirstPackage(int subscriptionId, String trecNumber) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Subscription subscription = em.find(Subscription.class, subscriptionId);
            if (subscription == null) {
                tx.rollback();
                throw new IllegalArgumentException("Подписка id=" + subscriptionId + " не найдена");
            }

            subscription.setStatus("active"); // dirty checking — Hibernate сам сгенерирует UPDATE при commit

            Package firstPackage = new Package(
                    LocalDateTime.now(), "Первая посылка по подписке", trecNumber, "pending", subscription);
            em.persist(firstPackage);

            tx.commit();
            return firstPackage.getId();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
