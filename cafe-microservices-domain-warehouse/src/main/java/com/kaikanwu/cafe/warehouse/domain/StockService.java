package com.kaikanwu.cafe.warehouse.domain;

import com.kaikanwu.cafe.infrastructure.domain.warehouse.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    // 库存普通操作

    /**
     * 根据产品 id 获取对应库存
     *
     * @param productId 产品 id
     * @return 库存信息
     */
    public Stock getByProductId(Integer productId) {
        return stockRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId.toString()));
    }

    /**
     * 设置货物库存数量
     */
    public void setStockNumber(Integer productId, Integer number) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.setAmount(number);
        stockRepository.save(stock);
    }

    /**
     * 增加货物库存库存
     */
    public void increase(Integer productId, Integer number) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.increase(number);
        stockRepository.save(stock);
        log.info("库存增加，商品：{}， 数量：{}", productId, number);
    }

    // 库存购买相关操作

    /**
     * 货物冻结：移动指定数量到冻结状态
     */
    public void frozen(Integer productId, Integer number) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.frozen(number);
        stockRepository.save(stock);
        log.info("库存冻结，商品：{}， 数量：{}", productId, number);

    }

    /**
     * 货物解冻：从冻结货物中移动指定数量到正常状态
     */
    public void thaw(Integer productId, Integer number) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.thaw(number);
        stockRepository.save(stock);
        log.info("库存解冻，商品：{}，数量：{}", productId, number);
    }

    /**
     * 货物售出：从冻结状态的货物中扣减
     */
    public void decrease(Integer productId, Integer number) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId.toString()));
        stock.decrease(number);
        stockRepository.save(stock);
        log.info("库存减少，商品：{}，数量：{}", productId, number);
    }


}
