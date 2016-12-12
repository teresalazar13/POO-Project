import java.io.Serializable;

public class AlunoClassificacao implements Serializable {
    private Aluno aluno;
    private int classificacao;

    public AlunoClassificacao() {}

    public AlunoClassificacao(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public String toString() {
        return "Aluno: " + aluno.nome + '\n' +
                "Classificacao: " + classificacao + '\n' +
                "----";
    }
}
