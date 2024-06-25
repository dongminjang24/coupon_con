package io.security.consumer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	public Coupon() {
	}

	public Coupon(Long id, Long userId) {
		this.id = id;
		this.userId = userId;
	}

	public Coupon(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
}