import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Projeto {

    private static ArrayList<Exame> exames = new ArrayList<Exame>();
    private static ArrayList<Aluno> alunos = new ArrayList<Aluno>();
    private static ArrayList<Docente> docentes = new ArrayList<Docente>();
    private static ArrayList<NaoDocente> naoDocentes = new ArrayList<NaoDocente>();
    private static ArrayList<Curso> cursos = new ArrayList<Curso>();
    private static ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();

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

    public static void menu() {
        Curso curso = new Curso("Engenharia Informatica", 3, "Licenciatura", disciplinas);
        Aluno aluno = new Aluno("Teresa", "teresa.sal13@gmail.com", 20, 1, curso, "normal");
        Docente docente = new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "Engenharia de Software");
        NaoDocente naoDocente = new NaoDocente("Jorge", "jorge@gmail.com", 10, "Tecnico", "Secretaria");
        Disciplina disciplina = new Disciplina("POO", docente, docentes, alunos);
        Data data = new Data(1,2,3,4,5);
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