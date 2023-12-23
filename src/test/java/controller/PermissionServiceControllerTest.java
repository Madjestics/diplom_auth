package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import diplom.auth.web.controllers.PermissionServiceController;
import diplom.auth.data.entity.Permission;
import service.mocks.MockPermissionService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PermissionServiceController.class, MockPermissionService.class})
public class PermissionServiceControllerTest {
    @Autowired
    private PermissionServiceController permissionServiceController;
    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/permission";//Я не помню какой URL нужен

    ObjectMapper mapper = new ObjectMapper();
    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(permissionServiceController).build();
    }

    //Тестирование создания
    @Test
    public void createTest() throws Exception{
        Permission permission = new Permission(14L, "TestPermission", "test.test");
        String requestJson = mapper.writeValueAsString(permission);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
    }

    //Тестирование поиска по ID
    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(get(URL+"/{id}",1L)).andExpect(status().isFound());
    }

    //Тестирование обновления
    @Test
    public void updateTest() throws Exception {
        Permission permission = new Permission(14L, "TestPermission", "test.test");
        String requestJson = mapper.writeValueAsString(permission);
        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @After
    public void deleteTest() throws Exception{
        mockMvc.perform(delete(URL+"/{id}",14L).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
    }
}
