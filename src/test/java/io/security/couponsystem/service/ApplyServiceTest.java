package io.security.couponsystem.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import io.security.couponsystem.repository.CouponRepository;

@SpringBootTest
class ApplyServiceTest {
	@Autowired
	private ApplyService applyService;

	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	// @BeforeEach
	// public void setUp() {
	// 	// Redis 초기화
	// 	redisTemplate.delete("coupon_count");
	// }

	@Test
	public void 한번만응모() {
		applyService.apply(1L);

		long count = couponRepository.count();

		assertThat(count).isEqualTo(1);
	}

	@Test
	public void 여러번응모() throws InterruptedException {
		int threadCount = 1000;

		//ExecutorService : 병렬 작업을 간단하게 할 수 있게 도와주는 Java API
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		// CountDownLatch : 다른 Thread에서 수행하는 작업을 기다리도록 도와주는 클래스
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i=0; i<threadCount; i++) {
			long userId = i;
			executorService.submit(() -> {
				try {
					applyService.apply(userId);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Thread.sleep(10000);
		long count = couponRepository.count();

		assertThat(count).isEqualTo(100);
	}

	@Test
	public void 한명당_한개의_쿠폰만_발급() throws InterruptedException {
		int threadCount = 1000;

		//ExecutorService : 병렬 작업을 간단하게 할 수 있게 도와주는 Java API
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		// CountDownLatch : 다른 Thread에서 수행하는 작업을 기다리도록 도와주는 클래스
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i=0; i<threadCount; i++) {
			long userId = i;
			executorService.submit(() -> {
				try {
					applyService.apply(1L);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Thread.sleep(10000);
		long count = couponRepository.count();

		assertThat(count).isEqualTo(1);
	}
}