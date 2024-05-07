package todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todoapp.dto.TodoDto;
import todoapp.dto.TodoResponse;
import todoapp.repository.TodoRepository;

import todoapp.service.TodoService;


@Controller
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String index() {
        return "index.html";
    }

    @GetMapping("/todos")
    public ResponseEntity<TodoResponse> todos() {
        return new ResponseEntity<>(todoService.getAllTodo(), HttpStatus.OK);
    }

    @PostMapping("/todoNew")
    public ResponseEntity<TodoDto> add(@RequestBody TodoDto todoDto) {
        todoService.add(todoDto);
        return new ResponseEntity<>(todoDto, HttpStatus.CREATED);
    }

    @PostMapping("/todoDelete/{id}")
    public ResponseEntity<TodoResponse> delete(@PathVariable long id) {
        todoService.deleteTodoId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/todoUpdate/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable long id, TodoDto todoDto) {
        todoService.updateTodo(todoDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}