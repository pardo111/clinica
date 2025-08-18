package clinica.version_java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.DTO.DTOPersona;
import clinica.version_java.services.PersonaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

   @Autowired
   private PersonaService personaService;

   @PostMapping("/create")
   public ResponseEntity<?> create(@RequestBody DTOPersona persona) {

      try {
         DTOPersona personaNueva = personaService.create(persona);
         System.out.println(personaNueva);
         return ResponseEntity.status(HttpStatus.OK)
                              .body(personaNueva);
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                              .body("Error al crear persona: " + e.getMessage());
      }

  
   }

}
