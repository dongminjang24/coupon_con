package io.security.couponsystem.service;

import org.springframework.stereotype.Service;

import io.security.couponsystem.domain.Coupon;
import io.security.couponsystem.producer.CouponCreateProducer;
import io.security.couponsystem.repository.AppliedUserRepository;
import io.security.couponsystem.repository.CouponCountRepository;
import io.security.couponsystem.repository.CouponRepository;
import jakarta.transaction.Transactional;
@Service
public class ApplyService {
	private final CouponRepository couponRepository;
	private final CouponCountRepository couponCountRepository;
	private final CouponCreateProducer couponCreateProducer;
	private final AppliedUserRepository appliedUserRepository;
	public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository,
		CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
		this.couponRepository = couponRepository;
		this.couponCountRepository = couponCountRepository;
		this.couponCreateProducer = couponCreateProducer;
		this.appliedUserRepository = appliedUserRepository;
	}

	public void apply(Long userId) {
		Long add = appliedUserRepository.add(userId);
		if (add != 1) {
			return;
		}
		// lock start
		// 쿠폰 발급 여부
		// if(발급됐다면) return
		Long count = couponCountRepository.increment();
		System.out.println("count : "+count);
		// long count = couponRepository.count();

		if (count > 100) {
			return;
		}

		// Coupon save = couponRepository.save(new Coupon(userId));
		// System.out.println("save! : "+save.getId());
		couponCreateProducer.create(userId);

		// 쿠폰 발급
		// lock end  lock 이렇게 되면 lock 범위가 너무 넓어짐
	}
}