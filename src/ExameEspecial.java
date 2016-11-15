import java.util.ArrayList;

public class ExameEspecial extends Exame {
    public ExameEspecial() {}

    public ExameEspecial(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes, ArrayList<NaoDocente> funcionariosNaoDocentes) {
        super(disciplina, data, duracao, docenteResponsavel, vigilantes, funcionariosNaoDocentes);
    }

    @Override
    public String toString() {
        return "Exame Especial: ";
    }
}
