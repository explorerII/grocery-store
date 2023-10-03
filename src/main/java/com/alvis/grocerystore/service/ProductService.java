package com.alvis.grocerystore.service;

import com.alvis.grocerystore.dto.ProductRequest;
import com.alvis.grocerystore.model.Product;

public interface ProductService {

    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
