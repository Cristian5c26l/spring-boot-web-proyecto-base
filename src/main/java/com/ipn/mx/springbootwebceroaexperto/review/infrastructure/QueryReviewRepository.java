package com.ipn.mx.springbootwebceroaexperto.review.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
