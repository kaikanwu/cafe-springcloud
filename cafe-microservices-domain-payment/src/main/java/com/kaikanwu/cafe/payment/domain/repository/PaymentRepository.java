package com.kaikanwu.cafe.payment.domain.repository;

import com.kaikanwu.cafe.payment.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    Payment getByPayId(String payId);
}
