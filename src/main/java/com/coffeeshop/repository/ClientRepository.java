package com.coffeeshop.repository;

import com.coffeeshop.entity.Client;
import com.coffeeshop.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

/**
 * Repository для клиента
 */
public class ClientRepository extends GenericRepository<Client, Integer> {

    public ClientRepository() { super(Client.class); }

    /** Найти клиента по email (email уникален в схеме). */
    public Optional<Client> findByEmail(String email) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Client> result = em.createQuery(
                    "FROM Client c WHERE c.emailAddress = :email", Client.class)
                    .setParameter("email", email)
                    .getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    /** Клиент со всеми его вкусовыми предпочтениями за 1 запрос (JOIN FETCH, решение N+1). */
    public Optional<Client> findByIdWithPreferences(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Client> result = em.createQuery(
                    "SELECT c FROM Client c LEFT JOIN FETCH c.preferences WHERE c.id = :id", Client.class)
                    .setParameter("id", id)
                    .getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }
}
