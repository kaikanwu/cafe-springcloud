package com.kaikanwu.cafe.warehouse.interfaces;

import com.kaikanwu.cafe.warehouse.application.ProductApplicationService;
import com.kaikanwu.cafe.infrastructure.domain.warehouse.Product;
import com.kaikanwu.cafe.infrastructure.infrasturcture.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductResource {
    @Autowired
    ProductApplicationService service;

    @PostMapping
    public Response createProduct(@Valid Product product) {
        return Response.success(service.saveProduct(product));
    }

//    @GetMapping("/{id}")
//    public Response getProductById(@PathVariable Integer id) {
//        return Response.success(service.getProductById(id));
//    }
//
//    @GetMapping
//    public Response getAllProducts() {
//        return Response.success(service.getAllProducts());
//    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return service.getProductById(id);
    }
    @GetMapping()
    public Iterable<Product> getProducts() {
        return service.getAllProducts();
    }

    @PutMapping
    public Response updateProduct(@Valid Product product) {
        return Response.success(service.saveProduct(product));
    }

    @DeleteMapping("/{id}")
    public Response removeProduct(@PathVariable Integer id) {
        service.removeProduct(id);
        return Response.success();
    }



}
