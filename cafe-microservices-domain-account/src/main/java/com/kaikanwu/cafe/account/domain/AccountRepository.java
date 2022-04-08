package com.kaikanwu.cafe.account.domain;

import com.kaikanwu.cafe.infrastructure.domain.account.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AccountRepository extends CrudRepository<Account, Integer> {

//    @Override
//    Iterable<Account> findAll();
//
//    @Cacheable(key = "#username")
    Account findByUsername(String username);


    /**
     * 唯一性判断
     */
    boolean existsByUsernameOrEmailOrTelephone(String username, String email, String telephone);

//    @Cacheable(key = "#username")
    boolean existsByUsername(String username);

    /**
     * 根据参数获取对应账号
     */
    Collection<Account> findByUsernameOrEmailOrTelephone(String username, String email, String telephone);
}

