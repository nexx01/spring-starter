package shadow.dev.spring.datatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="company")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@NamedQuery(
        name="Company.findByName",
        query="select c from Company c where lower( c.name) = lower( :name)"
)
public class Company implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true,nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "company_locales", joinColumns = @JoinColumn(name = "company_id"))
    @MapKeyColumn(name = "lang")
    @Column(name = "description")
    @Builder.Default
    private Map<String, String> locales = new HashMap<>();
}
