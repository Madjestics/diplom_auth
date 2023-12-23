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
import diplom.auth.data.entity.Permission;
import diplom.auth.data.jpa.PermissionRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfigRepository.class})
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Before
    public void createRoleTest(){
        Permission permission = new Permission(16L, "Разблокировать пользователя", "profile.unblock");
        permissionRepository.save(permission);
    }

    @Test
    public void findAllTest() {
        List<Permission> permissions = permissionRepository.findAll();
        Assert.assertNotNull(permissions);
        Assert.assertEquals(permissions.size(), 16);
    }

    @Test
    public void findByAuthorityTest(){
        Permission permission = permissionRepository.findByAuthority("scan.search");
        Assert.assertNotNull(permission);
        Assert.assertEquals(permission.getId(), (Long)1L);
    }

    @After
    public void deleteRoleTest(){
        permissionRepository.delete(16L);
        List<Permission> permissions = permissionRepository.findAll();
        Assert.assertNotNull(permissions);
        Assert.assertEquals(permissions.size(), 15);
    }
}
