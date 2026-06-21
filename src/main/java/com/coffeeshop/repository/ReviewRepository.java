package com.coffeeshop.repository;

import com.coffeeshop.entity.Review;
import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/** Repository для отзывов. */
public class ReviewRepository extends GenericRepository<Review, Integer> {

    public ReviewRepository() { super(Review.class); }

    public List<Review> findByClient(int clientId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Review r WHERE r.client.id = :clientId ORDER BY r.date DESC",
                    Review.class).setParameter("clientId", clientId).getResultList();
        }
    }
}
