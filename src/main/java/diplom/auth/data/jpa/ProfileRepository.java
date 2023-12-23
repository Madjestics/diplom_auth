package diplom.auth.data.jpa;

import diplom.auth.data.entity.Permission;
import diplom.auth.data.entity.Profile;
import diplom.auth.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUsername(String login);

    List<Profile> findByIsEnabledTrue();

    @Query(value = "select distinct r from Role r left join fetch r.profiles rp where rp.id = :profileId")
    List<Role> getRolesByProfileId(@Param("profileId") Long profileId);

    @Query(value = "select distinct p from Permission p left join fetch p.roles pr left join pr.profiles prof where prof.username = :profileLogin")
    Set<Permission> getPermissionsByProfileLogin(@Param("profileLogin") String profileLogin);

}
