import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Projeto {

    private static void adicionarExame(ArrayList<Exame> exames, ArrayList<Disciplina> disciplinas) {
        Scanner sc = new Scanner(System.in);
        Disciplina disciplina = procurarDisciplina(disciplinas);
        Data data = data();
        System.out.print("Duracao: ");
        int duracao = sc.nextInt();
        Docente docenteResponsavel = disciplina.getDocente(); // Adicionar docente da disciplina ao docente Responsavel
        ArrayList<Docente> vigilantes = new ArrayList<Docente>();
        ArrayList<Docente> docentesDisciplina = disciplina.getOutrosDocentes(); // Adicionar docentes da disciplina aos vigilantes se tiverem disponibilidade
        for (int i = 0; i < docentesDisciplina.size(); i++) {
            if (verificarDisponibilidadeDocente(exames, data, duracao, docentesDisciplina.get(i)) == 1) {
                vigilantes.add(docentesDisciplina.get(i));
            }
        }
        System.out.println("Escolha o tipo de exame");
        System.out.println("1: Exame Normal\n2: Exame Recurso\n3: Exame Especial\nOpcao: ");
        int opcao = sc.nextInt();
        if (opcao == 1) {
            Exame exame = new ExameNormal(disciplina, data, duracao, docenteResponsavel, vigilantes);
            exames.add(exame);
        }
        else if(opcao == 2) {
            Exame exame = new ExameRecurso(disciplina, data, duracao, docenteResponsavel, vigilantes);
            exames.add(exame);
        }
        else if (opcao == 3) {
            Exame exame = new ExameEspecial(disciplina, data, duracao, docenteResponsavel, vigilantes);
            exames.add(exame);
        }
    }

    private static int verificarDisponibilidadeDocente(ArrayList<Exame> exames, Data data, int duracao, Docente docente) {
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).contemDocente(docente)) {
                Data dataExame = exames.get(i).getData();
                int duracaoExame = exames.get(i).getDuracao();
                if (compararData(data, duracao, dataExame, duracaoExame) == 0) {
                    return 0;
                }
            }
        }
        return 1; // caso o docente tenha disponibilidade para vigiar o exame
    }

    private static int compararData(Data data, int duracao, Data dataExame, int duracaoExame) {
        int dataInicio = data.getAno() * 10000000 + data.getMes() * 100000 + data.getDia() * 1000 + data.getHora() * 10 + data.getMinuto();
        int dataFim = dataInicio + duracao;
        int exameInicio = dataExame.getAno() * 10000000 + dataExame.getMes() * 100000 + dataExame.getDia() * 1000 + dataExame.getHora() * 10 + dataExame.getMinuto();
        int exameFim = exameInicio + duracaoExame;
        if ((dataInicio >= exameInicio && dataInicio <= exameFim) || (dataFim >= exameInicio && dataFim <= exameFim)) {
            return 0; // caso data coincida
        }
        else {
            return 1; // caso haja disponibilidade
        }
    }

    private static void configurarSala(ArrayList<Exame> exames) {
        Scanner sc = new Scanner(System.in);
        Exame exame = procurarExame(exames);
        Data data = exame.getData();
        int duracao = exame.getDuracao();
        System.out.println("Sala: ");
        int sala = sc.nextInt();
        for (int i = 0; i < exames.size(); i++) {
            Data dataExame = exames.get(i).getData();
            int duracaoExame = exames.get(i).getDuracao();
            if (compararData(data, duracao, dataExame, duracaoExame) == 0) {
                System.out.println("Nao ha disponibilidade para esta sala.");
                return;
            }
        }
        exame.setSala(sala);
        System.out.println("Sala marcada.");
    }

    private static void inscreverAluno(ArrayList<Aluno> alunos, ArrayList<Exame> exames) { // Inscrever alunos em exame, assegurando que estao inscritos em disciplina e que tem acesso a epoca do exame
        Aluno aluno = procurarAluno(alunos);
        Exame exame = procurarExame(exames);
        Disciplina disciplina = exame.getDisciplina();
        ArrayList<Aluno> alunosDisciplina = disciplina.getAlunos();
        int checkInscricaoDisciplina = 0;
        for (int i = 0; i < alunosDisciplina.size(); i++) {
            if (alunosDisciplina.get(i).getNumero() == aluno.getNumero()) {
                checkInscricaoDisciplina = 1;
            }
        }
        if (checkInscricaoDisciplina == 0) {
            System.out.println("Aluno nao inscrito em disciplina.");
            return;
        }
        String regime = aluno.getRegime();
        Class classe = exame.getClass();
        if (classe.toString().equals("class ExameEspecial")) {
            if (regime.equals("Normal") || regime.equals("Erasmus")) {
                System.out.println("Aluno nao tem acesso a esta epoca de exame.");
                return;
            }
        }
        ArrayList<AlunoClassificacao> alunosClassificacao = exame.getAlunoClassificacao();
        AlunoClassificacao alunoClassificacao = new AlunoClassificacao(aluno);
        alunosClassificacao.add(alunoClassificacao);
    }

    private static void convocarFuncionarios(ArrayList<Exame> exames, ArrayList<Docente> docentes, ArrayList<NaoDocente> naoDocentes) { // Convocar vigilantes e funcionários
        Scanner sc = new Scanner(System.in);
        Exame exame = procurarExame(exames);
        ArrayList<Docente> vigilantes = exame.getVigilantes();
        while(true) {
            Docente vigilante = procurarDocente(docentes);
            System.out.print("Adicionar mais? (0 ou 1)");
            int opcao = sc.nextInt();
            if (verificarDisponibilidadeDocente(exames, exame.getData(), exame.getDuracao(), vigilante) == 1) {
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
            NaoDocente funcionarioNaoDocente = procurarNaoDocente(naoDocentes);
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

    private static void listarExames(ArrayList<Exame> exames) {
        System.out.println("----LISTA DE EXAMES----");
        Iterator<Exame> it = exames.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarAlunos(ArrayList<Aluno> alunos ) {
        System.out.println("----LISTA DE ALUNOS----");
        Iterator<Aluno> it = alunos.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarDocentes(ArrayList<Docente> docentes) {
        System.out.println("----LISTA DE FUNCIONARIOS DOCENTES----");
        Iterator<Docente> it = docentes.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarNaoDocentes(ArrayList<NaoDocente> naoDocentes) {
        System.out.println("----LISTA DE FUNCIONARIOS NAO DOCENTES----");
        Iterator<NaoDocente> it = naoDocentes.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarCursos(ArrayList<Curso> cursos) {
        System.out.println("----LISTA DE CURSOS----");
        Iterator<Curso> it = cursos.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static void listarDisciplinas(ArrayList<Disciplina> disciplinas) {
        System.out.println("----LISTA DE DISCIPLINAS----");
        Iterator<Disciplina> it = disciplinas.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    private static Exame procurarExame(ArrayList<Exame> exames) {
        listarExames(exames);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return exames.get(opcao - 1);
    }

    private static Aluno procurarAluno(ArrayList<Aluno> alunos) {
        listarAlunos(alunos);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return alunos.get(opcao - 1);
    }

    private static Docente procurarDocente(ArrayList<Docente> docentes) {
        listarDocentes(docentes);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return docentes.get(opcao - 1);
    }

    private static NaoDocente procurarNaoDocente(ArrayList<NaoDocente> naoDocentes) {
        listarNaoDocentes(naoDocentes);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return naoDocentes.get(opcao - 1);
    }

    private static Curso procurarCurso(ArrayList<Curso> cursos) {
        listarCursos(cursos);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return cursos.get(opcao - 1);
    }

    private static Disciplina procurarDisciplina(ArrayList<Disciplina> disciplinas) {
        listarDisciplinas(disciplinas);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return disciplinas.get(opcao - 1);
    }

    private static void menu(ArrayList<Exame> exames, ArrayList<Aluno> alunos, ArrayList<Docente> docentes, ArrayList<NaoDocente> naoDocentes, ArrayList<Curso> cursos, ArrayList<Disciplina> disciplinas) {
        Docente docente = new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "Engenharia de Software");
        Disciplina disciplina = new Disciplina("POO", docente, docentes, alunos);
        Curso curso = new Curso("Engenharia Informatica", 3, "Licenciatura", disciplinas);
        Aluno aluno = new Aluno("Teresa", "teresa.sal13@gmail.com", 20, 1, curso, "normal");
        NaoDocente naoDocente = new NaoDocente("Jorge", "jorge@gmail.com", 10, "Tecnico", "Secretaria");
        Data data = new Data(1, 2, 3, 4, 5);
        Exame exame = new ExameNormal(disciplina, data, 10, docente, docentes);

        docentes.add(docente);
        disciplinas.add(disciplina);
        cursos.add(curso);
        alunos.add(aluno);
        naoDocentes.add(naoDocente);
        exames.add(exame);

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
                    "4 - Inscreve aluno em exame\n" +
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
                    adicionarExame(exames, disciplinas);
                    break;
                case 2:
                    configurarSala(exames);
                    break;
                case 3:
                    convocarFuncionarios(exames, docentes, naoDocentes);
                    break;
                case 4:
                    inscreverAluno(alunos, exames);
                    break;
                case 5:
                    Exame exameEscolhido = procurarExame(exames);
                    exameEscolhido.lancarNotas();
                    break;
                case 6:
                    listarExames(exames);
                    break;
                case 10:

                case 11:
                    Exame exameEscolhidoNotas = procurarExame(exames);
                    exameEscolhidoNotas.listarNotas();
                    break;
                default:
                    return;
            }
        }
    }

    public static void main(String args[]){
        ArrayList<Exame> exames = new ArrayList<Exame>();
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        ArrayList<Docente> docentes = new ArrayList<Docente>();
        ArrayList<NaoDocente> naoDocentes = new ArrayList<NaoDocente>();
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
        menu(exames, alunos, docentes, naoDocentes, cursos, disciplinas);
    }
}