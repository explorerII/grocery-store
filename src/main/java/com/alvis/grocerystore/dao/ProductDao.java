package com.alvis.grocerystore.dao;

import com.alvis.grocerystore.dto.ProductRequest;
import com.alvis.grocerystore.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
