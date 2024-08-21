package dev.minn_shop.minn_shop.user;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.minn_shop.minn_shop.BaseEntity;
import dev.minn_shop.minn_shop.security.token.Token;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class User extends BaseEntity implements UserDetails, Principal {
    @Size(min = 6, max = 20, message = "Username must be 6-20 character")
    @Column(unique = true)
    private String username;

    @Size(min = 6, message = "Password must be at least 6 character")
    private String password;

    @Size(min = 2, max = 10, message = "First name must be 2-10 character")
    private String firstName;

    @Size(min = 2, max = 10, message = "Last name must be 2-10 character")
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phoneNumber;

    private Date birthDate;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;

    private boolean accountLocked = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy="user", cascade=CascadeType.REMOVE, orphanRemoval=true)
    private Collection<Token> tokens;

    public String fullname() {
        return firstName + " " + lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName(){
        return fullname();
    }

}
