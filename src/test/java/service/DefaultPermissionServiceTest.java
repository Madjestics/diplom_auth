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
import diplom.auth.business.services.DefaultPermissionService;
import diplom.auth.data.entity.Permission;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfigService.class})
public class DefaultPermissionServiceTest {
    @Autowired
    private DefaultPermissionService defaultPermissionService;

    @Before
    public void createPermission(){
        defaultPermissionService.createPermission(new Permission(16L, "TestPermission", "test.test"));
        List<Permission> permissions = defaultPermissionService.findAll();
        Assert.assertEquals(permissions.size(), 16);
    }
    @Test
    public void findAllTest(){
        List<Permission> permissions = defaultPermissionService.findAll();
        Assert.assertEquals(permissions.size(), 16);
    }

    @Test
    public void findByIdTest(){
        Permission permission = defaultPermissionService.findById(1L);
        Assert.assertEquals(permission.getId(), (Long) 1L);
        Assert.assertEquals(permission.getDescription(), "Поиск сканированного текста");
        Assert.assertEquals(permission.getAuthority(), "scan.search");

    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByNullIdTest(){
        defaultPermissionService.findById("");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIncorrectIdTest(){
        defaultPermissionService.findById("A*B-C!D?");
    }

    @Test(expected = NonExistentEntityException.class)
    public void findByNonExistentIdTest(){
        defaultPermissionService.findById((Long)0L);
    }


    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullTest(){
        defaultPermissionService.createPermission(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdTest(){
        defaultPermissionService.createPermission(new Permission(null, "TestPermission", "test.test"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullAuthorityTest(){
        defaultPermissionService.createPermission(new Permission(14L, "TestPermission", null));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createAlreadyExistentAuthorityTest(){
        defaultPermissionService.createPermission(new Permission(14L, "TestPermission", "image.create"));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createExistentPermissionTest(){
        defaultPermissionService.createPermission(new Permission(3L, "TestPermission", "test.test"));
    }

    @Test
    public void updatePermissionTest(){
        defaultPermissionService.updatePermission( new Permission(16L, "TestPermission1", "test.test1"));
        Permission permission = defaultPermissionService.findById(16L);
        Assert.assertEquals(permission.getId(), (Long) 16L);
        Assert.assertEquals(permission.getDescription(), "TestPermission1");
        Assert.assertEquals(permission.getAuthority(), "test.test1");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullTest(){
        defaultPermissionService.updatePermission(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullIdTest(){
        defaultPermissionService.updatePermission(new Permission(null, "TestPermission", "test.test2"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void updateNullAuthorityTest(){
        defaultPermissionService.updatePermission(new Permission(16L, "TestPermission", null));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void updateAlreadyExistentAuthorityTest(){
        defaultPermissionService.updatePermission(new Permission(16L, "TestPermission", "scan.search"));
    }

    @Test(expected = NonExistentEntityException.class)
    public void updateNonExistentPermissionTest(){
        defaultPermissionService.updatePermission(new Permission(17L, "TestPermission1", "test.test1"));
    }

    @Test(expected = NonExistentEntityException.class)
    public void deleteNonExistentPermissionTest(){
        defaultPermissionService.deletePermission((Long) 17L);
    }

    @After
    public void deletePermissionTest(){
        defaultPermissionService.deletePermission((Long) 16L);
        List<Permission> permissions = defaultPermissionService.findAll();
        Assert.assertEquals(permissions.size(), 15);
    }
}
