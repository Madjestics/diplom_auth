package service.mocks;

import diplom.auth.business.services.impl.RoleService;
import diplom.auth.data.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class MockRoleService implements RoleService {
    @Override
    public List<Role> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Role findById(Object id) {
        return new Role(Long.valueOf((String) id), "test", "TestPermission");
    }

    @Override
    public Role createRole(Role role) {
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        return role;
    }

    @Override
    public void deleteRole(String roleName) {

    }
}
