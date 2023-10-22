package com.alvis.grocerystore.controller;

import com.alvis.grocerystore.dto.CreateOrderRequest;
import com.alvis.grocerystore.dto.OrderQueryParams;
import com.alvis.grocerystore.model.Order;
import com.alvis.grocerystore.service.OrderService;
import com.alvis.grocerystore.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                       @RequestParam(defaultValue = "10") @Max(100) @Min(0) Integer limit,
                                       @RequestParam(defaultValue = "0") @Min(0) Integer offset){

        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // get order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // get numbers of order
        Integer total = orderService.countOrders(orderQueryParams);

        // for pagination
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setResult(orderList);
        page.setTotal(total);

        return ResponseEntity.status(200).body(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        // get order
        Order order = orderService.getOrderById(orderId);
        // get orderItem with detail and set up order
        order.setOrderItemWithDetailList(orderService.getOrderItemsWithDetailById(orderId));

        return ResponseEntity.status(201).body(order);
    }
}