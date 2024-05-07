package todoapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todoapp.dto.TodoDto;
import todoapp.dto.TodoResponse;
import todoapp.exception.TodoNotFoundException;
import todoapp.model.Todo;
import todoapp.repository.TodoRepository;
import todoapp.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDto add(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setTodoItem(todoDto.getTodoItem());
        todo.setCompleted(todoDto.getCompleted());

        Todo newTodo = todoRepository.save(todo);

        TodoDto todoResponse = new TodoDto();
        todoResponse.setId(newTodo.getId());
        todoResponse.setTodoItem(newTodo.getTodoItem());
        todoResponse.setCompleted(newTodo.getCompleted());

        return todoResponse;
    }

    @Override
    public TodoResponse getAllTodo() {
        List<Todo> listOfTodos = todoRepository.findAll();
        List<TodoDto> content = listOfTodos.stream().map(this::mapToDto).collect(Collectors.toList());

        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setContent(content);
        return todoResponse;
    }

    @Override
    public TodoDto getTodoById(long id) {
        List<Todo> todos = todoRepository.findById(id);
        if (todos.isEmpty()) {
            throw new TodoNotFoundException("Todo not found");
        }
        Todo todo = todos.get(0);

        return mapToDto(todo);
    }

    @Override
    public TodoDto updateTodo(TodoDto TodoDto, long id) {
        List<Todo> todos = todoRepository.findById(id);
        if (todos.isEmpty()) {
            throw new TodoNotFoundException("Could not update Todo");
        }
        Todo todo = todos.get(0);

        todo.setTodoItem(TodoDto.getTodoItem());
        todo.setCompleted(TodoDto.getCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return mapToDto(updatedTodo);
    }

    @Override
    public void deleteTodoId(long id) {
        List<Todo> todos = todoRepository.findById(id);
        if (todos.isEmpty()) {
            throw new TodoNotFoundException("Could not delete Todo");
        }
        Todo todo = todos.get(0);
        todoRepository.deleteById(todo.getId());
    }

    private TodoDto mapToDto(Todo todo) {
        TodoDto TodoDto = new TodoDto();
        TodoDto.setId(todo.getId());
        TodoDto.setTodoItem(todo.getTodoItem());
        TodoDto.setCompleted(todo.getCompleted());
        return TodoDto;
    }
}
