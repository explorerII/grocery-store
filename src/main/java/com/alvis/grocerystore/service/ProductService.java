package com.alvis.grocerystore.service;

import com.alvis.grocerystore.constant.ProductCategory;
import com.alvis.grocerystore.dto.ProductRequest;
import com.alvis.grocerystore.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category, String search);
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
