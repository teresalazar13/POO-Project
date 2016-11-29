import java.io.Serializable;
import java.util.ArrayList;

public class ExameNormal extends Exame implements Serializable {

    public ExameNormal() {}

    public ExameNormal(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes) {
        super(disciplina, data, duracao, docenteResponsavel, vigilantes);
    }

    @Override
    public String toString() {
        return "Exame Normal\n" + super.toString();
    }
}
