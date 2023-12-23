package diplom.auth.web.controllers;


import diplom.auth.data.entity.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import diplom.auth.business.services.impl.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@Tag(name = "Контроллер ролей", description = "Позволяет работать с ролями (добавлять, удалять, обновлять, искать)")
public class RoleServiceController {

    private final RoleService roleService;

    @Autowired
    public RoleServiceController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Найти роль по её id")
    public Role findById(@PathVariable String id) {
        return roleService.findById(id);
    }

    @GetMapping
    @Operation(description = "Найти все роли")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Добавить новую роль (можно сразу задавать привилегии, указывая их id)")
    @PreAuthorize("hasPermission('role', 'add')")
    public Role addRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить существующую роль (можно сразу задавать привилегии, указывая их id)")
    @PreAuthorize("hasPermission('role', 'update')")
    public Role updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить роль по её имени")
    @PreAuthorize("hasPermission('role', 'delete')")
    public void deleteRole(@PathVariable String name) {
        roleService.deleteRole(name);
    }

}
