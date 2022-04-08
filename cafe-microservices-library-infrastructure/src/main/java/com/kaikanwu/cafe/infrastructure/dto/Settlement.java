package com.kaikanwu.cafe.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaikanwu.cafe.infrastructure.domain.warehouse.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Map;

/**
 * 结算
 */
public class Settlement {
    /**
     * 结算单中的商品
     */
    @Size(min = 1, message = "至少要有一样商品")
    private Collection<Item> items;

    /**
     * 产品 id 和产品的映射
     */
    public transient Map<Integer, Product> productMap;

    public static class Item {
        // 商品数量
        @NotNull
        @Min(value = 1, message = "结算单中商品数量至少是 1 件")
        private Integer amount;

        @JsonProperty("id")
        @NotNull(message = "商品 id 不能为空")
        private Integer productId;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }
    }


    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }


}
