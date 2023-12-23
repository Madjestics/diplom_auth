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
import diplom.auth.business.exceptions.NonExistentEntityException;
import diplom.auth.business.services.DefaultRoleService;
import diplom.auth.data.entity.Role;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfigService.class})
public class DefaultRoleServiceTest {
    @Autowired
    private DefaultRoleService defaultRoleService;
    @Before
    public void createRoleTest(){
        defaultRoleService.createRole(new Role(3L, "test", "TestRole"));
    }
    @Test
    public void findAllTest(){
        List<Role> roles = defaultRoleService.findAll();
        Assert.assertEquals(roles.size(), 3);
    }
    @Test
    public void findByIdTest(){
        Role role = defaultRoleService.findById(3L);
        Assert.assertEquals(role.getId(), (Long) 3L);
        Assert.assertEquals(role.getName(), "test");
        Assert.assertEquals(role.getDescription(), "TestRole");
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void findByNullIdTest(){
        defaultRoleService.findById("");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIncorrectIdTest(){
        defaultRoleService.findById("A*B-C!D?");
    }

    @Test(expected = NonExistentEntityException.class)
    public void findByNonExistentIdTest(){
        defaultRoleService.findById((Long)0L);
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullTest(){
        defaultRoleService.createRole(null);
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdTest(){
        defaultRoleService.createRole(new Role(null, "test2", "TestRole"));
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullNameTest(){
        defaultRoleService.createRole(new Role(4L, null, "TestRole"));
    }
    @Test(expected = EntityAlreadyExistsException.class)
    public void createAlreadyExistentNameTest(){
        defaultRoleService.createRole(new Role(5L, "test", "TestRole"));
    }
    @Test(expected = EntityAlreadyExistsException.class)
    public void createExistentRoleTest(){
        defaultRoleService.createRole(new Role(3L, "test", "TestRole"));
    }
    @Test
    public void updateRoleTest(){
        defaultRoleService.updateRole(new Role(3L, "test", "NewDescription"));
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullTest(){
        defaultRoleService.updateRole(null);
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullIdTest(){
        defaultRoleService.updateRole(new Role(null, "test2", "TestRole"));
    }
    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullNameTest(){
        defaultRoleService.updateRole(new Role(4L, null, "TestRole"));
    }

    public void updateAlreadyExistentNameTest(){
        defaultRoleService.updateRole(new Role(4L, "test", "TestRole"));
    }
    @Test(expected = NonExistentEntityException.class)
    public void updateNonExistentRoleTest(){
        defaultRoleService.updateRole(new Role(0L, "test99", "TestRole"));
    }
    @Test(expected = NonExistentEntityException.class)
    public void deleteNonExistentRoleTest(){
        defaultRoleService.deleteRole("test99");
    }
    /*@After
    public void deleteRoleByIdTest(){
        defaultRoleService.deleteRole(3L);
    }*/
    @After
    public void deleteRoleByNameTest(){defaultRoleService.deleteRole("test");}
}