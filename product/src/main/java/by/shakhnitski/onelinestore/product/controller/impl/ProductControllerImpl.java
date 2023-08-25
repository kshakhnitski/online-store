package by.shakhnitski.onelinestore.product.controller.impl;

import by.shakhnitski.onelinestore.product.controller.ProductController;
import by.shakhnitski.onelinestore.product.dto.ProductCreateRequest;
import by.shakhnitski.onelinestore.product.dto.ProductDto;
import by.shakhnitski.onelinestore.product.mapper.ProductMapper;
import by.shakhnitski.onelinestore.product.model.Product;
import by.shakhnitski.onelinestore.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getProducts();
        return products.stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    @Override
    public ProductDto getProduct(Long productId) {
        Product product = productService.getProduct(productId);
        return productMapper.mapToDto(product);
    }

    @Override
    public ProductDto createProduct(ProductCreateRequest createRequest) {
        Product product = productMapper.mapToEntity(createRequest);
        product = productService.createProduct(product);
        return productMapper.mapToDto(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        productService.deleteProduct(productId);
    }
}
