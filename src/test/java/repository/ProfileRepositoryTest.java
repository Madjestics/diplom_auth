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
import diplom.auth.data.entity.Profile;
import diplom.auth.data.entity.Role;
import diplom.auth.data.entity.Permission;
import diplom.auth.data.jpa.ProfileRepository;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfigRepository.class})
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Before
    public void createProfileTest(){
        Profile profile = new Profile( "User6", "qwerty12", true);
        profileRepository.save(profile);
    }

    @Test
    public void findAllTest() {
        List<Profile> profiles = profileRepository.findAll();
        Assert.assertNotNull(profiles);
        Assert.assertEquals(profiles.size(), 6);
    }

    @Test
    public void findByLoginTest() {
        Profile profile = profileRepository.findByUsername("ADMIN");
        Assert.assertNotNull(profile);
        Assert.assertEquals(profile.getUsername(), "ADMIN");
    }

    @Test
    public void findByIsEnabledTrueTest() {
        List<Profile> profiles = profileRepository.findByIsEnabledTrue();
        Assert.assertNotNull(profiles);
        Assert.assertEquals(profiles.size(), 4);
    }

    @Test
    public void getRolesByProfileIdTest() {
        List<Role> roles = profileRepository.getRolesByProfileId(1L);
        Assert.assertNotNull(roles);
        Assert.assertEquals(roles.size(), 2);
    }

    @Test
    public void getPermissionsByProfileIdTest() {
        Set<Permission> permissions = profileRepository.getPermissionsByProfileLogin("ADMIN");
        Assert.assertNotNull(permissions);
        Assert.assertEquals(permissions.size(), 15);
    }

    @After
    public void deleteProfileTest() {
        Profile profile = profileRepository.findByUsername("User6");
        profileRepository.delete(profile);
        List<Profile> profiles = profileRepository.findAll();
        Assert.assertNotNull(profiles);
        Assert.assertEquals(profiles.size(), 5);
    }
}
