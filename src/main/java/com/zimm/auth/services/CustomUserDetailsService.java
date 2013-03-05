/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zimm.auth.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author zschallz
 */
public class CustomUserDetailsService implements UserDetailsService  {
    
    private DataSource dataSource;
    
    public void createNewUser(String username, String password, String name, String email)
    {        
        final String sql = "insert into users (username,password,enabled,salt,email,name) values (:username,:password,:enabled,:salt,:email,:name)";
        String salt = UUID.randomUUID().toString();
        
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String encodedPassword = encoder.encodePassword(password, salt);
        
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("username", username);
        source.addValue("password", encodedPassword); // sha2(password{salt},512)
        source.addValue("name", name);
        source.addValue("email", email);
        source.addValue("enabled", true);
        source.addValue("salt", salt);
        
        
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        
        final String sql = "select user_id,username,password,salt,enabled,name,email,when_created from users where username=:username";
        
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("username", username);
        
        SimpleJdbcTemplate sjt = new SimpleJdbcTemplate(getDataSource());
        List<CustomUserDetails> users = sjt.query(sql, new UserMapper(), source);
        
        if( users.isEmpty() )
        {
            /* TODO: use message properties file */
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        
        /* Querying by username, so only one row will be returned */
        return users.get(0);
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    
    private class UserMapper implements ParameterizedRowMapper<CustomUserDetails> {
 
        @Override
        public CustomUserDetails mapRow(ResultSet rs, int arg1) throws SQLException {
            
            int userID                      = rs.getInt("user_id");
            String username                 = rs.getString("username");
            String password                 = rs.getString("password");
            String name                     = rs.getString("name");
            String email                    = rs.getString("email");
            String salt                     = rs.getString("salt");
            Timestamp whenCreated           = rs.getTimestamp("when_created");
            boolean enabled                 = rs.getBoolean("enabled");
            boolean nonExpired              = true;
            boolean nonLocked               = true;
            boolean credentialsNonLocked    = true;
            
            CustomUserDetails user = 
                    new CustomUserDetails(userID, username, password, name, email,
                    whenCreated, getAuthorities(userID), nonExpired,
                    nonLocked, credentialsNonLocked, enabled, salt);
            
            return user;
        }
        
        private List<GrantedAuthority> getAuthorities(int userID)
        {            
            final String sql = "select user_role_id, user_id, authority from user_roles where user_id=:user_id";
        
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("user_id", userID);

            SimpleJdbcTemplate sjt = new SimpleJdbcTemplate(getDataSource());
            List<GrantedAuthority> roles = sjt.query(sql, new AuthorityMapper(), source);
            
            return roles;
        }
        
        private class AuthorityMapper implements ParameterizedRowMapper<GrantedAuthority> {

            @Override
            public GrantedAuthority mapRow(ResultSet rs, int i) throws SQLException {
                return new GrantedAuthorityImpl(rs.getString("authority"));
            }
            
            
        }
 
    }
    
}
