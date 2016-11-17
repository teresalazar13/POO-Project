import java.util.ArrayList;

public class ExameRecurso extends Exame {

    public ExameRecurso() {}

    public ExameRecurso(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes) {
        super(disciplina, data, duracao, docenteResponsavel, vigilantes);
    }

    @Override
    public String toString() {
        return "Exame Recurso\n" + super.toString();
    }
}
