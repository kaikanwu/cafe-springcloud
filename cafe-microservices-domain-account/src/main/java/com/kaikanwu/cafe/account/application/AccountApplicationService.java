package com.kaikanwu.cafe.account.application;


import com.kaikanwu.cafe.account.domain.AccountRepository;
import com.kaikanwu.cafe.infrastructure.domain.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountApplicationService {

    @Autowired
    private AccountRepository repository;


    /**
     * 创建账号
     */
    public void createAccount(Account account) {
        log.info("new user created: {}", account);
        account.setPassword(account.getPassword());
        repository.save(account);
    }

    /**
     * 根据用户名获取账号
     */
    public Account getAccountByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * 更新账号
     */
    public void updateAccount(Account account) {
        repository.save(account);
    }

}
