package diplom.auth.business.services;

import diplom.auth.business.exceptions.EntityAlreadyExistsException;
import diplom.auth.business.exceptions.EntityIllegalArgumentException;
import diplom.auth.data.entity.Role;
import diplom.auth.data.jpa.PermissionRepository;
import diplom.auth.data.jpa.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import diplom.auth.business.exceptions.NonExistentEntityException;
import diplom.auth.business.services.impl.RoleService;

import java.util.List;

@Service
public class DefaultRoleService  implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Autowired
    public DefaultRoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Object id) {
        Role role;
        if (id == ""){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Long parsedId;
        try{
            parsedId = Long.valueOf((String) id.toString());
        } catch (NumberFormatException ex){
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к нужному типу, текст ошибки: %s", ex));
        }
        role = roleRepository.findOne(parsedId);
        if (role == null){
            throw new NonExistentEntityException(String.format("Роль с id %d не существует", parsedId));
        }
        return role;
    }

    public Role createRole(Role role) {
        checkRole(role);
        Role existedRoleById = roleRepository.findOne(role.getId());
        if (existedRoleById != null) {
            throw new EntityAlreadyExistsException(String.format("Роль с id %d уже существует", existedRoleById.getId()));
        }
        Role existedRoleByName = roleRepository.findByName(role.getName());
        if (existedRoleByName != null) {
            throw new EntityAlreadyExistsException(Role.TYPE_NAME, existedRoleByName.getName());
        }
        return roleRepository.save(role);
    }

    public Role updateRole(Role role) {
        checkRole(role);
        Role existedRoleById = roleRepository.findOne(role.getId());
        if (existedRoleById == null) {
            throw new NonExistentEntityException(String.format("Роль с id %d не существует", role.getId()));
        }
        return roleRepository.save(role);
    }

    public void deleteRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new NonExistentEntityException(Role.TYPE_NAME, roleName);
        }
        roleRepository.delete(role.getId());
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findOne(id);
        if (role == null) {
            throw new NonExistentEntityException(Role.TYPE_NAME, id);
        }
        roleRepository.delete(role.getId());
    }

    private void checkRole(Role role) {
        if (role==null){
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if (role.getId()==null) {
            throw new EntityIllegalArgumentException("id роли не может быть null");
        }
        if (role.getName()==null) {
            throw new EntityIllegalArgumentException("Имя роли не может быть null");
        }

    }
}
