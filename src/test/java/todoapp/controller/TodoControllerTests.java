package todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;

import static org.mockito.BDDMockito.given;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import todoapp.controllers.TodoController;
import todoapp.dto.TodoDto;
import todoapp.dto.TodoResponse;
import todoapp.service.TodoService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;
    private List<TodoDto> todoDtoList;

    @BeforeEach
    public void init() {
        todoDtoList = new ArrayList<>();
        TodoDto TodoDto1= TodoDto.builder().id(1).todoItem("Understand how to work with Mockito").completed("false").build();
        TodoDto TodoDto2= TodoDto.builder().id(2).todoItem("Make a dinner").completed("false").build();

        todoDtoList.add(TodoDto1);
        todoDtoList.add(TodoDto2);


    }

    @Test
    public void TodoController_NewTodo_ReturnNew() throws Exception {
        given(todoService.add(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/todoNew")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoDtoList.get(0))));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.todoItem", CoreMatchers.is(todoDtoList.get(0).getTodoItem())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed", CoreMatchers.is(todoDtoList.get(0).getCompleted())));

    }

    @Test
    public void TodoController_GetAllTodo_ReturnResponseDto() throws Exception {

        TodoResponse responseDto = TodoResponse.builder().content(todoDtoList).build();
        when(todoService.getAllTodo()).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                .param("Make a dinner", "false"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));

    }

    @Test
    public void TodoController_TodoDetail_ReturnTodoDto() throws Exception {
        when(todoService.getTodoById(1)).thenReturn(todoDtoList.get(1));

        ResultActions response = mockMvc.perform(get("/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoDtoList.get(0))));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.todoItem", CoreMatchers.is(todoDtoList.get(1).getTodoItem())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed", CoreMatchers.is(todoDtoList.get(1).getCompleted())));

    }

    @Test
    public void TodoController_UpdateTodo_ReturnTodoDto() throws Exception {

        when(todoService.updateTodo(todoDtoList.get(0))).thenReturn(todoDtoList.get(0));

        ResultActions response = mockMvc.perform(put("/todoUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoDtoList.get(0))));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.todoItem", CoreMatchers.is(todoDtoList.get(0).getTodoItem())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed", CoreMatchers.is(todoDtoList.get(0).getCompleted())));

   }

    @Test
    public void TodoController_DeleteTodo_ReturnString() throws Exception {
        doNothing().when(todoService).deleteTodoId(0);

        ResultActions response = mockMvc.perform(delete("/todoDelete/0")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
}
