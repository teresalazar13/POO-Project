import java.util.ArrayList;

public class Exame {
    protected Disciplina disciplina;
    protected Data data;
    protected int duracao;
    protected Docente docenteResponsavel;
    protected ArrayList<Docente> vigilantes;
    protected ArrayList<NaoDocente> funcionariosNaoDocentes;
    protected ArrayList<AlunoClassificacao> alunosClassificacao;

    public Exame() {}

    public Exame(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes) {
        this.disciplina = disciplina;
        this.data = data;
        this.duracao = duracao;
        this.docenteResponsavel = docenteResponsavel;
        this.vigilantes = vigilantes;
        this.funcionariosNaoDocentes = new ArrayList<NaoDocente>();
        this.alunosClassificacao = new ArrayList<AlunoClassificacao>();
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

    public ArrayList<AlunoClassificacao> getAlunoClassificacao() {
        return alunosClassificacao;
    }

    public void setAlunoClassificacao(ArrayList<AlunoClassificacao> alunoClassificacao) {
        this.alunosClassificacao = alunoClassificacao;
    }

    public boolean contemDocente(Docente docente) {
        for (int i = 0; i < vigilantes.size(); i++) {
            if (vigilantes.get(i).equals(docente)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String str = "Exame: " + '\n' +
                "Disciplina: " + disciplina.getNome() + '\n' +
                "Data: " + data + '\n' +
                "Duracao: " + duracao + '\n' +
                "Docente responsavel: " + docenteResponsavel.getNome() + '\n' +
                "Vigilantes: ";
        for(int i = 0; i < vigilantes.size(); i++) {
            str += vigilantes.get(i).getNome() + " (" + vigilantes.get(i).getNumeroMecanografico() + ") "+ "; ";
        }
        str += "\nFuncionarios nao docentes: ";
        for (int i = 0; i < funcionariosNaoDocentes.size(); i++) {
            str += funcionariosNaoDocentes.get(i).getNome() + " (" + funcionariosNaoDocentes.get(i).getNumeroMecanografico() + ") " + "; ";
        }
        str += "\nAluno + Classificacao: " + "--lista de alunos--" + '\n' +
        "----";
        return str;
    }
}
