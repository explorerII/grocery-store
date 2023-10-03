package com.alvis.grocerystore.service.impl;

import com.alvis.grocerystore.dao.ProductDao;
import com.alvis.grocerystore.dto.ProductRequest;
import com.alvis.grocerystore.model.Product;
import com.alvis.grocerystore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }
}
