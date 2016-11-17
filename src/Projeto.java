import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Projeto {

    private static void adicionarExame(ArrayList<Exame> exames, ArrayList<Disciplina> disciplinas) {
        Scanner sc = new Scanner(System.in);
        Disciplina disciplina = escolherDisciplina(disciplinas);
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
        Exame exame = escolherExame(exames);
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


    private static void inscreverAluno(ArrayList<Pessoa> pessoas, ArrayList<Exame> exames) { // Inscrever alunos em exame, assegurando que estao inscritos em disciplina e que tem acesso a epoca do exame
        Aluno aluno = (Aluno) escolherPessoa(pessoas, "Aluno");
        Exame exame = escolherExame(exames);
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

    private static void convocarFuncionarios(ArrayList<Exame> exames, ArrayList<Pessoa> pessoas) { // Convocar vigilantes e funcionários
        Scanner sc = new Scanner(System.in);
        Exame exame = escolherExame(exames);
        ArrayList<Docente> vigilantes = exame.getVigilantes();
        while(true) {
            Docente vigilante = (Docente) escolherPessoa(pessoas, "Docente");
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
            NaoDocente funcionarioNaoDocente = (NaoDocente) escolherPessoa(pessoas, "NaoDocente");
            System.out.print("Adicionar mais? (0 ou 1)");
            int opcao = sc.nextInt();
            funcionariosNaoDocentes.add(funcionarioNaoDocente);
            if (opcao == 0) {
                break;
            }
        }
    }

    private static void listarExamesAluno(ArrayList<Exame> exames, ArrayList<Pessoa> pessoas) {
        Aluno aluno = (Aluno) escolherPessoa(pessoas, "Aluno");
        int numeroAluno = aluno.getNumero();
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).verificaAlunoInscrito(numeroAluno))
                System.out.println(exames.get(i));
        }
    }

    private static void listarExamesFuncionario(ArrayList<Exame> exames, ArrayList<Pessoa> pessoas) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Verificar:\n1- Docente Vigilante\n 2- Funcionario de apoio");
        int opcao = sc.nextInt();
        if (opcao == 1) {
            Docente docente = (Docente) escolherPessoa(pessoas, "Docente");
            for (int i = 0; i < exames.size(); i++) {
                if (exames.get(i).verificaVigilante(docente.getNumeroMecanografico())) {
                    System.out.println(exames.get(i));
                }
            }
        }
        else if (opcao == 2) {
            NaoDocente naoDocente = (NaoDocente) escolherPessoa(pessoas, "NaoDocente");
            for (int i = 0; i < exames.size(); i++) {
                if (exames.get(i).verificaFuncionarioNaoDocente(naoDocente.getNumeroMecanografico())) {
                    System.out.println(exames.get(i));
                }
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

    private static void listarPessoas(ArrayList<Pessoa> pessoas, String classe) {
        System.out.println("----LISTA DE "+ classe.toUpperCase() + "----");
        for (int i = 0; i < pessoas.size(); i++) {
            String className = pessoas.get(i).getClass().getName();
            if (className.equals(classe)) {
                System.out.println(i+1 + ": " + pessoas.get(i).toString());
            }
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

    private static Exame escolherExame(ArrayList<Exame> exames) {
        listarExames(exames);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return exames.get(opcao - 1);
    }

    private static Pessoa escolherPessoa(ArrayList<Pessoa> pessoas, String classe) {
        listarPessoas(pessoas, classe);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return (Aluno) pessoas.get(opcao - 1);
    }

    private static Curso escolherCurso(ArrayList<Curso> cursos) {
        listarCursos(cursos);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return cursos.get(opcao - 1);
    }

    private static Disciplina escolherDisciplina(ArrayList<Disciplina> disciplinas) {
        listarDisciplinas(disciplinas);
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        int opcao = sc.nextInt();
        return disciplinas.get(opcao - 1);
    }

    private static void menu(ArrayList<Exame> exames, ArrayList<Aluno> alunos, ArrayList<Docente> docentes, ArrayList<NaoDocente> naoDocentes, ArrayList<Pessoa> pessoas, ArrayList<Curso> cursos, ArrayList<Disciplina> disciplinas) {
        Docente docente = new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "Engenharia de Software");
        Disciplina disciplina = new Disciplina("POO", docente, docentes, alunos);
        Curso curso = new Curso("Engenharia Informatica", 3, "Licenciatura", disciplinas);
        Aluno aluno = new Aluno("Teresa", "teresa.sal13@gmail.com", 20, 1, curso, "normal");
        NaoDocente naoDocente = new NaoDocente("Jorge", "jorge@gmail.com", 10, "Tecnico", "Secretaria");
        Data data = new Data(1, 2, 3, 4, 5);
        Exame exame = new ExameNormal(disciplina, data, 10, docente, docentes);

        docentes.add(docente);
        pessoas.add(docente);
        disciplinas.add(disciplina);
        cursos.add(curso);
        alunos.add(aluno);
        pessoas.add(aluno);
        naoDocentes.add(naoDocente);
        pessoas.add(naoDocente);
        exames.add(exame);
        
        /*listarExames();
        listarPessoas(pessoas, "Docente");
        listarCursos();
        listarDisciplinas();*/

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
                    convocarFuncionarios(exames, pessoas);
                    break;
                case 4:
                    inscreverAluno(pessoas, exames);
                    break;
                case 5:
                    Exame exameEscolhido = escolherExame(exames);
                    exameEscolhido.lancarNotas();
                    break;
                case 6:
                    listarExames(exames);
                    break;
                case 7:
                    Exame exameEscolhidoNotas = escolherExame(exames);
                    exameEscolhidoNotas.listarNotas();
                    break;
                case 8:
                    listarExamesAluno(exames, pessoas);
                    break;
                case 9:
                    Exame exameEscolhidoFuncionarios = escolherExame(exames);
                    exameEscolhidoFuncionarios.listarFuncionarios();
                case 10:
                    listarExamesFuncionario(exames, pessoas);
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
        ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
        menu(exames, alunos, docentes, naoDocentes, pessoas, cursos, disciplinas);
    }
}