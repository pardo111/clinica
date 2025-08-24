package clinica.version_java.utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import clinica.version_java.repositories.CorreoPersonaRepository;
import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.repositories.TelefonoPersonaRepository;

public class PersonaUtils {

    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    TelefonoPersonaRepository telefonoPersonaRepository;
    @Autowired
    CorreoPersonaRepository correoPersonaRepository;



      
        public static <E > List<E> procesarLista(List<E> lista, Function<E, E> metodo) {
                return Optional.ofNullable(lista)
                                .orElse(List.of())
                                .stream()
                                .map(metodo)
                                .toList();
        }

  

}
