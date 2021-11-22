package com.karenhoffman.api.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.karenhoffman.api.security.entities.Rol;
import com.karenhoffman.api.security.enums.RolName;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByRolName(RolName rolName);
}
