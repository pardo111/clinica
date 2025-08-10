package clinica.version_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import clinica.version_java.models.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    
}
