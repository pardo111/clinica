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
import clinica.version_java.DTO.DTOAntecedentesFamiliares;
import clinica.version_java.DTO.DTOPersona;

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
    @Column(name = "sexo", nullable =  false)
    private Sexo sexo ;
    @Column(name = "dui", length = 10 , nullable = false, unique = true)
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






    public Persona(Persona persona) {
        this.nombres = persona.nombres;
        this.apellidos = persona.apellidos;
        this.fechaNacimiento = persona.fechaNacimiento;
        this.direccion = persona.direccion;
        this.sexo = persona.sexo;
        this.dui = persona.dui;
        this.tipoPersona = persona.tipoPersona;
        this.correoPersona = persona.correoPersona;
        this.telefonoPersona = persona.telefonoPersona;
        this.contacto = persona.contacto;
    }

    public Persona(DTOPersona persona) {
        this.nombres = persona.nombres;
        this.apellidos = persona.apellidos;
        this.fechaNacimiento = persona.fechaNacimiento;
        this.direccion = persona.direccion;
        this.sexo = persona.sexo;
        this.dui = persona.dui;
        this.tipoPersona = persona.tipoPersona;
    }

    public Persona(DTOAntecedentesFamiliares antecedente) {
        this.idPersona = antecedente.idPersona;
        this.nombres = antecedente.nombres;
        this.apellidos = antecedente.apellidos;
        this.fechaNacimiento = antecedente.fechaNacimiento;
        this.direccion = antecedente.direccion;
        this.estado = antecedente.estado;
        this.sexo = antecedente.sexo;
        this.dui = antecedente.dui;
        this.tipoPersona = antecedente.tipoPersona;
    }

    
    
}
