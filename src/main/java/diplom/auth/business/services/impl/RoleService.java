package diplom.auth.business.services.impl;

import diplom.auth.data.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Object id);
    Role createRole(Role role);
    Role updateRole(Role role);
    void deleteRole(String roleName);
}
