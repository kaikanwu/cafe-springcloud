package com.kaikanwu.cafe.infrastructure.domain.warehouse;

import com.kaikanwu.cafe.infrastructure.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Stock extends BaseEntity {

    private Integer amount;

    private Integer frozen;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private  Product product;


    /**
     * 冻结指定数量的库存
     */
    public void frozen(Integer number) {
        this.amount -= number;
        this.frozen += number;
    }

    /**
     * 解冻一定数量的库存
     */
    public void thaw(Integer number) {
        frozen(-1 * number);
    }

    /**
     * 减少指定数量的库存（指售出商品）
     */
    public void decrease(Integer number) {
        this.frozen -= number;
    }

    /**
     * 增加商品库存
     */
    public void increase(Integer number) {
        this.amount += number;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
