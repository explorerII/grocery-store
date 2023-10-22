package com.alvis.grocerystore.service.impl;

import com.alvis.grocerystore.dao.OrderDao;
import com.alvis.grocerystore.dao.ProductDao;
import com.alvis.grocerystore.dao.UserDao;
import com.alvis.grocerystore.dto.BuyItem;
import com.alvis.grocerystore.dto.CreateOrderRequest;
import com.alvis.grocerystore.dto.OrderItemWithDetail;
import com.alvis.grocerystore.dto.OrderQueryParams;
import com.alvis.grocerystore.model.Order;
import com.alvis.grocerystore.model.OrderItem;
import com.alvis.grocerystore.model.Product;
import com.alvis.grocerystore.model.User;
import com.alvis.grocerystore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order : orderList) {
            order.setOrderItemWithDetailList(orderDao.getOrderItemsWithDetailById(order.getOrderId()));
        }
        return orderList;
    }

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

        // check if user exists
        User user = userDao.getUserById(userId);
        if (user == null) {
            log.warn("userId {} not exists", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {

            // check if product exists and stock is enough for selling
            Product product = productDao.getProductById(buyItem.getProductId());
            if (product == null) {
                log.warn("productId {} not exists", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (buyItem.getQuantity() > product.getStock()) {
                log.warn("productId {} is not enough for selling, buying quantity: {}, stock: {}", product.getProductId(), buyItem.getQuantity(), product.getStock());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // calculus total amount
            int productPrice = productDao.getProductById(buyItem.getProductId()).getPrice();
            totalAmount += productPrice * buyItem.getQuantity();

            // transfer buyItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(totalAmount);

            // update stock
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            orderItemList.add(orderItem);
        }

        // create order
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
