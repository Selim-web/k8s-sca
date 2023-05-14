package com.palpashop.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


public class OrderLineItems implements Serializable {

    private Long id;
    private String code;
    private BigDecimal price;
    private Integer quantity;

    public OrderLineItems(String code, BigDecimal price, Integer quantity) {
        this.code = code;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderLineItems() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
