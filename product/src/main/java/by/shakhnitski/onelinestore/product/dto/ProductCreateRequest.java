package by.shakhnitski.onelinestore.product.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {
    @NotBlank
    private String name;

    private String description = "";

    @NotNull
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Long organizationId;

    @NotNull
    @Min(0)
    private Integer quantity;
}
