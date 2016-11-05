import java.util.ArrayList;

public class ExameRecurso extends Exame {
    public ExameRecurso(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes, ArrayList<NaoDocente> funcionariosNaoDocentes, ArrayList<AlunoClassificacao> alunoClassificao) {
        super(disciplina, data, duracao, docenteResponsavel, vigilantes, funcionariosNaoDocentes, alunoClassificao);
    }
}
