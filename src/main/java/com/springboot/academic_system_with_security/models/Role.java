package com.springboot.academic_system_with_security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.academic_system_with_security.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_ROLE")
@AllArgsConstructor
@Getter @Setter @ToString(exclude = "users")
//@EqualsAndHashCode(exclude = "users")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.REMOVE)
    private List<UserEntity> users= new ArrayList<>();

    public Role(){
    }

    @Override
    public String getAuthority() {
        return roleName.toString();
    }
}
