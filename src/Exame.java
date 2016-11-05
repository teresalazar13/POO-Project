import java.util.ArrayList;

public class Exame {
    protected Disciplina disciplina;
    protected Data data;
    protected int duracao;
    protected Docente docenteResponsavel;
    protected ArrayList<Docente> vigilantes;
    protected ArrayList<NaoDocente> funcionariosNaoDocentes;
    protected ArrayList<AlunoClassificacao> alunoClassificao;

    public Exame(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes, ArrayList<NaoDocente> funcionariosNaoDocentes, ArrayList<AlunoClassificacao> alunoClassificao) {
        this.disciplina = disciplina;
        this.data = data;
        this.duracao = duracao;
        this.docenteResponsavel = docenteResponsavel;
        this.vigilantes = vigilantes;
        this.funcionariosNaoDocentes = funcionariosNaoDocentes;
        this.alunoClassificao = alunoClassificao;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Docente getDocenteResponsavel() {
        return docenteResponsavel;
    }

    public void setDocenteResponsavel(Docente docenteResponsavel) {
        this.docenteResponsavel = docenteResponsavel;
    }

    public ArrayList<Docente> getVigilantes() {
        return vigilantes;
    }

    public void setVigilantes(ArrayList<Docente> vigilantes) {
        this.vigilantes = vigilantes;
    }

    public ArrayList<NaoDocente> getFuncionariosNaoDocentes() {
        return funcionariosNaoDocentes;
    }

    public void setFuncionariosNaoDocentes(ArrayList<NaoDocente> funcionariosNaoDocentes) {
        this.funcionariosNaoDocentes = funcionariosNaoDocentes;
    }

    public ArrayList<AlunoClassificacao> getAlunoClassificao() {
        return alunoClassificao;
    }

    public void setAlunoClassificao(ArrayList<AlunoClassificacao> alunoClassificao) {
        this.alunoClassificao = alunoClassificao;
    }
}
