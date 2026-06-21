package com.coffeeshop.repository;

import com.coffeeshop.entity.Rate;

/** Repository для тарифа — чистый CRUD из GenericRepository. */
public class RateRepository extends GenericRepository<Rate, Integer> {
    public RateRepository() { super(Rate.class); }
}
