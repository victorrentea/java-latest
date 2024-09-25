package victor.training.java.patterns.template.support;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Double price;
    private String imageUrl;
}
