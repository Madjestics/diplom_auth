package service.mocks;

import org.springframework.security.core.userdetails.UserDetails;
import diplom.auth.business.services.impl.ProfileService;
import diplom.auth.data.entity.Profile;
import diplom.auth.web.jwt.AccountCredentials;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MockProfileService implements ProfileService {
    //я без понятия как этп прописывать
    @Override
    public List<Profile> findAll() {
        return  new ArrayList<>();
    }

    @Override
    public Profile findById(Object id) {
        return null;
    }

    @Override
    public Long getIdByLogin(String login) {
        return null;
    }

    @Override
    public String getLoginById(Object id) {
        return null;
    }

    @Override
    public void createUser(UserDetails userDetails) {

    }
    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String login) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String login) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        return null;
    }

    @Override
    public String authenticate(AccountCredentials credentials) { return "";}

    @Override
    public Set<String> getAuthorities(String token) {
        return null;
    }

    @Override
    public void blockUser(Long id) {

    }


}
