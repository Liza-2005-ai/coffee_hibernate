package com.coffeeshop.repository;

import com.coffeeshop.entity.Farmer;

/** Repository для фермера . */
public class FarmerRepository extends GenericRepository<Farmer, Integer> {
    public FarmerRepository() { super(Farmer.class); }
}
