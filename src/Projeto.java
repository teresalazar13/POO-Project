import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.lang.reflect.*;

public class Projeto {

    public static ArrayList<Exame> exames = new ArrayList<Exame>();
    public static ArrayList<Aluno> alunos = new ArrayList<Aluno>();
    public static ArrayList<Docente> docentes = new ArrayList<Docente>();
    public static ArrayList<NaoDocente> naoDocentes = new ArrayList<NaoDocente>();
    public static ArrayList<Curso> cursos = new ArrayList<Curso>();
    public static ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();

    public static void adicionarExame() {
        Scanner sc = new Scanner(System.in);
        Disciplina disciplina = procurarDisciplina();
        Data data = data();
        System.out.print("Duracao: ");
        int duracao = sc.nextInt();
        Docente docenteResponsavel = procurarDocente();
        ArrayList<Docente> vigilantes = new ArrayList<Docente>();
        while(true) {
            Docente vigilante = procurarDocente();
            System.out.print("Adicionar mais? (0 ou 1)");
            int opcao = sc.nextInt();
            vigilantes.add(vigilante);
            if (opcao == 0) {
                break;
            }
        }
        ArrayList<NaoDocente> funcionariosNaoDocentes = new ArrayList<NaoDocente>();
        while(true) {
            NaoDocente funcionarioNaoDocente = procurarNaoDocente();
            System.out.print("Adicionar mais? (0 ou 1)");
            int opcao = sc.nextInt();
            funcionariosNaoDocentes.add(funcionarioNaoDocente);
            if (opcao == 0) {
                break;
            }
        }
        Exame exame = new Exame(disciplina, data, duracao, docenteResponsavel, vigilantes, funcionariosNaoDocentes);
        exames.add(exame);
    }

    public static Data data() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Dia");
        int dia = sc.nextInt();
        System.out.println("Mes");
        int mes = sc.nextInt();
        System.out.println("Ano");
        int ano = sc.nextInt();
        System.out.println("Hora");
        int hora = sc.nextInt();
        System.out.println("Minuto");
        int minuto = sc.nextInt();
        Data data = new Data(dia, mes, ano, hora, minuto);
        return data;
    }

    public static void listarExames() {
        System.out.println("----LISTA DE EXAMES----");
        Iterator<Exame> it = exames.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    public static void listarAlunos() {
        System.out.println("----LISTA DE ALUNOS----");
        Iterator<Aluno> it = alunos.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    public static void listarDocentes() {
        System.out.println("----LISTA DE FUNCIONARIOS DOCENTES----");
        Iterator<Docente> it = docentes.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    public static void listarNaoDocentes() {
        System.out.println("----LISTA DE FUNCIONARIOS NAO DOCENTES----");
        Iterator<NaoDocente> it = naoDocentes.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    public static void listarCursos() {
        System.out.println("----LISTA DE CURSOS----");
        Iterator<Curso> it = cursos.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    public static void listarDisciplinas() {
        System.out.println("----LISTA DE DISCIPLINAS----");
        Iterator<Disciplina> it = disciplinas.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    public static Exame procurarExame() {
        listarExames();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return exames.get(opcao - 1);
    }

    public static Aluno procurarAluno() {
        listarAlunos();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return alunos.get(opcao - 1);
    }

    public static Docente procurarDocente() {
        listarDocentes();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return docentes.get(opcao - 1);
    }

    public static NaoDocente procurarNaoDocente() {
        listarNaoDocentes();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return naoDocentes.get(opcao - 1);
    }

    public static Curso procurarCurso() {
        listarCursos();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return cursos.get(opcao - 1);
    }

    public static Disciplina procurarDisciplina() {
        listarDisciplinas();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return disciplinas.get(opcao - 1);
    }

    public static void menu() {
        Docente docente = new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "Engenharia de Software");
        Disciplina disciplina = new Disciplina("POO", docente, docentes, alunos);
        Curso curso = new Curso("Engenharia Informatica", 3, "Licenciatura", disciplinas);
        Aluno aluno = new Aluno("Teresa", "teresa.sal13@gmail.com", 20, 1, curso, "normal");
        NaoDocente naoDocente = new NaoDocente("Jorge", "jorge@gmail.com", 10, "Tecnico", "Secretaria");
        Data data = new Data(1,2,3,4,5);

        docentes.add(docente);
        disciplinas.add(disciplina);
        cursos.add(curso);
        alunos.add(aluno);
        naoDocentes.add(naoDocente);

        /*
        listarExames();
        listarAlunos();
        listarDocentes();
        listarNaoDocentes();
        listarCursos();
        listarDisciplinas();

        Aluno aluno1 = procurarAluno();
        System.out.println(aluno1);*/

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Pretende\n" +
                    "1 - Criar Exame\n" +
                    "2 - Configurar sala de exame\n" +
                    "3 - Convocar vigilantes e funcionários\n" +
                    "4 - Inscrever alunos\n" +
                    "5 - Lançar notas de um exame" +
                    "6 - Listar Exames\n" +
                    "7 - Listar alunos de exame e respetivas classificações\n" +
                    "8 - Listar exames de aluno e respetivas classificações\n" +
                    "9 - Listar docentes e funcionarios de exame\n" +
                    "10 - Listar exames de docente ou funcionario\n" +
                    "11 - Listar notas de exame\n" +
                    "0 para sair");
            int opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    adicionarExame();
                    listarExames();
                    break;
                case 6:
                    listarExames();
                    break;
                default:
                    return;
            }
        }
    }

    public static void main(String args[]){
        menu();
    }
}