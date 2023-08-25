package by.shakhnitski.onelinestore.product.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", length = 1000, nullable = false)
    private String description;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(name = "price", scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CharacteristicTableEntry> characteristicTable;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_keywords",
            joinColumns = @JoinColumn(name = "product_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "keyword_id", nullable = false)
    )
    private List<Keyword> keywords;
}
