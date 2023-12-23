package diplom.auth.data.jpa;

import diplom.auth.data.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByAuthority(String authority);
}
