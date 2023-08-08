package shadow.dev.spring.datatabase.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.PRIVATE;

@Entity
@FieldDefaults(level = PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    long id;

    String name;
    double price;

    public static Product from(int id, String name, double price) {
        return new Product(id, name, price);
    }

}
