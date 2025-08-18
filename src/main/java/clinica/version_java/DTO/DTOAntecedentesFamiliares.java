package clinica.version_java.DTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DTOAntecedentesFamiliares extends DTOPersona{
    public String antecedentes;

    public DTOAntecedentesFamiliares(DTOPersona persona, String antecedentes) {
        super(persona);
        this.antecedentes = antecedentes;
    }   
}
