package io.security.consumer.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import io.security.consumer.domain.Coupon;
import io.security.consumer.domain.FailedEvent;
import io.security.consumer.repository.CouponRepository;
import io.security.consumer.repository.FailedRepository;


@Component
public class CouponCreatedConsumer {

	private final CouponRepository couponRepository;

	private final FailedRepository failedRepository;

	private final Logger logger = LoggerFactory.getLogger(CouponCreatedConsumer.class);

	public CouponCreatedConsumer(CouponRepository couponRepository, FailedRepository failedRepository) {
		this.couponRepository = couponRepository;
		this.failedRepository = failedRepository;
	}

	@KafkaListener(topics = "coupon_create",groupId = "group_1")
	public void listener(Long userId) {
		try {
			couponRepository.save(new Coupon(userId));
		} catch (Exception e) {
			logger.error("failed to create coupon::" + userId);
			failedRepository.save(new FailedEvent(userId));
		}

	}
}
