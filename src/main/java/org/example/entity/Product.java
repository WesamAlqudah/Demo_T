package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.security.PrivateKey;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private Integer ID;
    private String Name;
    private Integer Quantity;
    private Integer Version;

    public Product(String product_name, int i) {
        Name=product_name;
        Quantity=i;
    }
}
