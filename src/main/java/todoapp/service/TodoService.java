package todoapp.service;

import todoapp.dto.TodoDto;
import todoapp.dto.TodoResponse;

public interface TodoService {
    TodoDto add(TodoDto todoDto);

    TodoResponse getAllTodo();

    TodoDto getTodoById(long id);

    void updateTodo(TodoDto todoDto, long id);

    void deleteTodoId(long id);
}