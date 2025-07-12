package com.thienan.auth_service.token;

import com.thienan.auth_service.account.Account;
import com.thienan.auth_service.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token extends BaseEntity{
    private String token;
    @Default
    private TokenType tokenType = TokenType.BEARER;
    private boolean isRevoked;
    private boolean isExpired;
    @ManyToOne(optional=false)
    @JoinColumn(name = "account_id")
    private Account account;
}
