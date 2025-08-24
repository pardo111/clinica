package clinica.version_java.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import clinica.version_java.utils.PersonaUtils;

@Service
public class PersonaService {

        private PersonaRepository personaRepository;
        private TelefonoPersonaRepository telefonoPersonaRepository;
        private CorreoPersonaRepository correoPersonaRepository;
        private ContactoEmergenciaRepository contactoEmergenciaRepository;
        private AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;

        // contructor que inyecta las dependencias de los repositorios
        public PersonaService(PersonaRepository personaRepository, TelefonoPersonaRepository telefonoPersonaRepository,
                        CorreoPersonaRepository correoPersonaRepository,
                        ContactoEmergenciaRepository contactoEmergenciaRepository,
                        AntecedentesFamiliaresRepository antecedentesFamiliaresRepository) {
                this.personaRepository = personaRepository;
                this.telefonoPersonaRepository = telefonoPersonaRepository;
                this.correoPersonaRepository = correoPersonaRepository;
                this.contactoEmergenciaRepository = contactoEmergenciaRepository;
                this.antecedentesFamiliaresRepository = antecedentesFamiliaresRepository;
        }

        // metodo para obtener todas las personas en formato DTO
        @Transactional(readOnly = true)
        public List<DTOPersona> getAll() {
                return personaRepository.findAll()
                                .stream()
                                .map(DTOPersona::new)
                                .toList();
        }

        // metodo para obtener una pagina de personas en formato DTO
        @Transactional(readOnly = true)
        public Page<DTOPersona> getPage(int page, int size, String sort) {
                Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
                return personaRepository.findAll(pageable)
                                .map(DTOPersona::new);
        }

        // metodo para ingresar nueva persona en general usando la capa de DTO
        // tablas afectadas: persona, telefono_persona, correo_persona,
        // contacto_emergencia, antecedentes_familiares
        @Transactional(rollbackFor = Exception.class)
        public DTOPersona crearPersonaCompleta(DTOPersona DTOpersona) {
                DTOPersona personaNueva = guardarDatosPersona(DTOpersona);
                Persona persona = personaRepository.findByDui(personaNueva.dui);

                DTOpersona.antecedentesFamiliares = PersonaUtils.procesarLista(DTOpersona.antecedentesFamiliares,
                                DTOantecedente -> {
                                        guardarDatosPersona(DTOantecedente);
                                        Persona AntecedenteFamiliar = personaRepository.findByDui(DTOantecedente.dui);
                                        antecedentesFamiliaresRepository.save(
                                                        new AntecedentesFamiliares(DTOantecedente.antecedentes,
                                                                        AntecedenteFamiliar, persona));
                                        return DTOantecedente;
                                });

                DTOpersona.contactosEmergencia = PersonaUtils.procesarLista(DTOpersona.contactosEmergencia,
                                DTOcontacto -> {
                                        guardarDatosPersona(DTOcontacto);
                                        Persona contactosEmergencia = personaRepository.findByDui(DTOcontacto.dui);

                                        contactoEmergenciaRepository.save(
                                                        new ContactoEmergencia(DTOcontacto.relacion,
                                                                        contactosEmergencia, persona));
                                        return DTOcontacto;
                                });
                return DTOpersona;
        }

        // metodo privado para guardar los datos basicos de una persona y sus telefonos
        // y correos
        // tablas efectadas: persona, telefono_persona, correo_persona
        private <T extends DTOPersona> T guardarDatosPersona(T dto) {
                try {
                        Persona persona = personaRepository.existsByDui(dto.dui) ? 
                                          personaRepository.findByDui(dto.dui)
                                        : personaRepository.save(new Persona(dto));
                        DTOPersona personaNueva = new DTOPersona(persona);

                        List<String> correos = new ArrayList<>();
                        List<String> telefonos = new ArrayList<>();
                        if (dto.correos != null)
                                correos = PersonaUtils.procesarLista(dto.correos,
                                                correo -> correoPersonaRepository
                                                                .save(new CorreoPersona(correo, persona))
                                                                .getCorreo());
                        if (dto.telefonos != null)
                                telefonos = PersonaUtils.procesarLista(dto.telefonos,
                                                telefono -> telefonoPersonaRepository.save(
                                                                new TelefonoPersona(telefono, persona))
                                                                .getTelefono());

                        return (T) personaNueva;
                } catch (Exception e) {
                        System.out.println("\n\n\n\n\n\n\n error guardarDatosPersona : " + e.getMessage());
                        throw e;
                }
        }

}