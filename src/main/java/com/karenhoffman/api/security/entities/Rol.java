package com.karenhoffman.api.security.entities;

import javax.persistence.*;


import org.springframework.lang.NonNull;
import com.karenhoffman.api.security.enums.RolName;

@Entity
public class Rol {
	
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id_rol;
    @NonNull
    @Enumerated(EnumType.STRING)
    private RolName rolName;

    public Rol() {
    }

    public Rol(@NonNull RolName rol) {
        this.rolName = rol;
    }

    public Long getId() {
        return id_rol;
    }

    public void setId(Long id) {
        this.id_rol = id;
    }

    public RolName getRolName() {
        return rolName;
    }

    public void setRolName(RolName rol) {
        this.rolName = rol;
    }
}