package diplom.auth.business.security;

import diplom.auth.data.entity.Permission;
import diplom.auth.data.entity.Profile;
import diplom.auth.data.jpa.PermissionRepository;
import diplom.auth.data.jpa.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import diplom.auth.business.exceptions.InvalidTokenException;
import diplom.auth.business.exceptions.UnavailableOperationException;

import java.io.Serializable;
import java.util.Set;

@Component
public class DefaultPermissionEvaluator implements PermissionEvaluator {

    PermissionRepository permissionRepository;

    ProfileRepository profileRepository;

    @Autowired
    public DefaultPermissionEvaluator(PermissionRepository permissionRepository, ProfileRepository profileRepository) {
        this.permissionRepository = permissionRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object resource, Object action) {
        if (authentication==null) {
            throw new InvalidTokenException("Токен не может быть null");
        }
        Profile user = (Profile) authentication.getPrincipal();
        String authority = resource+"."+action;
        Permission requiredPermission = permissionRepository.findByAuthority(authority);
        Set<Permission> permissions = profileRepository.getPermissionsByProfileLogin(user.getUsername());
        if (!permissions.contains(requiredPermission)){
            throw new UnavailableOperationException(authority);
        }
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
