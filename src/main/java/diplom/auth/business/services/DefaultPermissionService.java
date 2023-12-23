package diplom.auth.business.services;

import diplom.auth.business.exceptions.EntityAlreadyExistsException;
import diplom.auth.business.exceptions.EntityIllegalArgumentException;
import diplom.auth.data.entity.Permission;
import diplom.auth.data.jpa.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import diplom.auth.business.exceptions.NonExistentEntityException;
import diplom.auth.business.services.impl.PermissionService;

import java.util.List;

@Service
public class DefaultPermissionService implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public DefaultPermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    public Permission findById(Object id) {
        Permission permission;
        if (id == ""){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Long parsedId;
        try{
            parsedId = Long.valueOf(id.toString());
        } catch (NumberFormatException ex){
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к нужному типу, текст ошибки: %s", ex));
        }
        permission = permissionRepository.findOne(parsedId);
        if (permission == null){
            throw new NonExistentEntityException(String.format("Профиль с id %d не существует", parsedId));
        }
        return permission;
    }

    public Permission createPermission(Permission permission) {
        checkPermission(permission);
        Permission existedPermissionById = permissionRepository.findOne(permission.getId());
        if (existedPermissionById != null) {
            throw new EntityAlreadyExistsException(
                    String.format("Привилегия с id %d уже существует", existedPermissionById.getId()));
        }
        return permissionRepository.save(permission);
    }

    public Permission updatePermission(Permission permission) {
        checkPermission(permission);
        Permission existedPermissionById = permissionRepository.findOne(permission.getId());
        if (existedPermissionById == null){
            throw new NonExistentEntityException(
                    String.format("Привилегия с id %d не существует", permission.getId()));
        }
        return permissionRepository.save(permission);
    }

    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findOne(id);
        if (permission == null) {
            throw new NonExistentEntityException(
                    String.format("Привилегия с id %d не существует", id));
        }
        permissionRepository.delete(permission.getId());
    }


    private void checkPermission(Permission permission) {
        if (permission==null){
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if (permission.getId()==null) {
            throw new EntityIllegalArgumentException("Id привилегии не может быть null");
        }
        if (permission.getAuthority()==null) {
            throw new EntityIllegalArgumentException("Права не могут быть null");
        }
        Permission existedPermissionByAuthority = permissionRepository.findByAuthority(permission.getAuthority());
        if (existedPermissionByAuthority != null) {
            throw new EntityAlreadyExistsException(
                    String.format("Привилегия с правом %s уже существует", existedPermissionByAuthority.getAuthority()));
        }
    }
}
