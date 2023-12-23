package diplom.auth.business.services.impl;

import diplom.auth.data.entity.Profile;
import org.springframework.security.provisioning.UserDetailsManager;
import diplom.auth.web.jwt.AccountCredentials;

import java.util.List;
import java.util.Set;

//Скорее всего не нужен т.к. DefaultProfileService уже имет интерфейс UserDetailsManager, но в нем нет некоторыз методов.
public interface ProfileService extends UserDetailsManager {

    List<Profile> findAll();

    Profile findById(Object id);

    Long getIdByLogin(String login);

    String getLoginById(Object id);

    String authenticate(AccountCredentials credentials);

    Set<String> getAuthorities(String token);

    void blockUser(Long id);

}
