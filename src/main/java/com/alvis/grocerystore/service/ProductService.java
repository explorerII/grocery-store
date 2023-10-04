package com.alvis.grocerystore.service;

import com.alvis.grocerystore.dto.ProductQueryParams;
import com.alvis.grocerystore.dto.ProductRequest;
import com.alvis.grocerystore.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
