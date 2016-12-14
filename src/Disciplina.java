import java.io.Serializable;
import java.util.ArrayList;

public class Disciplina implements Serializable {
    private static final long serialVersionUID = -2212510796197535085L;
    private String nome;
    private Docente docente;
    private ArrayList<Docente> outrosDocentes;
    private ArrayList<Aluno> alunos;

    public Disciplina() {}

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

    public boolean contemAluno(Aluno aluno) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getNumero() == aluno.getNumero()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String text = "Disciplina: " + nome + '\n' +
                "Docente da Cadeira: " + docente.getNome() + '\n';
        text += "Docentes: ";
        for (int i = 0; i < outrosDocentes.size(); i++) {
            text += outrosDocentes.get(i).getNome() + ",";
        }
        text = text.substring(0, text.length()-1);
        text += "\nAlunos: ";
        for (int i = 0; i < alunos.size(); i++) {
            text += alunos.get(i).getNome() + ",";
        }
        text = text.substring(0, text.length()-1);
        return text + "\n----\n";
    }

}
