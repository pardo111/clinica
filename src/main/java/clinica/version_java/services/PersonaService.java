package clinica.version_java.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clinica.version_java.DTO.DTOPersona;
import clinica.version_java.models.AntecedentesFamiliares;
import clinica.version_java.models.ContactoEmergencia;
import clinica.version_java.models.CorreoPersona;
import clinica.version_java.models.Persona;
import clinica.version_java.models.TelefonoPersona;
import clinica.version_java.repositories.AntecedentesFamiliaresRepository;
import clinica.version_java.repositories.ContactoEmergenciaRepository;
import clinica.version_java.repositories.CorreoPersonaRepository;
import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.repositories.TelefonoPersonaRepository;

@Service
public class PersonaService {

    private PersonaRepository personaRepository;
    private TelefonoPersonaRepository telefonoPersonaRepository;
    private CorreoPersonaRepository correoPersonaRepository;
    private ContactoEmergenciaRepository contactoEmergenciaRepository;
    private AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;

    public PersonaService(PersonaRepository personaRepository, TelefonoPersonaRepository telefonoPersonaRepository,
            CorreoPersonaRepository correoPersonaRepository, ContactoEmergenciaRepository contactoEmergenciaRepository,
            AntecedentesFamiliaresRepository antecedentesFamiliaresRepository) {
        this.personaRepository = personaRepository;
        this.telefonoPersonaRepository = telefonoPersonaRepository;
        this.correoPersonaRepository = correoPersonaRepository;
        this.contactoEmergenciaRepository = contactoEmergenciaRepository;
        this.antecedentesFamiliaresRepository = antecedentesFamiliaresRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public DTOPersona create(DTOPersona persona) {

        // guardo a la persona principal
        DTOPersona personaNueva = salvaDatosGeneralesPersona(persona);

        // guardo a los antecedentes y/o contactos familiares
        personaNueva.antecedentesFamiliares = procesarLista(persona.antecedentesFamiliares,
                this::salvaDatosGeneralesPersona);
        personaNueva.contactosEmergencia = procesarLista(persona.contactosEmergencia,
                this::salvaDatosGeneralesPersona);



        Optional.ofNullable(persona.antecedentesFamiliares)
                .orElse(List.of())
                .forEach(dto -> {
                    Persona familiar = personaRepository.findById(dto.idPersona)
                            .orElse(new Persona(dto));

                    Persona paciente = personaRepository.findById(personaNueva.idPersona)
                                                        .orElse(new Persona(personaNueva));
                    antecedentesFamiliaresRepository.save(
                            new AntecedentesFamiliares(dto.antecedentes, paciente, familiar));
                });

        Optional.ofNullable(persona.contactosEmergencia)
                .orElse(List.of())
                .forEach(dto -> {
                    Persona contacto = personaRepository.findById(dto.idPersona)
                            .orElse(new Persona(dto));

                    Persona principal = personaRepository.findById(personaNueva.idPersona)
                                                        .orElse(new Persona(personaNueva));
                    contactoEmergenciaRepository.save(
                            new ContactoEmergencia(dto.relacion, principal, contacto));
                });

        return personaNueva;
    }

    private <T extends DTOPersona> T salvaDatosGeneralesPersona(T persona) {
        Persona personaNueva = personaRepository.save(new Persona(persona));

        List<String> correos = new ArrayList<>();
        List<String> telefonos = new ArrayList<>();
        if (persona.correos != null) {
            for (String correo : persona.correos) {
                CorreoPersona correoPersona = correoPersonaRepository.save(new CorreoPersona(correo, personaNueva));
                correos.add(correoPersona.getCorreo());
            }
        }

        if (persona.telefonos != null) {
            for (String telefono : persona.telefonos) {
                TelefonoPersona telefonoPersona = telefonoPersonaRepository
                        .save(new TelefonoPersona(telefono, personaNueva));
                telefonos.add(telefonoPersona.getTelefono());
            }
        }

        persona.idPersona = personaNueva.getIdPersona();
        persona.nombres = personaNueva.getNombres();
        persona.apellidos = personaNueva.getApellidos();
        persona.fechaNacimiento = personaNueva.getFechaNacimiento();
        persona.direccion = personaNueva.getDireccion();
        persona.estado = personaNueva.getEstado();
        persona.sexo = personaNueva.getSexo();
        persona.dui = personaNueva.getDui();
        persona.tipoPersona = personaNueva.getTipoPersona();
        return persona;
    }

    private <E extends DTOPersona> List<E> procesarLista(List<E> lista, Function<E, E> metodo) {
        return Optional.ofNullable(lista)
                .orElse(List.of())
                .stream()
                .map(metodo)
                .toList();
    }

}
