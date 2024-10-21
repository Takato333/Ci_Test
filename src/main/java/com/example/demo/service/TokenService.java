package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    AccountRepository accountRepository;

    public final String SECRET_KEY = "vanphuquocthinh3444thihoaminhtrang3464nguyennhattruong3428dangkienninh3802";

    private SecretKey getSignKey(){
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //tạo ra token
    public String generateToken(Account account) {
        String token = Jwts.builder()
                .subject(account.getId()+"")
                .issuedAt(new Date(System.currentTimeMillis())) //10:30
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey())
                .compact();
        return token;
    }

    //verify token
    public Account getAccountByToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String idString = claims.getSubject();
        long id = Long.parseLong(idString);
        return accountRepository.findAccountById(id);
    }

}
