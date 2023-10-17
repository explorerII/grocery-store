package com.alvis.grocerystore.dao.impl;

import com.alvis.grocerystore.dao.OrderDao;
import com.alvis.grocerystore.dto.OrderItemWithDetail;
import com.alvis.grocerystore.model.Order;
import com.alvis.grocerystore.model.OrderItem;
import com.alvis.grocerystore.rowmapper.OrderItemWithDetailRowMapper;
import com.alvis.grocerystore.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer orderId) {

        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> list = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItemWithDetail> getOrderItemsWithDetailById(Integer orderId) {

        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                "FROM order_item as oi " +
                "LEFT JOIN product as p ON oi.product_id = p.product_id " +
                "WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItemWithDetail> list = namedParameterJdbcTemplate.query(sql, map, new OrderItemWithDetailRowMapper());

        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {

        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date date = new Date();
        map.put("createdDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (:orderId, :productId, :quantity, :amount)";

        Map<String, Object>[] mapList = new Map[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {

            OrderItem orderItem = orderItemList.get(i);
            mapList[i] = new HashMap<>();
            mapList[i].put("orderId", orderId);
            mapList[i].put("productId", orderItem.getProductId());
            mapList[i].put("quantity", orderItem.getQuantity());
            mapList[i].put("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, mapList);
    }
}
