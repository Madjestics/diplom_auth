package diplom.auth.business.services;

import diplom.auth.business.exceptions.EntityAlreadyExistsException;
import diplom.auth.business.exceptions.EntityIllegalArgumentException;
import diplom.auth.business.exceptions.InvalidTokenException;
import diplom.auth.business.exceptions.NonExistentEntityException;
import diplom.auth.data.entity.Permission;
import diplom.auth.data.entity.Profile;
import diplom.auth.data.entity.Role;
import diplom.auth.data.jpa.ProfileRepository;
import diplom.auth.data.jpa.RoleRepository;
import diplom.auth.web.jwt.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import diplom.auth.business.services.impl.ProfileService;
import diplom.auth.web.jwt.AccountCredentials;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DefaultProfileService implements ProfileService {

    private final ProfileRepository profileRepository;

    private final RoleRepository roleRepository;

    ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);

    @Autowired
    public DefaultProfileService(ProfileRepository profileRepository, RoleRepository roleRepository) {
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
    }


    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile findById(Object id) {
        Profile profile;
        if (id == ""){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Long parsedId;
        try{
            parsedId = Long.parseLong(id.toString());
        } catch (NumberFormatException ex){
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к нужному типу, текст ошибки: %s", ex));
        }
        profile = profileRepository.findOne(parsedId);
        if (profile == null){
            throw new NonExistentEntityException(String.format("Профиль с id %d не существует", parsedId));
        }
        return profile;
    }

    //Добавил новый метод
    private Profile findByLogin(String login) {
        Profile profile;
        if (login == null){
            throw new EntityIllegalArgumentException("Логин пользователя не может быть null");
        }
        profile = profileRepository.findByUsername(login);
        if (profile == null){
            throw new NonExistentEntityException(Profile.TYPE_NAME, login);
        }
        return profile;
    }

    public Long getIdByLogin(String login) {
        Profile profile = findByLogin(login);
        return profile.getId();
    }

    public String getLoginById(Object id) {
        Profile profile = findById(id);
        return profile.getUsername();
    }

    @Override
    public void createUser(UserDetails userDetails) {
        checkProfile(userDetails);
        Profile existedProfile = profileRepository.findByUsername(userDetails.getUsername());
        if (existedProfile != null){
            throw new EntityAlreadyExistsException(Profile.TYPE_NAME, existedProfile.getUsername());
        }
        String encodedPassword = passwordEncoder.encodePassword(userDetails.getPassword(), null);
        Profile profile = new Profile(userDetails.getUsername(), encodedPassword, userDetails.isEnabled());
        Role userRole = roleRepository.findByName("USER");
        profile.setRoles(Collections.singleton(userRole));
        profileRepository.save(profile);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        checkProfile(userDetails);
        Profile existedProfile = profileRepository.findByUsername(userDetails.getUsername());
        if (existedProfile == null){
            throw new NonExistentEntityException(Profile.TYPE_NAME, userDetails.getUsername());
        }
        String encodedPassword = passwordEncoder.encodePassword(userDetails.getPassword(), null);
        Profile profile = new Profile(existedProfile.getId(), userDetails.getUsername(), encodedPassword, userDetails.isEnabled());
        Role userRole = roleRepository.findByName("USER");
        profile.setRoles(Collections.singleton(userRole));
        profileRepository.save(profile);
    }

    private void checkProfile(UserDetails userDetails) {
        if (userDetails==null){
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if (userDetails.getUsername()==null){
            throw new EntityIllegalArgumentException("Имя профиля не может быть null");
        }
        if (userDetails.getPassword()==null){
            throw new EntityIllegalArgumentException("Пароль пользователя не может быть null");
        }
    }

    @Override
    public void deleteUser(String login) {
        Profile profile = profileRepository.findByUsername(login);
        if (profile == null) {
            throw new NonExistentEntityException(Profile.TYPE_NAME, login);
        }
        profileRepository.delete(profile);
    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String login) {
        Profile profile = profileRepository.findByUsername(login);
        return profile != null;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return  findByLogin(login);
    }

    public String authenticate(AccountCredentials credentials) {
        if (credentials==null ){
            throw new EntityIllegalArgumentException("credentials can't be null");
        }
        if (credentials.getLogin()==null  || credentials.getLogin().equals("")){
            throw new EntityIllegalArgumentException("login can't be null");
        }
        if (credentials.getPassword()==null || credentials.getPassword().equals("")){
            throw new EntityIllegalArgumentException("password can't be null");
        }
        Profile profile = profileRepository.findByUsername(credentials.getLogin());
        if (profile==null) {
            throw new NonExistentEntityException(Profile.TYPE_NAME, credentials.getLogin());
        }
        String encodedPassword = passwordEncoder.encodePassword(credentials.getPassword(), null);
        if (!profile.getPassword().equals(encodedPassword)) {
            throw new EntityIllegalArgumentException("Введен неверный пароль");
        }
        return TokenAuthenticationService.generateToken(profile.getUsername());
    }

    @Override
    public Set<String> getAuthorities(String token) {
        if (token == null || token.equals("")) {
            throw new InvalidTokenException("Токен не может быть null или пустой строкой");
        }
        String login = TokenAuthenticationService.getUsername(token);
        if (login == null) {
            throw new InvalidTokenException("Логин в токене не может быть null");
        }
        Set<Permission> permissions = profileRepository.getPermissionsByProfileLogin(login);
        Set<String> authorities = new HashSet<>();
        for (Permission permission : permissions) {
            authorities.add(permission.getAuthority());
        }
        return authorities;
    }

    @Override
    public void blockUser(Long id) {
        if (id==null ){
            throw new EntityIllegalArgumentException("id can't be null");
        }
        Profile profile = profileRepository.findOne(id);
        if (profile==null) {
            throw new NonExistentEntityException(Profile.TYPE_NAME, id);
        }
        profile.setEnabled(false);
        profileRepository.save(profile);
    }
}
