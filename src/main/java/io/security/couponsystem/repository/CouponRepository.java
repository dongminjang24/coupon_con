package io.security.couponsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.security.couponsystem.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
