package com.cursos.spring.security.entity;

import com.cursos.spring.security.util.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table( name = "\"user\"" )
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String username;

    private String name;

    private String password;

    @Enumerated( EnumType.STRING )
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List< GrantedAuthority > authorities = role
                .getPermissions()
                .stream()
                .map( permission -> new SimpleGrantedAuthority( permission.name() ) )
                .collect( Collectors.toList() );

        authorities.add( new SimpleGrantedAuthority( "ROLE_" + role.name() ) );

        return authorities;

    }

    @Override
    public boolean isAccountNonExpired() {
        // return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        // return UserDetails.super.isEnabled();
        return true;
    }

}