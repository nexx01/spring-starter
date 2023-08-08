package shadow.dev.spring.integration.datatabase.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shadow.dev.spring.datatabase.entity.Project;
import shadow.dev.spring.datatabase.repository.ProjectRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ProjectRepositoryIT {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestEntityManager entityManage;

    @Test
    void givenNewProject_whenSave_thenSuccess() {
        var newProject = new Project("PTEST-1", "Test project 1", "Description for PTEST-1");
        var insertedProject = projectRepository.save(newProject);
        assertThat(entityManage.find(Project.class, insertedProject.getId())).isEqualTo(newProject);
    }

    @Test
    void givenProjectCreated_whenUpdate_thenSuccess() {
        var newProject = new Project("PTEST-1", "Test project 1", "Description for PTEST-1");
        entityManage.persist(newProject);
        String newName = "New Project 001";
        newProject.setName(newName);
        projectRepository.save(newProject);
        assertThat(entityManage.find(Project.class, newProject.getId()).getName()).isEqualTo(newName);
    }

    @Test
    void givenProjectCreated_whenFindById_thenSuccess() {
        var newProject = new Project("PTEST-1", "Test project 1", "Description for PTEST-1");
        entityManage.persist(newProject);
        var retrieved = projectRepository.findById(newProject.getId());
        assertThat(retrieved).contains(newProject);
    }

    @Test
    void givenProjectCreated_whenFindByNameContaining_thenSuccess() {
        var newProject1 = new Project("PTEST-1", "Test project 1", "Description for PTEST-1");
        var newProject2 = new Project("PTEST-2", "Test project 2", "Description for PTEST-2");
        entityManage.persist(newProject2);
        entityManage.persist(newProject1);

        var projects = projectRepository.findByNameContaining("Test");

        assertThat(projects).contains(newProject1, newProject2);
    }

    @Test
    void givenProjectCreated_whenDellete_thenSuccess() {
        var newProject = new Project("PTEST-1", "Test project 1", "Description for PTEST-1");
        entityManage.persist(newProject);
        projectRepository.delete(newProject);
        assertThat(entityManage.find(Project.class,newProject.getId())).isNull();
    }
}
