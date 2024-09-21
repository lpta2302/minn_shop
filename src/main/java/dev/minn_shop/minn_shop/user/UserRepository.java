package dev.minn_shop.minn_shop.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);

    @Query("""
            select new dev.minn_shop.minn_shop.user.BriefUserRecord(
            u.id,
            u.username,
            u.firstName,
            u.lastName,
            u.avatar)
            from User u
            """)
    public Page<BriefUserRecord> findUserDetails(Pageable pageable);
}
