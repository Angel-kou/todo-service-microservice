package com.thoughtworks.training.kmj.todoservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.training.kmj.todoservice.model.ToDo;
import com.thoughtworks.training.kmj.todoservice.model.User;
import com.thoughtworks.training.kmj.todoservice.repository.UserRepository;
import com.thoughtworks.training.kmj.todoservice.service.ToDoService;
import com.thoughtworks.training.kmj.todoservice.utils.JwtAuthentication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ToDoAPITest {

    private int userId = 1;
    private int todoId = 1;
    private final User user = new User(userId, "kmj", "123456");



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoService toDoService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        when(userRepository.findOne(userId)).thenReturn(user);
    }

    @Test
    public void shouldReturn401ForUnauthenticatedRequest() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(unauthenticated());
    }

    @Test
    public void shouldReturnTodoListWithAuthentication() throws Exception {
        mockMvc.perform(
                get("/todos")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                user.getId(), null, Collections.emptyList()
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].id").value(todoId))
                .andExpect(jsonPath("$[0].content").value("todo1"));
    }


    @Test
    public void shouldReturnTodoListWithAuthorizationHeader() throws Exception {
        String token = JwtAuthentication.generateToken(userId);

        mockMvc.perform(
                get("/todos")
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].id").value(todoId))
                .andExpect(jsonPath("$[0].content").value("todo1"));
    }



    @Test
    public void shouldReturnTodoListWithManuallySetSecurityContext() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userId, null, Collections.emptyList()
        ));

        List<ToDo> list = toDoService.getList();
        assertThat(list.size(), is(4));
        assertThat(list.get(0).getId(), is(todoId));
        assertThat(list.get(0).getContent(), is("todo1"));
    }



    @Test
    public void jsonToObject() throws IOException {
        String json = "{\"id\":1,\"content\":\"todo1\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        ToDo todo = objectMapper.readValue(json,ToDo.class);
        System.out.println(todo);
    }


    @Test
    public void jsonToObjectArray() throws IOException {
        String json = "[{\"id\":1,\"content\":\"todo1\"},{\"id\":2,\"content\":\"todo2\"},{\"id\":3,\"content\":\"todo3\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<ToDo> list = objectMapper.readValue(json,
                new TypeReference<List<ToDo>>(){}
                );
        System.out.println(list);
    }


}
