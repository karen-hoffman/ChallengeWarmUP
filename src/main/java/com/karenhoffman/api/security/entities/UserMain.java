package com.karenhoffman.api.security.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserMain implements UserDetails {

    
	private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    // Variable que nos otorga las autorizaciones (no confundir con autenticación)
    private Collection<? extends GrantedAuthority> authorities;

    
    public UserMain(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = email;
        this.password = password;
        this.authorities = authorities;
    }

    //Metodo que asigna los privilegios (autorizaciones)
    public static UserMain build(User usuario){
        //Convertimos la clase Rol a la clase GrantedAuthority
        List<GrantedAuthority> authorities =
                usuario.getRoles()
                        .stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getRolName().name()))
                .collect(Collectors.toList());
        return new UserMain(usuario.getEmail(),
                usuario.getPassword(), authorities);
    }

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public String getUsername() {
		 return this.username;
	}
}