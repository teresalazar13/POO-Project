import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Exame implements Serializable {
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

    public ArrayList<AlunoClassificacao> getAlunosClassificacao() {
        return alunosClassificacao;
    }

    public void setAlunosClassificacao(ArrayList<AlunoClassificacao> alunosClassificacao) {
        this.alunosClassificacao = alunosClassificacao;
    }

    public boolean contemDocente(Docente docente) {
        for (int i = 0; i < vigilantes.size(); i++) {
            if (vigilantes.get(i).getNumeroMecanografico() == docente.getNumeroMecanografico()) {
                return true;
            }
        }
        return false;
    }

    public boolean contemNaoDocente(NaoDocente naoDocente) {
        for (int i = 0; i < funcionariosNaoDocentes.size(); i++) {
            if (funcionariosNaoDocentes.get(i).getNumeroMecanografico() == naoDocente.getNumeroMecanografico()) {
                return true;
            }
        }
        return false;
    }

    public boolean contemAluno(Aluno aluno) {
        for (int i = 0; i < alunosClassificacao.size(); i++) {
            if (alunosClassificacao.get(i).getAluno().getNumero() == aluno.getNumero()) {
                return true;
            }
        }
        return false;
    }

    public void lancarNotas() {
        Scanner sc = new Scanner(System.in);
        if (alunosClassificacao.size() == 0) {
            System.out.println("Nao existe nenhum aluno inscrito no exame");
        }
        for (int i = 0; i < alunosClassificacao.size(); i++) {
            System.out.print(alunosClassificacao.get(i).getAluno().getNome() + ": ");
            int nota;
            while(true) {
                while (!sc.hasNextInt()) { // Caso o input nao seja um numero
                    System.out.println("Por favor escreva um numero inteiro");
                    sc.next();
                }
                nota = sc.nextInt();
                if (20 < nota || nota <= 0) { // Caso a nota nao seja um valor entre 0 e 20, inclusive
                    System.out.println("Por favor escreve um numero entre 1 e " + 20);
                }
                else {
                    break;
                }
            }
            alunosClassificacao.get(i).setClassificacao(nota);
        }
    }

    public void listarNotas() {
        if (alunosClassificacao.size() == 0) {
            System.out.println("Nao existe nenhum aluno inscrito no exame");
        }
        for (int i = 0; i < alunosClassificacao.size(); i++ ){
            System.out.println(alunosClassificacao.get(i).getAluno().getNome() + ": " + alunosClassificacao.get(i).getClassificacao());
        }
    }

    public void listarFuncionarios() {
        System.out.println("Docente Responsavel: " + docenteResponsavel);
        System.out.println("Vigilantes");
        if (vigilantes.size() == 0) {
            System.out.println("Nao ha vigilantes inscritos para este exame");
        }
        for (int i = 0; i < vigilantes.size(); i++) {
            System.out.println(vigilantes.get(i));
        }
        if (funcionariosNaoDocentes.size() == 0) {
            System.out.println("Nao ha Funcionario Nao Docentes inscritos para este exame");
        }
        System.out.println("Funcionarios Nao Docentes");
        for (int i = 0; i < funcionariosNaoDocentes.size(); i++) {
            System.out.println(funcionariosNaoDocentes.get(i));
        }
    }

    @Override
    public String toString() {
        String salaString;
        if (sala == 0) {
            salaString = "nao marcada";
        }
        else {
            salaString = Integer.toString(sala);
        }
        return "Disciplina: " + disciplina.getNome() +
                "\nData: " + data +
                "\nDuracao: " + duracao + " minutos" +
                "\nSala: " + salaString +
                "\nDocente responsavel: " + docenteResponsavel.getNome() +
                "\nNumero de Vigilantes: " + vigilantes.size() +
                "\nNumero de Funcionarios nao docentes: " + funcionariosNaoDocentes.size() +
                "\nNumero de Alunos: " + alunosClassificacao.size() +
                "\n----";
    }
}