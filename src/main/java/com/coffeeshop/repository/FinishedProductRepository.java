package com.coffeeshop.repository;

import com.coffeeshop.entity.FinishedProduct;
import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Repository для готовой продукции.
 */
public class FinishedProductRepository extends GenericRepository<FinishedProduct, Integer> {

    public FinishedProductRepository() { super(FinishedProduct.class); }

    public int batchInsert(List<FinishedProduct> products) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (int i = 0; i < products.size(); i++) {
                em.persist(products.get(i));
                if (i > 0 && i % 25 == 0) {
                    em.flush();
                    em.clear();
                }
            }
            tx.commit();
            return products.size();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
