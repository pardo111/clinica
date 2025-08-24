package clinica.version_java.controllers;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.DTO.DTOPersona;
import clinica.version_java.services.PersonaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/personas")
public class PersonaController {

   @Autowired
   private PersonaService personaService;

   @PostMapping("/create")
   public ResponseEntity<?> create(@RequestBody DTOPersona persona) {

      try {
         DTOPersona personaNueva = personaService.crearPersonaCompleta(persona);
         System.out.println(personaNueva);
         return ResponseEntity.status(HttpStatus.OK)
                              .body(personaNueva);
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                              .body("Error al crear persona: " + e.getMessage());
      }

  
   }

   @GetMapping("/getAll")
   public ResponseEntity<?> getAll() {
      try {
         return ResponseEntity.status(HttpStatus.OK)
                              .body(personaService.getAll());
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                              .body("Error al obtener personas: " + e.getMessage());
      }

   }

   @GetMapping
   public ResponseEntity<Page<DTOPersona>> getPage(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id", required = false) String sortBy
   ) {
      return ResponseEntity.ok( personaService.getPage(size, page, sortBy));
   }
   
   

}
