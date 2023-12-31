package com.alvis.grocerystore.controller;

import com.alvis.grocerystore.constant.ProductCategory;
import com.alvis.grocerystore.dto.ProductQueryParams;
import com.alvis.grocerystore.dto.ProductRequest;
import com.alvis.grocerystore.model.Product;
import com.alvis.grocerystore.service.ProductService;
import com.alvis.grocerystore.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            // pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset){

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // get product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // get numbers of product
        Integer toatal = productService.countProduct(productQueryParams);

        // for pagination
        Page page = new Page();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setResult(productList);
        page.setTotal(toatal);

        return ResponseEntity.status(200).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){

        Product product = productService.getProductById(productId);

        if (product != null){
            return ResponseEntity.status(200).body(product);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        // check if product not exists
        if (productService.getProductById(productId) == null) {
            return ResponseEntity.status(404).build();
        }

        productService.updateProduct(productId, productRequest);

        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(200).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);

        return ResponseEntity.status(204).build();
    }
}
