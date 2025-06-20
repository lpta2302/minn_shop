package com.thienan.auth_service.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long>{
    @Query("""
        select new com.thienan.auth_service.account.AccountDetail(
            a.email,
            a.password,
            a.fullname
        )
        from Account a
        where a.email = :email
    """)
    Optional<AccountDetail> findByEmail(String email);
}
