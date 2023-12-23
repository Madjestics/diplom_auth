package service;

import config.TestConfigService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import diplom.auth.business.exceptions.EntityAlreadyExistsException;
import diplom.auth.business.exceptions.EntityIllegalArgumentException;
import diplom.auth.business.exceptions.InvalidTokenException;
import diplom.auth.business.exceptions.NonExistentEntityException;
import diplom.auth.business.services.DefaultProfileService;
import diplom.auth.data.entity.Profile;
import diplom.auth.web.jwt.AccountCredentials;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfigService.class})
public class DefaultProfileServiceTest {
    @Autowired
    DefaultProfileService defaultProfileService;
    @Before
    public void createUserTest(){
        defaultProfileService.createUser(new Profile("test6", "123",true));
    }
    @Test
    public void findAllTest(){
        List<Profile> profiles = defaultProfileService.findAll();
        Assert.assertEquals(profiles.size(), 6);
    }

    @Test
    public void findByIdTest(){
        Profile profile = defaultProfileService.findById(1L);
        Assert.assertEquals(profile.getId(), (Long) 1L);
        Assert.assertEquals(profile.getUsername(), "ADMIN");
        Assert.assertEquals(profile.isEnabled(),true);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByNullIdTest(){
        defaultProfileService.findById("");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIncorrectIdTest(){
        defaultProfileService.findById("A*B-C!D?");
    }

    @Test(expected = NonExistentEntityException.class)
    public void findByNonExistentIdTest(){
        defaultProfileService.findById((Long)0L);
    }


    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullTest(){
        defaultProfileService.createUser(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullUsernameTest(){
        defaultProfileService.createUser(new Profile(null, "123",true));
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullPasswordTest(){
        defaultProfileService.createUser(new Profile("test6", null,true));
    }
    @Test(expected = EntityAlreadyExistsException.class)
    public void createAlreadyExistentUserTest(){
        defaultProfileService.createUser(new Profile("ADMIN", "admin", true));
    }
    @Test
    public void updateTest(){defaultProfileService.updateUser(new Profile("test6", "321",true));}
    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullTest(){
        defaultProfileService.updateUser(null);
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullUsernameTest(){
        defaultProfileService.updateUser(new Profile(null, "123",true));
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullPasswordTest(){
        defaultProfileService.updateUser(new Profile("test6", null,true));
    }
    @Test(expected = NonExistentEntityException.class)
    public void updateNonExistentUserTest(){
        defaultProfileService.updateUser(new Profile("test99", "0987", true));
    }
    @Test(expected = NonExistentEntityException.class)
    public void deleteNonExistentUsernameTest(){
        defaultProfileService.deleteUser("test8");
    }

    //Тестирование авторизации
    @Test
    public void authenticateTest() {
        defaultProfileService.authenticate(new AccountCredentials("ADMIN", "admin"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void authenticateNullTest(){
        defaultProfileService.authenticate(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void authenticateNullLoginTest(){
        defaultProfileService.authenticate(new AccountCredentials(null, "admin"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void authenticateNullPasswordTest(){
        defaultProfileService.authenticate(new AccountCredentials("ADMIN", null));
    }
    @Test(expected = NonExistentEntityException.class)
    public void authenticateWrongLoginTest() {
        defaultProfileService.authenticate(new AccountCredentials("admin1", "admin"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void authenticateWrongPasswordTest() {
        defaultProfileService.authenticate(new AccountCredentials("ADMIN", "admin1"));
    }

    //Тестирование получения полномочии
    @Test
    public void getAuthoritiesTest(){
        String token = defaultProfileService.authenticate(new AccountCredentials("ADMIN", "admin"));
        Set<String> authorities = defaultProfileService.getAuthorities(token);
    }

    @Test(expected = InvalidTokenException.class)
    public void getAuthoritiesByNullTest(){
        Set<String> authorities = defaultProfileService.getAuthorities(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void getAuthoritiesByNullLoginTest(){
        String token = defaultProfileService.authenticate(new AccountCredentials(null, "admin"));
        defaultProfileService.getAuthorities(token);
    }

    //Тестирование блокировки пользователя
    @Test
    public void blockUserTest() {
        defaultProfileService.blockUser(2L);
        Profile profile = defaultProfileService.findById(2L);
        Assert.assertEquals(profile.isEnabled(), false);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void blockNullIdUserTest() {
        defaultProfileService.blockUser(null);
    }

    @Test(expected = NonExistentEntityException.class)
    public void blockNonExistentUser() {
        defaultProfileService.blockUser(20L);
    }

    @After
    public void deleteUserTest(){
        defaultProfileService.deleteUser("test6");
    }
}
