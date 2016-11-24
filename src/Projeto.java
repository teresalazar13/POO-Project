import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;

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
            if (regime.equals("normal") || regime.equals("erasmus")) {
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
        return pessoas.get(opcao - 1);
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

    private static void menu(ArrayList<Exame> exames, ArrayList<Aluno> alunos, ArrayList<Docente> docentes, ArrayList<NaoDocente> naoDocentes, ArrayList<Pessoa> pessoas, ArrayList<Curso> cursos, ArrayList<Disciplina> disciplinas) throws IOException, ClassNotFoundException {
        Docente docente1= new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "ES");
        Docente docente2 = new Docente("Joana Silva", "js@dei.uc.pt", 2, "Assistente", "SO");
        Docente docente3 = new Docente("Carlos Cruz", "cc@dei.uc.pt", 3, "Auxiliar", "CT");
        Docente docente4 = new Docente("Ze", "mZe@dei.uc.pt", 4, "Catedratico", "ES");
        Docente docente5 = new Docente("Salazar", "sal@dei.uc.pt", 5, "Catedratico", "SI");

        ArrayList<Docente> docentesPOO = new ArrayList<Docente>();
        ArrayList<Docente> docentesSO = new ArrayList<Docente>();
        ArrayList<Docente> docentesTI = new ArrayList<Docente>();

        docentesPOO.add(docente1);
        docentesPOO.add(docente2);
        docentesSO.add(docente3);
        docentesSO.add(docente5);
        docentesSO.add(docente1);
        docentesTI.add(docente4);

        Disciplina disciplinaPOO = new Disciplina("POO", docente1, docentesPOO, alunos);
        Disciplina disciplinaSO = new Disciplina("SO", docente2, docentesSO, alunos);
        Disciplina disciplinaTI = new Disciplina("SO", docente2, docentesTI, alunos);

        ArrayList<Disciplina> disciplinasEI = new ArrayList<Disciplina>();
        ArrayList<Disciplina> disciplinasDM = new ArrayList<Disciplina>();
        disciplinasEI.add(disciplinaPOO);
        disciplinasEI.add(disciplinaSO);
        disciplinasEI.add(disciplinaTI);
        disciplinasDM.add(disciplinaTI);


        Curso cursoEI = new Curso("Engenharia Informatica", 5, "Mestrado",disciplinasEI);
        Curso cursoDM = new Curso("LDM", 3, "Licenciatura", disciplinasDM);

        Aluno a1 = new Aluno("Teresa", "teresa.sal13@gmail.com", 1 1, cursoEI, "normal");
        Aluno a2 = new Aluno("Joao", "johnny@gmail.com", 2, 2, cursoEI, "trabalhador-estudante");
        Aluno a3 = new Aluno("Sofia", "sofs@gmail.com", 3, 3, cursoDM, "atleta");
        Aluno a4 = new Aluno("Maria", "mary@gmail.com", 4, 4, cursoDM, "erasmus");
        Aluno a5 = new Aluno("Jorge", "jorjao@gmail.com", 5, 5, cursoEI, "dirigente-associativo");
        Aluno a6 = new Aluno("Joana", "juju@gmail.com", 6, 6, cursoDM, "normal");
        Aluno a7 = new Aluno("Abilio", "bibi@gmail.com", 7, 7, cursoEI, "erasmus");
        Aluno a8 = new Aluno("Zeca", "zeca@gmail.com", 8, 8, cursoDM, "normal");

        NaoDocente naoDocente = new NaoDocente("Isaura", "isaura@gmail.com", 10, "Especialista de Informática", "Apoio técnico");
        ArrayList<NaoDocente> naoDocentes1= new ArrayList<NaoDocente>();
        naoDocentes1.add(naoDocente);

        Data data1 = new Data(04,12,2016,14,30);
        Data data2 = new Data(05,12,2016,14,35);
        Data data3 = new Data(06,12,2016,14,50);

        docentes.add(docente1);
        docentes.add(docente2);
        docentes.add(docente3);
        docentes.add(docente4);
        docentes.add(docente5);
        pessoas.add(docente1);
        pessoas.add(docente2);
        pessoas.add(docente3);
        pessoas.add(docente4);
        pessoas.add(docente5);
        cursos.add(cursoDM);
        cursos.add(cursoEI);
        alunos.add(a1);
        alunos.add(a2);
        alunos.add(a3);
        alunos.add(a4);
        alunos.add(a5);
        alunos.add(a6);
        alunos.add(a7);
        alunos.add(a8);
        pessoas.add(a1);
        pessoas.add(a2);
        pessoas.add(a3);
        pessoas.add(a4);
        pessoas.add(a5);
        pessoas.add(a6);
        pessoas.add(a7);
        pessoas.add(a8);
        naoDocentes.add(naoDocente);
        pessoas.add(naoDocente);

        Exame exame1 = new ExameNormal(disciplinaPOO, data1, 50, disciplinaPOO.getDocente(), disciplinaPOO.getOutrosDocentes());
        Exame exame2 = new ExameNormal(disciplinaTI, data2, 20, disciplinaPOO.getDocente(), disciplinaPOO.getOutrosDocentes());
        Exame exame3 = new ExameRecurso(disciplinaSO, data3, 20, disciplinaPOO.getDocente(), disciplinaPOO.getOutrosDocentes());
        Exame exame4 = new ExameEspecial(disciplinaTI, data3, 20, disciplinaPOO.getDocente(), disciplinaPOO.getOutrosDocentes());
        exames.add(exame1);
        exames.add(exame2);
        exames.add(exame3);
        exames.add(exame4);

        /*
        escreverFicheiros();
        lerFicheiros();*/


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
                    break;
                case 10:
                    listarExamesFuncionario(exames, pessoas);
                    break;
                default:
                    return;
            }
        }
    }
    /*

    public static void escreverFicheiros(Object classe) throws IOException, ClassNotFoundException {
        Ficheiro ficheiroDisciplinas = new Ficheiro();
        ficheiroDisciplinas.abreEscrita("Ficheiros/Disciplinas.dat");
        ficheiroDisciplinas.escreveObjecto(disciplinas);
        ficheiroDisciplinas.fechaEscrita();
    }


    public static void lerFicheiros(Object classe) throws IOException, ClassNotFoundException {
        Ficheiro ficheiroExames = new Ficheiro();
        ficheiroExames.abreLeitura("Ficheiros/Exames.dat");
        try {
            ArrayList <Exame> exames = (ArrayList <Exame>) ficheiroExames.leObjecto();
            for (int i = 0; i < exames.size(); i++) {
                System.out.println(exames.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ficheiroExames.fechaLeitura();
    }*/

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        ArrayList<Exame> exames = new ArrayList<Exame>();
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        ArrayList<Docente> docentes = new ArrayList<Docente>();
        ArrayList<NaoDocente> naoDocentes = new ArrayList<NaoDocente>();
        ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
        try {
            menu(exames, alunos, docentes, naoDocentes, pessoas, cursos, disciplinas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}