package com.kaikanwu.cafe.payment.domain;

import com.kaikanwu.cafe.infrastructure.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {

    String payId;

    Status payStatus;

    Double totalPrice;

    String paymentLink;

    Long expires;

    Date createTime;

    public Payment() {
    }

    public Payment(Double totalPrice, Long expires) {
        setTotalPrice(totalPrice);
        setExpires(expires);
        setCreateTime(new Date());
        setPayStatus(Status.WAITING);
        setPayId(UUID.randomUUID().toString());
        // 链接也可以增加账户 id
        setPaymentLink("/pay/"+ getPayId());
    }

    /**
     * 支付状态枚举
     */
    public enum Status{
        WAITING,
        CANCEL,
        PAYED,
        TIMEOUT
    }


}
