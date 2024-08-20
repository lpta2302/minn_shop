package dev.minn_shop.minn_shop.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);

    @Query("""
        select new UserDetailRecord(
        u.id, 
        u.username, 
        u.firstName, 
        u.lastName, 
        u.email, 
        u.phoneNumber, 
        u.birthDate, 
        u.avatar)
        from User u
        """)
    public Page<UserDetailRecord> findUserDetails(Pageable pageable);
}
