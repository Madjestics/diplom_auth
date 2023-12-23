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
import diplom.auth.business.services.impl.ProfileService;
import diplom.auth.data.entity.Profile;
import diplom.auth.web.controllers.ProfileServiceController;
import diplom.auth.web.jwt.AccountCredentials;
import service.mocks.MockProfileService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProfileServiceController.class, MockProfileService.class})
public class ProfileServiceControllerTest {
    @Autowired
    private ProfileServiceController profileServiceController;

    @Autowired
    private ProfileService profileService;
    private MockMvc mockMvc;
    private final static String URL = "http://localhost:8080/profile";//Я не помню какой URL нужен
    ObjectMapper mapper = new ObjectMapper();
    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(profileServiceController).build();
    }
    @Test
    public void createTest() throws Exception{
        Profile profile = new Profile("test6", "123",true);
        String requestJson = mapper.writeValueAsString(profile);
        mockMvc.perform(post(URL+"/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
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

    //Тестирование поиска ID по логину
    @Test
    public void findIdByLoginTest() throws Exception {
        mockMvc.perform(get(URL+"/getId/{login}", "test2")).andExpect(status().isFound());
    }

    //Тестирование блокировки
    @Test
    public void blockUserTest() throws Exception{
        mockMvc.perform(put(URL+ "/block/{id}", 2L).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    //Тестирование аутентификации
    @Test
    public void authenticateWithTokenTest() throws Exception{
        AccountCredentials credentials = new AccountCredentials("ADMIN", "admin");
        String requestJson = mapper.writeValueAsString(credentials);
        mockMvc.perform(post(URL+ "/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk());
    }
    //Тестирование получения прав. КАК ПОЛУЧИТЬ ДЕЙСТВИТЕЛЬНЫЙ ТОКЕН
    @Test
    public void getPermissionsTest() throws Exception{
        AccountCredentials credentials = new AccountCredentials("ADMIN", "admin");
        String token = profileService.authenticate(credentials);
        String requestJson = mapper.writeValueAsString(token);
        mockMvc.perform(post(URL + "/getPermissions").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk());
    }

    //Тестирование обновления
    @Test
    public void updateTest() throws Exception {
        Profile profile = new Profile("test6", "321",true);
        String requestJson = mapper.writeValueAsString(profile);
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
