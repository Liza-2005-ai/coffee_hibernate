package com.coffeeshop.service;

import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Бизнес-запросы на JPQL
 */
public class BusinessQueryService {

    // 1. Продукты, на которые пока нет ни одного отзыва (детализации review_item)
    public void productsWithoutReviews() {
        System.out.println("\nПродукты без отзывов:");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT fp.id, fp.name, fp.price
                    FROM FinishedProduct fp
                    WHERE fp.id NOT IN (SELECT ri.finishedProduct.id FROM ReviewItem ri)
                    ORDER BY fp.name
                    """, Object[].class).getResultList();

            if (results.isEmpty()) {
                System.out.println("  (на все продукты уже есть отзывы)");
            }
            for (Object[] row : results) {
                System.out.printf("  id=%d, %s, цена=%s%n", ((Number) row[0]).intValue(), row[1], row[2]);
            }
        }
    }

    // 2. Заполняемость подписок по тарифам — выручка и количество подписчиков на тариф
    public void revenueByRate() {
        System.out.println("\nПодписчики и выручка по тарифам:");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT s.rate.id, s.rate.price, COUNT(s)
                    FROM Subscription s
                    GROUP BY s.rate.id, s.rate.price
                    ORDER BY COUNT(s) DESC
                    """, Object[].class).getResultList();

            for (Object[] row : results) {
                System.out.printf("  тариф id=%d, цена=%s, подписчиков=%d%n",
                        ((Number) row[0]).intValue(), row[1], ((Number) row[2]).longValue());
            }
        }
    }

    // 3. Топ-3 клиента по количеству посылок
    public void topClientsByPackages() {
        System.out.println("\nТоп-3 клиента по количеству посылок:");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT p.subscription.client.surname, p.subscription.client.name, COUNT(p)
                    FROM Package p
                    GROUP BY p.subscription.client.surname, p.subscription.client.name
                    ORDER BY COUNT(p) DESC
                    """, Object[].class)
                    .setMaxResults(3)
                    .getResultList();

            for (Object[] row : results) {
                System.out.printf("  %s %s, посылок=%d%n", row[0], row[1], ((Number) row[2]).longValue());
            }
        }
    }

    // 4. Средний рейтинг по продуктам (review_item)
    public void avgGradeByProduct() {
        System.out.println("\nСредний рейтинг отзывов по продуктам:");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT ri.finishedProduct.name, COUNT(ri), AVG(ri.grade)
                    FROM ReviewItem ri
                    GROUP BY ri.finishedProduct.name
                    ORDER BY AVG(ri.grade) DESC
                    """, Object[].class).getResultList();

            for (Object[] row : results) {
                double avgGrade = ((Number) row[2]).doubleValue();
                System.out.printf("  %s, отзывов=%d, средний балл=%.2f%n",
                        row[0], ((Number) row[1]).longValue(), avgGrade);
            }
        }
    }

    // 5. Фермеры, у которых есть хотя бы одна плантация
    public void farmersWithPlantations() {
        System.out.println("\nФермеры, у которых есть плантации:");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT f.id, f.name, f.country
                    FROM Farmer f
                    WHERE EXISTS (SELECT 1 FROM Plantation pl WHERE pl.farmer = f)
                    ORDER BY f.name
                    """, Object[].class).getResultList();

            for (Object[] row : results) {
                System.out.printf("  id=%d, %s, %s%n", ((Number) row[0]).intValue(), row[1], row[2]);
            }
        }
    }

    public void runAll() {
        productsWithoutReviews();
        revenueByRate();
        topClientsByPackages();
        avgGradeByProduct();
        farmersWithPlantations();
    }
}
