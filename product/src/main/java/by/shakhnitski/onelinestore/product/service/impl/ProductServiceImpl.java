package by.shakhnitski.onelinestore.product.service.impl;

import by.shakhnitski.onelinestore.product.model.Product;
import by.shakhnitski.onelinestore.product.repository.ProductRepository;
import by.shakhnitski.onelinestore.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException();
        }
        productRepository.deleteById(productId);
    }
}
