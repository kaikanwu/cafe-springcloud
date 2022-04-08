package com.kaikanwu.cafe.payment.domain;


import com.kaikanwu.cafe.infrastructure.domain.BaseEntity;
import com.kaikanwu.cafe.infrastructure.domain.account.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Wallet extends BaseEntity {


    public Wallet() {

    }
    private Double money;

    private Integer accountId;

}
