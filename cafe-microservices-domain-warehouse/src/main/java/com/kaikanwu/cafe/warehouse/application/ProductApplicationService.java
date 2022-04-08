package com.kaikanwu.cafe.warehouse.application;


import com.kaikanwu.cafe.infrastructure.domain.warehouse.StockStatus;
import com.kaikanwu.cafe.warehouse.domain.ProductService;
import com.kaikanwu.cafe.warehouse.domain.StockService;
import com.kaikanwu.cafe.infrastructure.domain.warehouse.Product;
import com.kaikanwu.cafe.infrastructure.domain.warehouse.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductApplicationService {

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    /**
     * 获取所有的产品
     */
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * 获取 id 对应产品信息
     */
    public Product getProductById(Integer id) {
        return productService.getProductById(id);
    }

    public List<Product> getProductsByIds(List<Integer> ids) {
        return productService.getProductsByIds(ids);
    }
    /**
     * 创建或者更新一个产品
     */
    public Product saveProduct(Product product) {
        return productService.saveProduct(product);
    }

    public void removeProduct(Integer id) {
        productService.removeProduct(id);
    }


    /**
     * 根据产品获取对应库存
     */
    public Stock getStock(Integer productId) {
        Stock stock = stockService.getByProductId(productId);
        stock.setProduct(productService.getProductById(productId));
        return stock;
    }

    /**
     * 调整库存数量
     */
    public void setStockAmount(Integer productId, Integer amount) {
        stockService.setStockNumber(productId, amount);
    }

    public void setStockStatus(Integer productId, Integer amount, StockStatus status) {
        switch (status) {
            case INCREASE:
                stockService.increase(productId, amount);
                break;
            case DECREASE:
                stockService.decrease(productId, amount);
                break;
            case FROZEN:
                stockService.frozen(productId, amount);
                break;
            case THAWED:
                stockService.thaw(productId, amount);
                break;
        }
    }

}
