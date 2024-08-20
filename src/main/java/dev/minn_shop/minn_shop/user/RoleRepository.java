package dev.minn_shop.minn_shop.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Integer>{
    public Optional<Role> findByName(RoleType name);
}
