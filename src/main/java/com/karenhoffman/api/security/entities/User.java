package com.karenhoffman.api.security.entities;

import javax.persistence.*;
import org.springframework.lang.NonNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    private String password;
    @NonNull
    @ManyToMany
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Rol> roles = new HashSet<>();

    public User() {
    }


    public User( @NonNull String email, 
                 @NonNull String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }
    
	public void setEmail(String email) {
		this.email = email;
	}
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }


	public Long getId_user() {
		return id_user;
	}


	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}


	@Override
	public int hashCode() {
		return Objects.hash(email, id_user, password, roles);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(id_user, other.id_user)
				&& Objects.equals(password, other.password) && Objects.equals(roles, other.roles);
	}

	
	


	}


	





    
