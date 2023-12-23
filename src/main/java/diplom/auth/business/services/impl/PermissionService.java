package diplom.auth.business.services.impl;

import diplom.auth.data.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();
    Permission findById(Object id);
    Permission createPermission(Permission permission);
    Permission updatePermission(Permission permission);
    void deletePermission(Long id);

}
