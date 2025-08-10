
package clinica.version_java.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import clinica.version_java.models.Citas;
import clinica.version_java.models.enums.Estado;

public interface CitasRepository extends JpaRepository<Citas, Integer>{
    Page<Citas> findByCodigoContaining(String codigo,Pageable pag);
    List<Citas> findByCodigo(String codigo);
    List<Citas> findByPaciente(Integer paciente);
    Page<Citas> findByEstado(Estado estado);
    Page<Citas> findByFechaCitaCierre(LocalDateTime fechaInicio, LocalDateTime fechaFinal);
}
