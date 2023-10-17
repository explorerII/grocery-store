package com.alvis.grocerystore.service;

import com.alvis.grocerystore.dto.CreateOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
