package diplom.auth.data.jpa;

import diplom.auth.data.entity.Permission;
import diplom.auth.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Query(value = "select distinct p from Permission p join p.roles pr where pr.id = :roleId")
    List<Permission> getPermissionByRoleId(@Param("roleId") Long roleId);
}
