package shadow.dev.spring.datatabase.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    Long id;

    String code;

    String name;

    String description;

    public Project(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
