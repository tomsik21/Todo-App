package todoapp.api.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import todoapp.domain.Todo;
import todoapp.repository.TodoRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void TodoRepository_SaveAll_ReturnSavedTodo() {

        //Arrange
        Todo todo = Todo.builder()
                .todoItem("Learn")
                .completed("No").build();

        //Act
        Todo savedTodo = todoRepository.save(todo);

        //Assert
        Assertions.assertThat(savedTodo).isNotNull();
        Assertions.assertThat(savedTodo.getId()).isGreaterThan(0);
    }

    @Test
    public void TodoRepository_GetAll_ReturnMoreThanOneTodo() {

        //Arrange
        Todo todo = Todo.builder()
                .todoItem("Learn")
                .completed("No").build();
        Todo todo2 = Todo.builder()
                .todoItem("Learn")
                .completed("No").build();

        //Act
        todoRepository.save(todo);
        todoRepository.save(todo2);

        //Assert
        List<Todo> todoList = todoRepository.findAll();
        Assertions.assertThat(todoList).isNotNull();
        Assertions.assertThat(todoList.size()).isEqualTo(2);
    }
}