package com.kaikanwu.cafe.payment.application;


import com.kaikanwu.cafe.infrastructure.dto.Settlement;
import com.kaikanwu.cafe.payment.domain.Payment;
import com.kaikanwu.cafe.payment.domain.client.ProductServiceClient;
import com.kaikanwu.cafe.payment.domain.service.PaymentService;
import com.kaikanwu.cafe.payment.domain.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentApplicationService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductServiceClient productService;

    @Autowired
    private WalletService walletService;

    @Resource(name = "settlementCache")
    private Cache settlementCache;


    /**
     * 执行结算清单，生成支付单
     */
    public Payment executeBySettlement(Settlement settlement) {
        // 根据提交的商品信息获取服务器端存储端商品信息，用于支付计算
        productService.setProductMap(settlement);
        // 生成支付单
        Payment payment = paymentService.producePayment(settlement);
        // 设置定时任务
        paymentService.autoThawedTrigger(payment);
        return payment;
    }

    /**
     * 完成支付
     */
    public void accomplishPayment(Integer accountId, String payId) {
        // 完成支付
        double price = paymentService.accomplish(payId);
        // 钱包扣款
        walletService.decrease(accountId, price);
        // 清除定时任务
        settlementCache.evict(payId);
    }

    /**
     * 取消支付
     */
    public void cancelPayment(String payId) {
        // 取消支付单
        paymentService.cancel(payId);
        // 清除定时任务
        settlementCache.evict(payId);
    }
}
