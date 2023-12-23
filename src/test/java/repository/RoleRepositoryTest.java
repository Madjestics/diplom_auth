package repository;

import config.TestConfigRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import diplom.auth.data.entity.Role;
import diplom.auth.data.entity.Permission;
import diplom.auth.data.jpa.RoleRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfigRepository.class})
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void createRoleTest(){
        Role role = new Role(3L, "BlockedUser", "Роль заблокированного пользователя");
        roleRepository.save(role);
    }

    @Test
    public void findAllTest() {
        List<Role> roles = roleRepository.findAll();
        Assert.assertNotNull(roles);
        Assert.assertEquals(roles.size(), 3);
    }

    @Test
    public void findByNameTest(){
        Role role = roleRepository.findByName("ADMIN");
        Assert.assertNotNull(role);
        Assert.assertEquals(role.getId(), (Long) 1L);
    }

    @Test
    public void getPermissionsByRoleIdTest(){
        List<Permission> permissions = roleRepository.getPermissionByRoleId(2L);
        Assert.assertNotNull(permissions);
        Assert.assertEquals(permissions.size(), 3);
    }

    @After
    public void deleteRoleTest(){
        roleRepository.delete(3L);
        List<Role> roles = roleRepository.findAll();
        Assert.assertNotNull(roles);
        Assert.assertEquals(roles.size(), 2);
    }

}
