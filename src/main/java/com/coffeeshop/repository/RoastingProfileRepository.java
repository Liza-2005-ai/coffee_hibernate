package com.coffeeshop.repository;

import com.coffeeshop.entity.RoastingProfile;

/** Repository для профиля обжарки  */
public class RoastingProfileRepository extends GenericRepository<RoastingProfile, Integer> {
    public RoastingProfileRepository() { super(RoastingProfile.class); }
}
