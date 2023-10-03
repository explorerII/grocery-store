package com.alvis.grocerystore.dao;

import com.alvis.grocerystore.dto.ProductRequest;
import com.alvis.grocerystore.model.Product;

public interface ProductDao {

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);
}
