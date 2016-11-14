import java.util.ArrayList;

public class ExameNormal extends Exame{

    public ExameNormal() {}

    public ExameNormal(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes, ArrayList<NaoDocente> funcionariosNaoDocentes, ArrayList<AlunoClassificacao> alunoClassificao) {
        super(disciplina, data, duracao, docenteResponsavel, vigilantes, funcionariosNaoDocentes, alunoClassificao);
    }

    @Override
    public String toString() {
        return "Exame Normal: ";
    }
}
