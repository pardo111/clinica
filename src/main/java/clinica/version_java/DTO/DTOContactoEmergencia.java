package clinica.version_java.DTO;

import clinica.version_java.models.enums.Relacion;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DTOContactoEmergencia extends DTOPersona {
    public Relacion relacion;

    public DTOContactoEmergencia(DTOPersona persona, Relacion relacion) {
        super(persona);
        this.relacion = relacion;
    }
    
}


