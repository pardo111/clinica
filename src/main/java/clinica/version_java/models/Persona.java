package clinica.version_java.models;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Sexo;
import clinica.version_java.models.enums.TipoPersona;
import jakarta.persistence.Column;

import lombok.Data;

@Entity
@Data
@Table(name="persona")
public class Persona {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_persona")
    private int idPersona;
    @Column(name="nombres")
    private String nombres;
    @Column(name="apellidos")
    private String apellidos;
    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column(name="direccion", length = 500)
    private String direccion;
    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private Estado estado= Estado.ACTIVO;
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo ;
    @Column(name = "dui", length = 10 , nullable = false)
    private String dui;
    @Enumerated(EnumType.STRING)
    @Column(name= "tipo")
    private TipoPersona tipoPersona;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    


    //RELACIONES
    @OneToMany(mappedBy = "persona")
    private List<CorreoPersona> correoPersona;
    @OneToMany(mappedBy = "persona")
    private List<TelefonoPersona> telefonoPersona;
    @OneToMany(mappedBy = "contacto")
    private List<ContactoEmergencia> contacto;
    @OneToMany(mappedBy = "paciente")
    private List<ContactoEmergencia> pacienteContacto;
    @OneToMany(mappedBy = "paciente")
    private List<AntecedentesFamiliares> pacienteFamiliar;
    @OneToMany(mappedBy = "familiar")
    private List<AntecedentesFamiliares> familiar;
    @OneToOne(mappedBy = "persona")
    private Usuarios usuarios;
    @OneToMany(mappedBy = "paciente")
    private List<Citas> paciente;
    @OneToMany(mappedBy = "medico")
    private List<Citas> medico;
}
