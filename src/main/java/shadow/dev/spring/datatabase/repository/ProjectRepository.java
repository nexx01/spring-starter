package shadow.dev.spring.datatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shadow.dev.spring.datatabase.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Iterable<Project> findByNameContaining(String name);

}
