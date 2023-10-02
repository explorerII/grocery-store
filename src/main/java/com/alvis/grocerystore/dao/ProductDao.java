package com.alvis.grocerystore.dao;

import com.alvis.grocerystore.model.Product;

public interface ProductDao {

    Product getProductById(Integer id);
}
