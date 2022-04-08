package com.kaikanwu.cafe.warehouse.domain;

import com.kaikanwu.cafe.infrastructure.domain.warehouse.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Integer> {

}
