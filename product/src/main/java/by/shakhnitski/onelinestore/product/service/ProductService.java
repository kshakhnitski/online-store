package by.shakhnitski.onelinestore.product.service;

import by.shakhnitski.onelinestore.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    Product getProduct(Long productId);

    Product createProduct(Product product);

    void deleteProduct(Long productId);
}
