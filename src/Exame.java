import java.util.ArrayList;
import java.util.Scanner;

public class Exame {
    protected Disciplina disciplina;
    protected Data data;
    protected int duracao;
    protected int sala;
    protected Docente docenteResponsavel;
    protected ArrayList<Docente> vigilantes;
    protected ArrayList<NaoDocente> funcionariosNaoDocentes;
    protected ArrayList<AlunoClassificacao> alunosClassificacao;

    public Exame() {
    }

    public Exame(Disciplina disciplina, Data data, int duracao, Docente docenteResponsavel, ArrayList<Docente> vigilantes) {
        this.disciplina = disciplina;
        this.data = data;
        this.duracao = duracao;
        this.sala = 0;
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

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
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

    public void lancarNotas() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < alunosClassificacao.size(); i++) {
            System.out.print(alunosClassificacao.get(i).getAluno().getNome() + ": ");
            int nota = sc.nextInt();
            alunosClassificacao.get(i).setClassificacao(nota);
        }
    }

    public void listarNotas() {
        for(int i = 0; i < alunosClassificacao.size(); i++ ){
            System.out.println(alunosClassificacao.get(i).getAluno().getNome() + ": " + alunosClassificacao.get(i).getClassificacao());
        }
    }

    public void listarFuncionarios() {
        System.out.println("Docente Responsavel: " + docenteResponsavel);
        System.out.println("Vigilantes");
        for (int i = 0; i < vigilantes.size(); i++) {
            System.out.println(vigilantes.get(i));
        }
        System.out.println("Funcionarios Nao Docentes");
        for (int i = 0; i < funcionariosNaoDocentes.size(); i++) {
            System.out.println(funcionariosNaoDocentes.get(i));
        }
    }

    public boolean verificaAlunoInscrito(int numero) {
        for (int i = 0; i < alunosClassificacao.size(); i++) {
            if (alunosClassificacao.get(i).getAluno().getNumero() == numero)
                return true;
        }
        return false;
    }

    public boolean verificaVigilante(int numero) {
        for (int i = 0; i < vigilantes.size(); i++) {
            if (vigilantes.get(i).getNumeroMecanografico() == numero)
                return true;
        }
        return false;
    }

    public boolean verificaFuncionarioNaoDocente(int numero) {
        for (int i = 0; i < funcionariosNaoDocentes.size(); i++) {
            if (funcionariosNaoDocentes.get(i).getNumeroMecanografico() == numero)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Disciplina: " + disciplina.getNome() +
                "\nData: " + data +
                "\nDuracao: " + duracao +
                "\nSala: " + sala +
                "\nDocente responsavel: " + docenteResponsavel.getNome() +
                "\nNumero de Vigilantes: " + vigilantes.size() +
                "\nNumero de Funcionarios nao docentes: " + funcionariosNaoDocentes.size() +
                "\nNumero de Alunos " + alunosClassificacao.size() +
                "\n----";
    }
}