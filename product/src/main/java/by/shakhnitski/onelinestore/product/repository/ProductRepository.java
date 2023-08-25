package by.shakhnitski.onelinestore.product.repository;

import by.shakhnitski.onelinestore.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
