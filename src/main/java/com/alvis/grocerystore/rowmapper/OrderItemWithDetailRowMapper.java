package com.alvis.grocerystore.rowmapper;

import com.alvis.grocerystore.dto.OrderItemWithDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemWithDetailRowMapper implements RowMapper<OrderItemWithDetail> {
    @Override
    public OrderItemWithDetail mapRow(ResultSet resultSet, int i) throws SQLException {

        OrderItemWithDetail orderItemsNDetail = new OrderItemWithDetail();
        orderItemsNDetail.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItemsNDetail.setOrderId(resultSet.getInt("order_id"));
        orderItemsNDetail.setProductId(resultSet.getInt("product_id"));
        orderItemsNDetail.setQuantity(resultSet.getInt("quantity"));
        orderItemsNDetail.setAmount(resultSet.getInt("quantity"));
        orderItemsNDetail.setProductName(resultSet.getString("product_name"));
        orderItemsNDetail.setImageUrl(resultSet.getString("image_url"));

        return orderItemsNDetail;
    }
}
