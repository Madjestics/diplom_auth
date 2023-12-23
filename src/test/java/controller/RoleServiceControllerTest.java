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
import diplom.auth.data.entity.Role;
import diplom.auth.web.controllers.RoleServiceController;
import service.mocks.MockRoleService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RoleServiceController.class, MockRoleService.class})
public class RoleServiceControllerTest {
    @Autowired
    private RoleServiceController roleServiceController;
    private MockMvc mockMvc;
    private final static String URL = "http://localhost:8080/role";//Я не помню какой URL нужен
    ObjectMapper mapper = new ObjectMapper();
    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(roleServiceController).build();
    }
    @Test
    public void createTest() throws Exception{
        Role role = new Role(3L, "test", "TestRole");
        String requestJson = mapper.writeValueAsString(role);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isCreated());
    }
    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
    }
    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(get(URL+"/{id}",3L)).andExpect(status().isFound());
    }

    //Тестирование обновления
    @Test
    public void updateTest() throws Exception {
        Role role = new Role(3L, "test", "NewDescription");
        String requestJson = mapper.writeValueAsString(role);
        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRoleByIdTest() throws Exception{
        mockMvc.perform(delete(URL+"/{id}",1L).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
    }
    @After
    public void deleteRoleByNameTest() throws Exception{
        mockMvc.perform(delete(URL+"/{name}","test").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
    }
}
