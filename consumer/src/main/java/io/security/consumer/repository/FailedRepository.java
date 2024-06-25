package io.security.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.security.consumer.domain.FailedEvent;

public interface FailedRepository extends JpaRepository<FailedEvent,Long> {
}
