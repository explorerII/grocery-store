package com.alvis.grocerystore.service.impl;

import com.alvis.grocerystore.dao.OrderDao;
import com.alvis.grocerystore.dao.ProductDao;
import com.alvis.grocerystore.dto.BuyItem;
import com.alvis.grocerystore.dto.CreateOrderRequest;
import com.alvis.grocerystore.dto.OrderItemWithDetail;
import com.alvis.grocerystore.model.Order;
import com.alvis.grocerystore.model.OrderItem;
import com.alvis.grocerystore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {

        return orderDao.getOrderById(orderId);
    }

    @Override
    public List<OrderItemWithDetail> getOrderItemsWithDetailById(Integer orderId) {
        return orderDao.getOrderItemsWithDetailById(orderId);
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {

            // calculus total amount
            int productPrice = productDao.getProductById(buyItem.getProductId()).getPrice();
            totalAmount += productPrice * buyItem.getQuantity();

            // transfer buyItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(totalAmount);

            orderItemList.add(orderItem);
        }

        // create order
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
