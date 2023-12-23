package diplom.auth.data.entity;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission")
@NoArgsConstructor
public class Permission implements GrantedAuthority  {

    public static String TYPE_NAME = "Привилегия";

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column (name = "description", nullable = true)
    private String description;

    @Column (name = "granted_authority", nullable = false)
    private String authority;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public Permission(Long id, String description, String authority) {
        this.id = id;
        this.description = description;
        this.authority = authority;
    }

    public Permission(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
