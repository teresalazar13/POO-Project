import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Projeto {

    public static ArrayList<Exame> exames = new ArrayList<Exame>();
    public static ArrayList<Aluno> alunos = new ArrayList<Aluno>();
    public static ArrayList<Docente> docentes = new ArrayList<Docente>();
    public static ArrayList<NaoDocente> naoDocentes = new ArrayList<NaoDocente>();
    public static ArrayList<Curso> cursos = new ArrayList<Curso>();
    public static ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();

    public static void adicionarExame() {
        Exame exame = new Exame();
        exames.add(exame);
    }

    public static void listarExames() {
        System.out.println("----LISTA DE EXAMES----");
        Iterator<Exame> it = exames.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void listarAlunos() {
        System.out.println("----LISTA DE ALUNOS----");
        Iterator<Aluno> it = alunos.iterator();
        while(it.hasNext()) {
            System.out.println("oi");
            System.out.println(it.next());
        }
    }

    public static void listarDocentes() {
        System.out.println("----LISTA DE FUNCIONARIOS DOCENTES----");
        Iterator<Docente> it = docentes.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void listarNaoDocentes() {
        System.out.println("----LISTA DE FUNCIONARIOS NAO DOCENTES----");
        Iterator<NaoDocente> it = naoDocentes.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void listarCursos() {
        System.out.println("----LISTA DE CURSOS----");
        Iterator<Curso> it = cursos.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void listarDisciplinas() {
        System.out.println("----LISTA DE DISCIPLINAS----");
        Iterator<Disciplina> it = disciplinas.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void menu() {
        Curso curso = new Curso("Engenharia Informatica", 3, "Licenciatura", disciplinas);
        System.out.println(curso);
        cursos.add(curso);
        Aluno aluno = new Aluno("Teresa", "teresa.sal13@gmail.com", 20, 1, curso, "normal");
        alunos.add(aluno);
        Docente docente = new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "Engenharia de Software");
        docentes.add(docente);
        NaoDocente naoDocente = new NaoDocente("Jorge", "jorge@gmail.com", 10, "Tecnico", "Secretaria");
        naoDocentes.add(naoDocente);
        Disciplina disciplina = new Disciplina("POO", docente, docentes, alunos);
        disciplinas.add(disciplina);
        Data data = new Data(1,2,3,4,5);

        listarExames();
        /*
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();

        System.out.println("----LISTA DE ALUNOS----");
        Iterator<Aluno> it = alunos.iterator();
        while(it.hasNext()) {
            System.out.println("oi");
            System.out.println(it.next());
        }*/
        //listarAlunos();
        //listarDocentes();
        //listarNaoDocentes();
        //listarCursos();
        //listarDisciplinas();

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