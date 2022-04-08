package com.kaikanwu.cafe.infrastructure.domain.warehouse;

import com.kaikanwu.cafe.infrastructure.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

    @NotEmpty
    private String title;

    @NotNull
    @Min(value = 0, message = "商品价格不能低于 0 ")
    private Double price;

    @Max(value = 10, message = "商品评分最高 10")
    @Min(value = 0, message = "商品评分最低 0")
    private Float rate;

    @NotEmpty
    private String description;

    private String cover;

    private String detail;
}
