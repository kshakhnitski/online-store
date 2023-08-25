package by.shakhnitski.onelinestore.product.controller;

import by.shakhnitski.onelinestore.product.dto.ProductCreateRequest;
import by.shakhnitski.onelinestore.product.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/v1/products")
public interface ProductController {

    @GetMapping
    List<ProductDto> getProducts();

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto getProduct(@PathVariable Long productId);

    @PostMapping
    ProductDto createProduct(@Valid @RequestBody ProductCreateRequest createRequest);

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable Long productId);
}
