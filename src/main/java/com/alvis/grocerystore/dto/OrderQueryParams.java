package com.alvis.grocerystore.dto;

public class OrderQueryParams {
    private Integer userId;
    private Integer limit;
    private Integer offset;

    public OrderQueryParams(Integer userId, Integer limit, Integer offset) {
        this.userId = userId;
        this.limit = limit;
        this.offset = offset;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
