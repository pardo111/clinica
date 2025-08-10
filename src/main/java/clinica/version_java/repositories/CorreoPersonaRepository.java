package clinica.version_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import clinica.version_java.models.CorreoPersona;

public interface CorreoPersonaRepository extends JpaRepository<CorreoPersona, Integer>{
    
}
