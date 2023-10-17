package com.alvis.grocerystore.service;

import com.alvis.grocerystore.dto.CreateOrderRequest;
import com.alvis.grocerystore.dto.OrderItemWithDetail;
import com.alvis.grocerystore.model.Order;

import java.util.List;

public interface OrderService {
    Order getOrderById(Integer orderId);
    List<OrderItemWithDetail> getOrderItemsWithDetailById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
