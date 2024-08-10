package com.cursos.spring.security.service;

import com.cursos.spring.security.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value( "${security.jwt.secret_key}" )
    private String SECRET_KEY;

    @Value( "${security.jwt.expiration-in-minutes}" )
    private long EXPIRATION_IN_MINUTES;

    public String generateToken( User user, Map<String, Object> extraClaims ) {

        Date issuedAt = new Date( System.currentTimeMillis() );
        Date expiration = new Date( ( EXPIRATION_IN_MINUTES * 60 * 1000 ) + issuedAt.getTime() );

        return Jwts
                .builder()
                .header()
                .type( "JWT" )
                .and()
                .subject( user.getUsername() )
                .issuedAt( issuedAt )
                .expiration( expiration )
                .claims( extraClaims )

                .signWith( generateKey(), Jwts.SIG.HS256 )

                .compact();

    }

    private SecretKey generateKey() {

        byte[] keyDecoded = Decoders.BASE64.decode( SECRET_KEY );

        return Keys.hmacShaKeyFor( keyDecoded );

    }

    public String extractUsername( String jwt ) {
        return extractAllClaims( jwt ).getSubject();
    }

    private Claims extractAllClaims( String jwt ) {

        return Jwts
                .parser()
                .verifyWith( generateKey() )
                .build()
                .parseSignedClaims( jwt )
                .getPayload();

    }

}