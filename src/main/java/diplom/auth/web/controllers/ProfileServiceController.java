package diplom.auth.web.controllers;

import diplom.auth.data.entity.Profile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import diplom.auth.business.services.impl.ProfileService;
import diplom.auth.web.jwt.AccountCredentials;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/profile")
@Tag(name = "Контроллер профилей", description = "Позволяет работать с профилями (добавлять, удалять, обновлять, " +
        "искать, производить вход в аккаунт, выдавать привилегии пользователя по токену аутентификации)")
public class ProfileServiceController {

    private final ProfileService profileService;

    @Autowired
    public ProfileServiceController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Найти профиль по id")
    public Profile findById(@PathVariable String id) {
        return profileService.findById(id);
    }

    @GetMapping
    @Operation(description = "Найти все профили")
    public List<Profile> findAll() {
        return profileService.findAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать новый профиль (обязательны для заполнение только 'username', 'password');" +
            "остальное можно оставить по умолчанию (доступность - true, роль автоматически задается USER)")
    //@PreAuthorize("hasPermission('profile', 'add')")
    public Profile addProfile(@RequestBody Profile profile) {
        profileService.createUser(profile);
        return profile;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить данные существующего профиля")
    @PreAuthorize("hasPermission('profile', 'update')")
    public Profile updateProfile(@RequestBody Profile profile) {
        profileService.updateUser(profile);
        return profile;
    }

    @GetMapping("/getId/{login}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Получить id пользователя по его логину")
    public Long getIdByLogin(@PathVariable String login) {
        return profileService.getIdByLogin(login);
    }


    @GetMapping("/getLogin/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Получить имя пользователя по id профиля")
    public String getLoginById(@PathVariable String id) {
        return profileService.getLoginById(id);
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить профиль по его логину")
    @PreAuthorize("hasPermission('profile', 'delete')")
    public void deleteProfile(@PathVariable String login) {
        profileService.deleteUser(login);
    }


    //Новые методы, надо протестировать
    @PutMapping("/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Заблокировать пользователя по его id")
    @PreAuthorize("hasPermission('profile', 'block')")
    public void blockUser(@PathVariable Long id) {
        profileService.blockUser(id);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Авторизация с получением токена (для авторизации админа - 'login': ADMIN, 'password': admin ")
    public String authenticateWithToken(@RequestBody AccountCredentials credentials) {
        return profileService.authenticate(credentials);
    }

    @PostMapping("/getPermissions")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получить права из токена")
    public Set<String> getPermissions(@RequestBody String token) {
        return profileService.getAuthorities(token);
    }
}
