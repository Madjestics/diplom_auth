package diplom.auth.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import diplom.auth.business.services.impl.PermissionService;
import diplom.auth.data.entity.Permission;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@Tag(name = "Permission Controller", description = "Управление привилегиями")
public class PermissionServiceController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionServiceController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Найти привилегию по её id")
    public Permission findById(@PathVariable String id) {
        return permissionService.findById(id);
    }

    @GetMapping
    @Operation(description = "Найти все привилегии")
    public List<Permission> findAll() {
        return permissionService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать новую привилегию")
    @PreAuthorize("hasPermission('permission', 'add')")
    public Permission addPermission(@RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить существующую привилегию")
    @PreAuthorize("hasPermission('permission', 'update')")
    public Permission updatePermission(@RequestBody Permission permission) {
        return permissionService.updatePermission(permission);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить привилегию по её id")
    @PreAuthorize("hasPermission('permission', 'delete')")
    public void deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
    }
}
