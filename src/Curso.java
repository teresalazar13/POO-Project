import java.util.ArrayList;
import java.util.StringTokenizer;

public class Curso {
    private String nome;
    private int duracao;
    private String grau;
    private ArrayList<Disciplina> disciplinas;

    public Curso() {}

    public Curso(String nome, int duracao, String grau, ArrayList<Disciplina> disciplinas) {
        this.nome = nome;
        this.duracao = duracao;
        this.grau = grau;
        this.disciplinas = disciplinas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString() {
        return "Curso: " + nome + '\n' +
                "Duracao: " + duracao + '\n' +
                "Grau: " + grau + '\n' +
                "Disciplinas: " + disciplinas + '\n' +
                "----";
    }
}
