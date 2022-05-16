package com.example.ecommercenashtechbackend.configuration;

import com.example.ecommercenashtechbackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActive implements UserDetails {
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> simpleGrantedAuthorityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.simpleGrantedAuthorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
