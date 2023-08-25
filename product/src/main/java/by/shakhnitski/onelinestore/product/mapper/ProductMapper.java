package by.shakhnitski.onelinestore.product.mapper;

import by.shakhnitski.onelinestore.product.dto.ProductCreateRequest;
import by.shakhnitski.onelinestore.product.dto.ProductDto;
import by.shakhnitski.onelinestore.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;

@Mapper(imports = Collections.class)
public interface ProductMapper {
    ProductDto mapToDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "keywords", expression = "java(Collections.emptyList())")
    @Mapping(target = "characteristicTable", expression = "java(Collections.emptyList())")
    Product mapToEntity(ProductCreateRequest createRequest);
}
