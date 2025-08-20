
package clinica.version_java.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Sexo;
import clinica.version_java.models.enums.TipoPersona;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@NoArgsConstructor
public class DTOPersona {

    public int idPersona;
    @NotBlank
    public String nombres;
    @NotBlank
    public String apellidos;
    @NotBlank
    public LocalDate fechaNacimiento;
    public String direccion;
    public Estado estado = Estado.ACTIVO;
    @NotBlank
    public Sexo sexo;
    public String dui;
    @NotBlank
    public TipoPersona tipoPersona;


    public List<DTOContactoEmergencia> contactosEmergencia = new ArrayList<>();
    public List<String> correos = new ArrayList<>();
    public List<String> telefonos = new ArrayList<>();
    public List<DTOAntecedentesFamiliares> antecedentesFamiliares = new ArrayList<>();



    public DTOPersona(
        Persona persona,
        List<String> correos,
        List<String> telefonos,
        List<DTOContactoEmergencia> contactosEmergencia,
        List<DTOAntecedentesFamiliares> antecedentesFamiliares
        ) {
        this.idPersona = persona.getIdPersona();
        this.nombres = persona.getNombres();
        this.apellidos = persona.getApellidos();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.direccion = persona.getDireccion();
        this.estado = persona.getEstado();
        this.sexo = persona.getSexo();
        this.dui = persona.getDui();
        this.tipoPersona = persona.getTipoPersona();
        this.correos = correos;
        this.telefonos = telefonos;
        this.antecedentesFamiliares = antecedentesFamiliares;
        this.contactosEmergencia = contactosEmergencia;
    }


    

    public DTOPersona(
        Persona persona,
        List<String> correos,
        List<String> telefonos
        ) {
        this.idPersona = persona.getIdPersona();
        this.nombres = persona.getNombres();
        this.apellidos = persona.getApellidos();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.direccion = persona.getDireccion();
        this.estado = persona.getEstado();
        this.sexo = persona.getSexo();
        this.dui = persona.getDui();
        this.tipoPersona = persona.getTipoPersona();
        this.correos = correos;
        this.telefonos = telefonos;
    }

    public DTOPersona(
        DTOPersona persona
        ) {
        this.idPersona = persona.idPersona;
        this.nombres = persona.nombres;
        this.apellidos = persona.apellidos;
        this.fechaNacimiento = persona.fechaNacimiento;
        this.direccion = persona.direccion;
        this.estado = persona.estado;
        this.sexo = persona.sexo;
        this.dui = persona.dui;
        this.tipoPersona = persona.tipoPersona;
        this.correos = persona.correos;
        this.telefonos = persona.telefonos;
        this.antecedentesFamiliares = persona.antecedentesFamiliares;
        this.contactosEmergencia = persona.contactosEmergencia;
        }

        public DTOPersona(Persona persona) {
    this.idPersona = persona.getIdPersona();
    this.nombres = persona.getNombres();
    this.apellidos = persona.getApellidos();
    this.fechaNacimiento = persona.getFechaNacimiento();
    this.direccion = persona.getDireccion();
    this.estado = persona.getEstado();
    this.sexo = persona.getSexo();
    this.dui = persona.getDui();
    this.tipoPersona = persona.getTipoPersona();
}

}