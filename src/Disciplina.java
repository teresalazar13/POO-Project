import java.util.ArrayList;

public class Disciplina {
    private String nome;
    private Docente docente;
    private ArrayList<Docente> outrosDocentes;
    private ArrayList<Aluno> alunos;

    public Disciplina(String nome, Docente docente, ArrayList<Docente> outrosDocentes, ArrayList<Aluno> alunos) {
        this.nome = nome;
        this.docente = docente;
        this.outrosDocentes = outrosDocentes;
        this.alunos = alunos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public ArrayList<Docente> getOutrosDocentes() {
        return outrosDocentes;
    }

    public void setOutrosDocentes(ArrayList<Docente> outrosDocentes) {
        this.outrosDocentes = outrosDocentes;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }
}
