package com.kaikanwu.cafe.warehouse.interfaces;

import com.kaikanwu.cafe.infrastructure.domain.warehouse.StockStatus;
import com.kaikanwu.cafe.infrastructure.infrasturcture.response.Response;
import com.kaikanwu.cafe.warehouse.application.ProductApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockResource {

    @Autowired
    private ProductApplicationService service;

    @PostMapping("/status/{productId}")
    public void setStockStatus(@PathVariable Integer productId, @RequestParam("amount") Integer amount, @RequestParam("status") StockStatus status) {
        service.setStockStatus(productId, amount, status);
    }

    @GetMapping("/{productId}")
    public Response getStock(@PathVariable Integer productId) {
        return Response.success(service.getStock(productId));
    }

    @PostMapping("/{productId}")
    public Response updateStock(@PathVariable Integer productId, Integer amount) {
        service.setStockAmount(productId, amount);
        return Response.success();
    }

}
