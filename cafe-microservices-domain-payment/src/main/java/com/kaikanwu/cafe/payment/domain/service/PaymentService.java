package com.kaikanwu.cafe.payment.domain.service;

import com.kaikanwu.cafe.infrastructure.dto.Settlement;
import com.kaikanwu.cafe.infrastructure.infrasturcture.cache.CacheConfiguration;
import com.kaikanwu.cafe.payment.domain.Payment;
import com.kaikanwu.cafe.payment.domain.client.ProductServiceClient;
import com.kaikanwu.cafe.payment.domain.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Slf4j
public class PaymentService {

    private static final long PRODUCT_FROZEN_EXPIRES = CacheConfiguration.DEFAULT_CACHE_EXPIRES;

    private final Timer timer = new Timer();

    @Autowired(required = false)
    private ProductServiceClient stockService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Resource(name = "settlementCache")
    private Cache settlementCache;


    /**
     * 生成支付单的操作
     * 1. 根据结算单冻结货物库存
     * 2. 计算总价，生成支付单
     */
    public Payment producePayment(Settlement settlement) {
        Double total = settlement.getItems().stream().mapToDouble(i -> {
            // 1. 先冻结库存
            stockService.frozen(i.getProductId(), i.getAmount());
            // 2. 计算结算单的总价
            return settlement.productMap.get(i.getProductId()).getPrice() * i.getAmount();
        }).sum();

        Payment payment = new Payment(total, PRODUCT_FROZEN_EXPIRES);
        paymentRepository.save(payment);
        // cache
        settlementCache.put(payment.getPayId(), settlement);

        log.info("支付订单已创建，总额: {}，订单号：{}", payment.getTotalPrice(), payment.getPayId());
        //todo 尝试打印缓存
//        CacheStats cacheStats = (CaffeineCache)settlementCache.getNativeCache()
//        log.info("当前结算单缓存：{}", cache.as);
        return payment;
    }


    /**
     * 用户完成支付操作
     */
    public double accomplish(String payId) {
        synchronized (payId.intern()) {
            Payment payment = paymentRepository.getByPayId(payId);
            if (payment.getPayStatus() == Payment.Status.WAITING) {
                payment.setPayStatus(Payment.Status.PAYED);
                paymentRepository.save(payment);
                //
                accomplishSettlement(Payment.Status.PAYED, payment.getPayId());
                log.info("编号为 {} 的支付单已经完成支付，支付金额为 {}", payId, payment.getTotalPrice());
                return payment.getTotalPrice();
            } else {
                throw new UnsupportedOperationException("当前订单不允许支付，当前状态为：" + payment.getPayStatus());
            }
        }
    }

    /**
     * 主动取消支付单操作
     */
    public void cancel(String payId) {
        synchronized (payId.intern()) {
            Payment payment = paymentRepository.getByPayId(payId);
            if (payment.getPayStatus() == Payment.Status.WAITING) {
                payment.setPayStatus(Payment.Status.CANCEL);
                paymentRepository.save(payment);
                accomplishSettlement(Payment.Status.CANCEL, payment.getPayId());
                log.info("订单编号为 {} 的支付单已经被取消", payId);
            } else {
                throw new UnsupportedOperationException("当前订单不允许取消，当前状态为：" + payment.getPayStatus());
            }
        }
    }

    /**
     * 开启一个定时任务，取消超时订单
     * 实际生产环境应该用分布式定时任务框架和分布式锁来处理。
     */
    public void autoThawedTrigger(Payment payment) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (payment.getPayId().intern()) {
                    Payment currentPayment = paymentRepository.findById(payment.getId()).orElseThrow(() -> new EntityNotFoundException(payment.getPayId().toString()));
                    if (currentPayment.getPayStatus() == Payment.Status.WAITING) {
                        accomplishSettlement(Payment.Status.TIMEOUT, payment.getPayId());
                        log.info("超时支付订单已解冻，订单金额：{}，订单号：{}", payment.getTotalPrice(), payment.getPayId());
                    }
                }
            }
        }, payment.getExpires());
    }


    /**
     * 根据支付状态，调整库存
     */
    private void accomplishSettlement(Payment.Status endState, String payId) {
        Settlement settlement = (Settlement) Objects.requireNonNull(Objects.requireNonNull(settlementCache.get(payId)).get());
        settlement.getItems().forEach(i -> {
            if (endState == Payment.Status.PAYED) {
                // 已支付状态
                stockService.decrease(i.getProductId(), i.getAmount());
            } else {
                // 取消、超时状态，需要解冻
                stockService.thaw(i.getProductId(), i.getAmount());
            }
        });
    }
}
