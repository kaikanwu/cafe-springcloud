package com.kaikanwu.cafe.payment.domain.client;

import com.kaikanwu.cafe.infrastructure.domain.warehouse.Product;
import com.kaikanwu.cafe.infrastructure.domain.warehouse.StockStatus;
import com.kaikanwu.cafe.infrastructure.dto.Settlement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@FeignClient(name = "ProductServiceClient")
public interface ProductServiceClient {

    default void setProductMap(Settlement settlement) {
        List<Integer> ids = settlement.getItems().stream().map(Settlement.Item::getProductId).collect(Collectors.toList());
        //todo : filter needed here
        settlement.productMap = getProducts().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/products")
    List<Product> getProducts();

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    Product getProductById(@PathVariable Integer id);

    // 库存相关
    @RequestMapping(method = RequestMethod.POST, value = "/stock/status/{productId}")
    void setStockStatus(@PathVariable Integer productId, @RequestParam("amount") Integer amount, @RequestParam("status") StockStatus status);

    default void decrease(Integer productId, Integer amount) {
        setStockStatus(productId, amount, StockStatus.DECREASE);
    }

    default void frozen(Integer productId, Integer amount) {
        setStockStatus(productId, amount, StockStatus.FROZEN);
    }

    default void thaw(Integer productId, Integer amount) {
        setStockStatus(productId, amount, StockStatus.THAWED);
    }
}
