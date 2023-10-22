package com.alvis.grocerystore.dao;

import com.alvis.grocerystore.dto.OrderItemWithDetail;
import com.alvis.grocerystore.dto.OrderQueryParams;
import com.alvis.grocerystore.model.Order;
import com.alvis.grocerystore.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);
    List<OrderItemWithDetail> getOrderItemsWithDetailById(Integer orderId);
    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
