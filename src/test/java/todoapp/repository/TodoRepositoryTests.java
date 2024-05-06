package todoapp.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import todoapp.model.Todo;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoRepositoryTests {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoRepositoryTests(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

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
                .todoItem("Exercise")
                .completed("No").build();

        //Act
        todoRepository.save(todo);
        todoRepository.save(todo2);

        //Assert
        List<Todo> todoList = todoRepository.findAll();
        Assertions.assertThat(todoList).isNotNull();
        Assertions.assertThat(todoList.size()).isEqualTo(2);
    }

    @Test
    public void TodoRepository_FindById_ReturnTodo() {

        //Arrange
        Todo todo = Todo.builder()
                .todoItem("Learn")
                .completed("No").build();

        //Act
        todoRepository.save(todo);

        //Assert
        Todo todoList = todoRepository.findById(todo.getId()).get(0);

        Assertions.assertThat(todoList).isNotNull();
    }

    @Test
    public void TodoRepository_FindByType_ReturnTodoNotNull() {

        //Arrange
        Todo todo = Todo.builder()
                .todoItem("Learn")
                .completed("No").build();

        //Act
        todoRepository.save(todo);

        //Assert
        Todo todoList = todoRepository.findByCompleted(todo.getCompleted()).get();

        Assertions.assertThat(todoList).isNotNull();
    }

    @Test
    public void TodoRepository_UpdateTodo_ReturnTodoNotNull() {

        //Arrange
        Todo todo = Todo.builder()
                .todoItem("Learn")
                .completed("No").build();

        //Act
        todoRepository.save(todo);

        //Assert
        Todo todoSave = todoRepository.findById(todo.getId()).get(0);
        todoSave.setCompleted("No");
        todoSave.setTodoItem("Organize");

        Todo updatedTodo = todoRepository.save(todoSave);

        Assertions.assertThat(updatedTodo.getTodoItem()).isNotNull();
        Assertions.assertThat(updatedTodo.getCompleted()).isNotNull();

    }

    @Test
    public void TodoRepository_TodoDelete_ReturnTodoIsEmpty() {

        //Arrange
        Todo todo = Todo.builder()
                .todoItem("Learn")
                .completed("No").build();

        //Act
        todoRepository.save(todo);

        todoRepository.deleteById(todo.getId());
        Optional<Todo> todoReturn = todoRepository.findByCompleted(todo.getCompleted());

        //Assert

        Assertions.assertThat(todoReturn).isEmpty();
    }
}