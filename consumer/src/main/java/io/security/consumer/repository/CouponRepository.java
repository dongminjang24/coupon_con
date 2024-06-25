package io.security.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.security.consumer.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
