package shadow.dev.spring.datatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
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
public class Company implements BaseEntity<Long>{
//    @GeneratedValue(strategy = GenerationType.AUTO)



//
//    @GeneratedValue(generator = "sequence-generator")
//    @GenericGenerator(
//            name = "sequence-generator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "company_id_seq"),
//                    @Parameter(name = "initial_value", value = "1"),
//                    @Parameter(name = "increment_size", value = "1")
//            }
//    )
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private String publicName;
    private Boolean isWorldFamous;

    public Company(Long id, String name, String publicName, Boolean isWorldFamous) {
        this.id = id;
        this.name = name;
        this.publicName = publicName;
        this.isWorldFamous = isWorldFamous;
    }

    public Company(Long id, String name, String publicName, Map<String, String> locales) {
        this.id = id;
        this.name = name;
        this.publicName = publicName;
        this.locales = locales;
    }

    @ElementCollection
    @CollectionTable(name = "company_locales", joinColumns = @JoinColumn(name = "company_id"))
    @MapKeyColumn(name = "lang")
    @Column(name = "description")
    @Builder.Default
    private Map<String, String> locales = new HashMap<>();
}
