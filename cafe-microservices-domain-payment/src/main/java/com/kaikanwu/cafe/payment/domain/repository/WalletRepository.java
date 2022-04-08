package com.kaikanwu.cafe.payment.domain.repository;

import com.kaikanwu.cafe.payment.domain.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {

    Optional<Wallet> findByAccountId(Integer accountId);

}
