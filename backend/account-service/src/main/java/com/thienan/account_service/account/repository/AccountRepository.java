package com.thienan.account_service.account.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.thienan.account_service.account.dto.AccountDetail;
import com.thienan.account_service.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
    
    @Query("""
        select new com.thienan.account_service.account.dto.AccountDetail(
            a.email,
            a.password,
            c.fullname
        )
        from Account a
        join a.customer c
        where a.email = :email
    """)
    Optional<AccountDetail> findByEmail(String email);
}
