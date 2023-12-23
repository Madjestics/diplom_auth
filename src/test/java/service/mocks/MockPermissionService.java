package service.mocks;

import org.springframework.stereotype.Service;
import diplom.auth.business.services.impl.PermissionService;
import diplom.auth.data.entity.Permission;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockPermissionService implements PermissionService {

    @Override
    public List<Permission> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Permission findById(Object id) {
        return new Permission(Long.valueOf((String) id), "testPermission", "test.test");
    }

    @Override
    public Permission createPermission(Permission permission) {
        return permission;
    }

    @Override
    public Permission updatePermission(Permission permission) {

        return permission;
    }

    @Override
    public void deletePermission(Long id) {
    }
}
