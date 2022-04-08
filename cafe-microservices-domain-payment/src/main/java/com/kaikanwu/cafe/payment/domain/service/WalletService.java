package com.kaikanwu.cafe.payment.domain.service;

import com.kaikanwu.cafe.infrastructure.domain.account.Account;
import com.kaikanwu.cafe.payment.domain.Wallet;
import com.kaikanwu.cafe.payment.domain.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WalletService {

    @Autowired(required = false)
    private WalletRepository walletRepository;

    public void decrease(Integer accountId, Double amount) {
        Wallet wallet = walletRepository.findByAccountId(accountId).orElseGet(() -> {
            Wallet newWallet = new Wallet();
            Account account = new Account();
            account.setId(accountId);
            newWallet.setAccountId(accountId);
//            newWallet.setAccount(account);
            // todo: 临时处理
            newWallet.setMoney(amount);
            walletRepository.save(newWallet);
            return newWallet;
        });
        if (wallet.getMoney() > amount) {
            wallet.setMoney(wallet.getMoney() - amount);
            walletRepository.save(wallet);
            log.info("用户 {} 支付成功，本次消费 {}，余额 {}", accountId, amount, wallet.getMoney());
        } else {
            log.info("用户 {} 支付失败，余额不足，当前余额 {}，当前订单额 {}", accountId, wallet.getMoney(), amount);

        }
    }


    public void increase(Integer accountId, Double amount) {


    }

    public void frozen(Integer accountId, Double amount) {

    }

    public void thaw(Integer accountId, Double amount) {

    }

}
