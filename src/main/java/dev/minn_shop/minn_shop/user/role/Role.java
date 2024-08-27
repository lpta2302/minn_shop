package dev.minn_shop.minn_shop.user.role;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.minn_shop.minn_shop.BaseEntity;
import dev.minn_shop.minn_shop.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Role extends BaseEntity{
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users;

}
