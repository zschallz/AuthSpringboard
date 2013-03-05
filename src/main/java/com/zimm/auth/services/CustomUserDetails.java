/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zimm.auth.services;

import java.sql.Timestamp;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author zschallz
 */
public class CustomUserDetails implements UserDetails {

    private int userID;
    private String username;
    private String password;
    private String name;
    private String email;
    private Timestamp timestamp;
    private Collection<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String salt;

    public CustomUserDetails(int userID, String username, String password, String name, String email, Timestamp whenCreated, Collection<GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, String salt) {
        this.userID                 = userID;
        this.username               = username;
        this.password               = password;
        this.name                   = name;
        this.email                  = email;
        this.timestamp              = whenCreated;
        this.authorities            = authorities;
        this.accountNonExpired      = accountNonExpired;
        this.accountNonLocked       = accountNonLocked;
        this.credentialsNonExpired  = credentialsNonExpired;
        this.enabled                = enabled;
        this.salt                   = salt;
    }
    
    
    
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }
    
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
}
