package clinica.version_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import clinica.version_java.models.ContactoEmergencia;

public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Integer>{
    
}
