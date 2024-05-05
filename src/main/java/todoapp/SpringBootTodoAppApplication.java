package todoapp;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import todoapp.domain.Todo;
import todoapp.repository.TodoRepository;

@SpringBootApplication
public class SpringBootTodoAppApplication {
    @Autowired
    public TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTodoAppApplication.class, args);
    }

}