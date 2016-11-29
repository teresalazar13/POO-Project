import java.io.Serializable;
import java.util.ArrayList;

public class ExameEspecial extends Exame implements Serializable {
    public ExameEspecial() {}

    public ExameEspecial(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes) {
        super(disciplina, data, duracao, docenteResponsavel, vigilantes);
    }

    @Override
    public String toString() {
        return "Exame Especial\n" + super.toString();
    }
}
