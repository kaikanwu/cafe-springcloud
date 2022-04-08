package com.kaikanwu.cafe.warehouse.domain;

import com.kaikanwu.cafe.infrastructure.domain.warehouse.Product;
import com.kaikanwu.cafe.infrastructure.dto.Settlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public void setProductMap(Settlement settlement) {
        List<Integer> ids = settlement.getItems().stream().map(Settlement.Item::getProductId).collect(Collectors.toList());
        settlement.productMap = repository.findByIdIn(ids).stream().collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    /**
     * 获取所有的货物信息
     */
    public Iterable<Product> getAllProducts() {
        return repository.findAll();
    }

    public List<Product> getProductsByIds(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }
    /**
     * 根据 id 获取对应的产品
     */
    public Product getProductById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * 创建或更新一个产品
     */
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    /**
     * 根据 id 删除对应的产品
     */
    public void removeProduct(Integer id) {
        repository.deleteById(id);
    }
}
