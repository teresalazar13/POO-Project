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

    private static void adicionarExame() {
        Scanner sc = new Scanner(System.in);
        Disciplina disciplina = procurarDisciplina();
        Data data = data();
        System.out.print("Duracao: ");
        int duracao = sc.nextInt();
        Docente docenteResponsavel = disciplina.getDocente(); // Adicionar docente da disciplina ao docente Responsavel
        ArrayList<Docente> vigilantes = new ArrayList<Docente>();
        ArrayList<Docente> docentesDisciplina = disciplina.getOutrosDocentes(); // Adicionar docentes da disciplina aos vigilantes se tiverem disponibilidade
        for (int i = 0; i < docentesDisciplina.size(); i++) {
            if (compararData(data, duracao, docentesDisciplina.get(i)) == 1) {
                vigilantes.add(docentesDisciplina.get(i));
            }
        }
        Exame exame = new Exame(disciplina, data, duracao, docenteResponsavel, vigilantes);
        exames.add(exame);
    }

    private static int compararData(Data data, int duracao, Docente docente) {
        int dataInicio = data.getAno() * 10000000 + data.getMes() * 100000 + data.getDia() * 1000 + data.getHora() * 10 + data.getMinuto();
        int dataFim = dataInicio + duracao;
        System.out.println(dataInicio + " " + dataFim);
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).contemDocente(docente)) {
                Data dataExame = exames.get(i).getData();
                int duracaoExame = exames.get(i).getDuracao();
                int exameInicio = dataExame.getAno() * 10000000 + dataExame.getMes() * 100000 + dataExame.getDia() * 1000 + dataExame.getHora() * 10 + dataExame.getMinuto();
                int exameFim = exameInicio + duracaoExame;
                System.out.println(exameInicio + " " + exameFim);
                if ((dataInicio >= exameInicio && dataInicio <= exameFim) || (dataFim >= exameInicio && dataFim <= exameFim)) {
                    return 0; // caso data coincida com exame do vigilante
                }
            }
        }
        return 1; // caso o docente tenho disponibilidade para vigiar o exame
    }

    private static void convocarFuncionarios() { // Convocar vigilantes e funcionários
        Scanner sc = new Scanner(System.in);
        Exame exame = procurarExame();
        ArrayList<Docente> vigilantes = exame.getVigilantes();
        while(true) {
            Docente vigilante = procurarDocente();
            System.out.print("Adicionar mais? (0 ou 1)");
            int opcao = sc.nextInt();
            if (compararData(exame.getData(), exame.getDuracao(), vigilante) == 1) {
                vigilantes.add(vigilante);
            }
            else {
                System.out.println("Vigilante nao tem disponibilidade.");
            }
            if (opcao == 0) {
                break;
            }
        }
        ArrayList<NaoDocente> funcionariosNaoDocentes = exame.getFuncionariosNaoDocentes();
        while(true) {
            NaoDocente funcionarioNaoDocente = procurarNaoDocente();
            System.out.print("Adicionar mais? (0 ou 1)");
            int opcao = sc.nextInt();
            funcionariosNaoDocentes.add(funcionarioNaoDocente);
            if (opcao == 0) {
                break;
            }
        }
    }

    private static Data data() {
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
        return new Data(dia, mes, ano, hora, minuto);
    }

    private static void listarExames() {
        System.out.println("----LISTA DE EXAMES----");
        Iterator<Exame> it = exames.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarAlunos() {
        System.out.println("----LISTA DE ALUNOS----");
        Iterator<Aluno> it = alunos.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarDocentes() {
        System.out.println("----LISTA DE FUNCIONARIOS DOCENTES----");
        Iterator<Docente> it = docentes.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarNaoDocentes() {
        System.out.println("----LISTA DE FUNCIONARIOS NAO DOCENTES----");
        Iterator<NaoDocente> it = naoDocentes.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarCursos() {
        System.out.println("----LISTA DE CURSOS----");
        Iterator<Curso> it = cursos.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarDisciplinas() {
        System.out.println("----LISTA DE DISCIPLINAS----");
        Iterator<Disciplina> it = disciplinas.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static Exame procurarExame() {
        listarExames();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return exames.get(opcao - 1);
    }

    private static Aluno procurarAluno() {
        listarAlunos();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return alunos.get(opcao - 1);
    }

    private static Docente procurarDocente() {
        listarDocentes();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return docentes.get(opcao - 1);
    }

    private static NaoDocente procurarNaoDocente() {
        listarNaoDocentes();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return naoDocentes.get(opcao - 1);
    }

    private static Curso procurarCurso() {
        listarCursos();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return cursos.get(opcao - 1);
    }

    private static Disciplina procurarDisciplina() {
        listarDisciplinas();
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return disciplinas.get(opcao - 1);
    }

    private static void menu() {
        Docente docente = new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "Engenharia de Software");
        Disciplina disciplina = new Disciplina("POO", docente, docentes, alunos);
        Curso curso = new Curso("Engenharia Informatica", 3, "Licenciatura", disciplinas);
        Aluno aluno = new Aluno("Teresa", "teresa.sal13@gmail.com", 20, 1, curso, "normal");
        NaoDocente naoDocente = new NaoDocente("Jorge", "jorge@gmail.com", 10, "Tecnico", "Secretaria");

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
                    "5 - Lançar notas de um exame\n" +
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
                case 3:
                    convocarFuncionarios();
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