import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Projeto {

    /** Adiciona Exame ao Arraylist de exames */
    public static void adicionarExame(ArrayList<Exame> exames, ArrayList<Disciplina> disciplinas) throws IOException {
        System.out.println("Escolha a disciplina do exame");
        Disciplina disciplina = escolherDisciplina(disciplinas); /** Escolhe a disciplina do exame */
        Data data = data();
        System.out.print("Duracao: ");
        int duracao = devolveInteiroValido(200); // Adiciona duraçao do exame
        Docente docenteResponsavel = disciplina.getDocente(); // Adicionar docente da disciplina ao docente Responsavel automaticamente
        ArrayList<Docente> vigilantes = new ArrayList<Docente>();
        ArrayList<Docente> docentesDisciplina = disciplina.getOutrosDocentes(); // Adicionar docentes da disciplina aos vigilantes se tiverem disponibilidade
        for (int i = 0; i < docentesDisciplina.size(); i++) {
            if (verificarDisponibilidadeDocente(exames, data, duracao, docentesDisciplina.get(i)) == 1) {
                vigilantes.add(docentesDisciplina.get(i));
            }
        }
        System.out.println("Escolha o tipo de exame");
        System.out.println("1: Exame Normal\n2: Exame Recurso\n3: Exame Especial\nOpcao: ");
        int opcao = devolveInteiroValido(3);
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
        System.out.println("Exame adicionado com sucesso");
        atualizarFicheiroExames(exames);
    }

    /** Configura a sala para o exame */
    public static void configurarSala(ArrayList<Exame> exames) throws IOException {
        System.out.println("Escolha o exame para o qual pretende marcar sala");
        Exame exame = escolherExame(exames);
        Data data = exame.getData();
        int duracao = exame.getDuracao();
        System.out.println("Sala: ");
        int sala = devolveInteiroValido(10);
        if (sala == exame.getSala()) { // Verifica se exame ja estava marcado para esta sala
            System.out.println("Este exame ja estava marcado para esta sala.");
            return;
        }
        for (int i = 0; i < exames.size(); i++) { // Verifica se ha disponibilidade para a sala na hora do exame pretendido
            Data dataExame = exames.get(i).getData();
            int duracaoExame = exames.get(i).getDuracao();
            if (compararData(data, duracao, dataExame, duracaoExame) == 0 && exames.get(i).getSala() == sala) {
                System.out.println("Nao ha disponibilidade para esta sala.");
                return;
            }
        }
        exame.setSala(sala);
        System.out.println("Sala marcada.");
        atualizarFicheiroExames(exames);
    }

    /** Convoca Funcionarios para um exame */
    public static void convocarFuncionarios(ArrayList<Exame> exames, ArrayList<Pessoa> pessoas) throws IOException {
        Exame exame = escolherExame(exames);
        ArrayList<Docente> vigilantes = exame.getVigilantes();
        while(true) {
            System.out.println("Pretende adicionar Docentes? (1(Sim), 2(Nao))");
            if (devolveInteiroValido(2) == 2) {
                break;
            }
            Docente vigilante = (Docente) escolherPessoa(pessoas, "Docente");
            if (exame.contemDocente(vigilante)) { // Verifica se exame ja continha o docente desejado
                System.out.println("Docente já estava a vigilar exame");
            }
            else { // Verificar disponibilidade do docente
                if (verificarDisponibilidadeDocente(exames, exame.getData(), exame.getDuracao(), vigilante) == 1) {
                    System.out.println("Docente adicionado ao exame com sucesso");
                    vigilantes.add(vigilante);
                } else {
                    System.out.println("Vigilante nao tem disponibilidade.");
                }
            }
        }
        ArrayList<NaoDocente> funcionariosNaoDocentes = exame.getFuncionariosNaoDocentes();
        while(true) {
            System.out.println("Pretende adicionar Nao Docentes? (1(Sim), 2(Nao))");
            if (devolveInteiroValido(2) == 2) {
                break;
            }
            NaoDocente funcionarioNaoDocente = (NaoDocente) escolherPessoa(pessoas, "NaoDocente");
            if (exame.contemNaoDocente(funcionarioNaoDocente)) {
                System.out.println("Exame ja continha Funcionario nao docente.");
            }
            else {
                System.out.println("Funcionario nao docente adicionado ao exame com sucesso.");
                funcionariosNaoDocentes.add(funcionarioNaoDocente);
            }
        }
        atualizarFicheiroExames(exames);
    }

    /** Inscreve aluno em exame, assegurando que estao inscritos em disciplina e que tem acesso a epoca do exame */
    public static void inscreverAluno(ArrayList<Pessoa> pessoas, ArrayList<Exame> exames) throws IOException {
        System.out.println("Selecione aluno que pretende inscrever");
        Aluno aluno = (Aluno) escolherPessoa(pessoas, "Aluno");
        System.out.println("Selecione exame em que pretende inscrever aluno");
        Exame exame = escolherExame(exames);
        Disciplina disciplina = exame.getDisciplina();
        ArrayList<Aluno> alunosDisciplina = disciplina.getAlunos();
        if (!disciplina.contemAluno(aluno)) { // Se aluno nao estiver inscrito, a operacao e anulada
            System.out.println("Aluno nao inscrito em disciplina.");
            return;
        }
        String regime = aluno.getRegime();
        Class classe = exame.getClass();
        if (classe.toString().equals("class ExameEspecial")) { // Verifica se o aluno tem acesso a epoca deste exame
            if (regime.equals("normal") || regime.equals("erasmus")) {
                System.out.println("Aluno nao tem acesso a esta epoca de exame.");
                return; // Se nao tiver acesso, a operacao e anulada
            }
        }
        if (exame.contemAluno(aluno)) { // Verifica se aluno ja tinha sido inscrito em exame
            System.out.println("Aluno ja tinha sido inscrito em exame");
            return;
        }
        ArrayList<AlunoClassificacao> alunosClassificacao = exame.getAlunosClassificacao();
        AlunoClassificacao alunoClassificacao = new AlunoClassificacao(aluno);
        alunoClassificacao.setAluno(aluno);
        alunosClassificacao.add(alunoClassificacao);
        System.out.println("Aluno inscrito em exame com sucesso");
        atualizarFicheiroExames(exames);
    }

    /** Lista exames em que um aluno esta inscrito */
    public static void listarExamesAluno(ArrayList<Exame> exames, ArrayList<Pessoa> pessoas) {
        System.out.println("Por favor escolha o aluno ");
        Aluno aluno = (Aluno) escolherPessoa(pessoas, "Aluno");
        System.out.println("Lista de exames do aluno: " + aluno.getNome());
        int checkAlunoTemExames = 0;
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).contemAluno(aluno)) {
                checkAlunoTemExames = 1;
                System.out.println(exames.get(i));
                ArrayList<AlunoClassificacao> alunosClassificacao = exames.get(i).getAlunosClassificacao();
                int classificacao = 0;
                for (int j = 0; j < alunosClassificacao.size(); j++) {
                    if (alunosClassificacao.get(j).getAluno().getNumero() == aluno.getNumero())
                         classificacao = alunosClassificacao.get(j).getClassificacao();
                }
                if (classificacao != 0)
                    System.out.println("Classificacao: " + classificacao);
                else
                    System.out.println("Nota de aluno deste exame ainda nao foi lancada");
            }
        }
        if (checkAlunoTemExames == 0) {
            System.out.println("Aluno nao esta inscrito em nenhum exame");
        }
    }

    /** Lista exames em que um docente vigilante ou um nao docente de apoio esta inscrito */
    public static void listarExamesFuncionario(ArrayList<Exame> exames, ArrayList<Pessoa> pessoas) {
        System.out.println("Listar:\n1- Docentes Vigilantes\n2- Funcionarios de apoio");
        int opcao = devolveInteiroValido(2);
        int check = 0;
        if (opcao == 1) {
            Docente docente = (Docente) escolherPessoa(pessoas, "Docente");
            for (int i = 0; i < exames.size(); i++) {
                if (exames.get(i).contemDocente(docente)) {
                    check = 1;
                    System.out.println(exames.get(i));
                }
            }
            if (check == 0) {
                System.out.println("Docente nao esta inscrito em nenhum exame");
            }
        }
        else if (opcao == 2) {
            NaoDocente naoDocente = (NaoDocente) escolherPessoa(pessoas, "NaoDocente");
            for (int i = 0; i < exames.size(); i++) {
                if (exames.get(i).contemNaoDocente(naoDocente)) {
                    check = 1;
                    System.out.println(exames.get(i));
                }
            }
            if (check == 1) {
                System.out.println("Funcionario de apoio nao esta inscrito em nenhum exame");
            }
        }
    }

    /** Percorre a lista de exame. Se exame tiver docente, verificar se a data e a duração desse exame coincidem com as dadas */
    public static int verificarDisponibilidadeDocente(ArrayList<Exame> exames, Data data, int duracao, Docente docente) {
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

    /** Compara duas datas e durações de exames para ver se coincidem */
    public static int compararData(Data data, int duracao, Data dataExame, int duracaoExame) {
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

    /** Input para datas */
    public static Data data() {
        System.out.println("Dia");
        int dia = devolveInteiroValido(31);
        System.out.println("Mes");
        int mes = devolveInteiroValido(12);
        System.out.println("Ano");
        int ano = devolveInteiroValido(2017);
        System.out.println("Hora");
        int hora = devolveInteiroValido(23);
        System.out.println("Minuto");
        int minuto = devolveInteiroValido(59);
        return new Data(dia, mes, ano, hora, minuto);
    }

    /** Lista todos os exames */
    public static void listarExames(ArrayList<Exame> exames) {
        System.out.println("----LISTA DE EXAMES----");
        Iterator<Exame> it = exames.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    /** Lista todos os alunos, docentes ou nao docentes */
    public static Hashtable listarPessoas(ArrayList<Pessoa> pessoas, String classe) {
        Hashtable<Integer, Integer> mapIndexes = new Hashtable<Integer, Integer>(); // HashTable for mapping indexes
        System.out.println("----LISTA DE "+ classe.toUpperCase() + "S----");
        int counter = 1;
        for (int i = 0; i < pessoas.size(); i++) {
            String className = pessoas.get(i).getClass().getName();
            if (className.equals(classe)) {
                mapIndexes.put(counter, i);
                System.out.println(counter + ": " + pessoas.get(i).toString());
                counter++;
            }
        }
        return mapIndexes;
    }

    /** Lista todas as disciplinas */
    public static void listarDisciplinas(ArrayList<Disciplina> disciplinas) {
        System.out.println("----LISTA DE DISCIPLINAS----");
        Iterator<Disciplina> it = disciplinas.iterator();
        int i = 1;
        while(it.hasNext()) {
            System.out.println(i + ": " + it.next());
            i++;
        }
    }

    /** Permite escolher exame através do input de um numero */
    public static Exame escolherExame(ArrayList<Exame> exames) {
        listarExames(exames);
        int opcao = devolveInteiroValido(exames.size());
        return exames.get(opcao - 1);
    }

    /** Permite escolher pessoa (aluno, docente ou nao docente) através do input de um numero */
    public static Pessoa escolherPessoa(ArrayList<Pessoa> pessoas, String classe) {
        Hashtable<Integer, Integer> mapIndexes = listarPessoas(pessoas, classe);
        int opcao = devolveInteiroValido(mapIndexes.size());
        return pessoas.get(mapIndexes.get(opcao));
    }

    /** Permite escolher disciplina através do input de um numero */
    public static Disciplina escolherDisciplina(ArrayList<Disciplina> disciplinas) {
        listarDisciplinas(disciplinas);
        int opcao = devolveInteiroValido(disciplinas.size());
        return disciplinas.get(opcao - 1);
    }

    // Menu
    public static void menu(ArrayList<Exame> exames, ArrayList<Pessoa> pessoas, ArrayList<Disciplina> disciplinas) throws IOException, ClassNotFoundException {
        /*Docente docente1= new Docente("Marilia Curado", "mariliacurado@dei.uc.pt", 1, "Catedratico", "ES");
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
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();

        Disciplina disciplinaPOO = new Disciplina("POO", docente1, docentesPOO, alunos);
        Disciplina disciplinaSO = new Disciplina("SO", docente2, docentesSO, alunos);
        Disciplina disciplinaTI = new Disciplina("TI", docente4, docentesTI, alunos);

        disciplinas.add(disciplinaPOO);
        disciplinas.add(disciplinaSO);
        disciplinas.add(disciplinaTI);

        ArrayList<Disciplina> disciplinasEI = new ArrayList<Disciplina>();
        ArrayList<Disciplina> disciplinasDM = new ArrayList<Disciplina>();
        disciplinasEI.add(disciplinaPOO);
        disciplinasEI.add(disciplinaSO);
        disciplinasEI.add(disciplinaTI);
        disciplinasDM.add(disciplinaTI);

        Curso cursoEI = new Curso("Engenharia Informatica", 5, "Mestrado", disciplinasEI);
        Curso cursoDM = new Curso("LDM", 3, "Licenciatura", disciplinasDM);

        Aluno a1 = new Aluno("Teresa", "teresa.sal13@gmail.com", 1, 1, cursoEI, "normal");
        Aluno a2 = new Aluno("Joao", "johnny@gmail.com", 2, 2, cursoEI, "trabalhador-estudante");
        Aluno a3 = new Aluno("Sofia", "sofs@gmail.com", 3, 3, cursoDM, "atleta");
        Aluno a4 = new Aluno("Maria", "mary@gmail.com", 4, 4, cursoDM, "erasmus");
        Aluno a5 = new Aluno("Jorge", "jorjao@gmail.com", 5, 5, cursoEI, "dirigente-associativo");
        Aluno a6 = new Aluno("Joana", "juju@gmail.com", 6, 6, cursoDM, "normal");
        Aluno a7 = new Aluno("Abilio", "bibi@gmail.com", 7, 7, cursoEI, "erasmus");
        Aluno a8 = new Aluno("Zeca", "zeca@gmail.com", 8, 8, cursoDM, "normal");

        alunos.add(a1);
        alunos.add(a2);
        alunos.add(a3);
        alunos.add(a4);
        alunos.add(a5);
        alunos.add(a6);
        alunos.add(a7);
        alunos.add(a8);

        disciplinaPOO.setAlunos(alunos);
        disciplinaSO.setAlunos(alunos);
        disciplinaTI.setAlunos(alunos);

        NaoDocente naoDocente = new NaoDocente("Isaura", "isaura@gmail.com", 10, "Especialista de Informática", "Apoio técnico");

        Data data1 = new Data(4,12,2016,14,30);
        Data data2 = new Data(5,12,2016,14,35);
        Data data3 = new Data(6,12,2016,14,50);

        pessoas.add(docente1);
        pessoas.add(docente2);
        pessoas.add(docente3);
        pessoas.add(docente4);
        pessoas.add(docente5);
        pessoas.add(a1);
        pessoas.add(a2);
        pessoas.add(a3);
        pessoas.add(a4);
        pessoas.add(a5);
        pessoas.add(a6);
        pessoas.add(a7);
        pessoas.add(a8);
        pessoas.add(naoDocente);

        Exame exame1 = new ExameNormal(disciplinaPOO, data1, 50, disciplinaPOO.getDocente(), disciplinaPOO.getOutrosDocentes());
        Exame exame2 = new ExameNormal(disciplinaTI, data2, 20, disciplinaTI.getDocente(), disciplinaTI.getOutrosDocentes());
        Exame exame3 = new ExameRecurso(disciplinaSO, data3, 20, disciplinaSO.getDocente(), disciplinaSO.getOutrosDocentes());
        Exame exame4 = new ExameEspecial(disciplinaTI, data3, 20, disciplinaTI.getDocente(), disciplinaTI.getOutrosDocentes());
        exames.add(exame1);
        exames.add(exame2);
        exames.add(exame3);
        exames.add(exame4);

        escreverFicheiros(pessoas, "Pessoas");
        escreverFicheiros(exames, "Exames");
        escreverFicheiros(disciplinas, "Disciplinas");*/

        pessoas = (ArrayList<Pessoa>) lerFicheiros("Pessoa");
        exames = (ArrayList<Exame>) lerFicheiros("Exame");
        disciplinas = (ArrayList<Disciplina>) lerFicheiros("Disciplina");

        ArrayList <String[]> users = new ArrayList<String[]>();
        FileReader fileReader = new FileReader("Ficheiros/Autenticacao.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String arrayLogin[] = line.split(",");
            users.add(arrayLogin);
            i++;
        }
        fileReader.close();
        registarLogin(users);

        while(true) {
            System.out.println("Pretende\n" +
                    "1 - Criar Exame\n" +
                    "2 - Configurar sala de exame\n" +
                    "3 - Convocar vigilantes e funcionários\n" +
                    "4 - Inscrever aluno em exame\n" +
                    "5 - Lançar notas de um exame\n" +
                    "6 - Listar Exames\n" +
                    "7 - Listar alunos de exame e respetivas classificações\n" +
                    "8 - Listar exames de aluno e respetivas classificações\n" +
                    "9 - Listar docentes e funcionarios de exame\n" +
                    "10 - Listar exames de docente ou funcionario\n" +
                    "11 para sair");
            int opcao = devolveInteiroValido(11);
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
                    atualizarFicheiroExames(exames);
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

    /** Faz o registo ou o login */
    public static void registarLogin(ArrayList<String[]> users) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escolha a opcao:\n1 - Registar\n2 - Login");
        int opcao = devolveInteiroValido(2);
        if (opcao == 1) {
            System.out.println("Username");
            String username = sc.next();
            System.out.println("Password");
            String password = sc.next();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Ficheiros/Autenticacao.txt", true)));
            out.println(username + "," + password);
            out.close();
            System.out.println("Bem vindo " + username + "!");
        }
        else while (true) {
            System.out.println("Username: ");
            String username = sc.next();
            System.out.println("Password: ");
            String password = sc.next();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i)[0].equals(username) && users.get(i)[1].equals(password)) {
                    System.out.println("Bem vindo " + username + "!");
                    return;
                }
            }
            System.out.println("Username ou password invalidos");
        }
    }

    /** Protecao para todos os input. Devolve sempre um inteiro valido entre 1 e numero maximo */
    public static int devolveInteiroValido(int numeroMaximo) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Opcao: ");
        while (true) {
            while (!sc.hasNextInt()) { // Caso o input nao seja um numero
                System.out.println("Por favor escreva um numero inteiro");
                sc.next();
            }
            int opcao = sc.nextInt();
            if (numeroMaximo < opcao || opcao <= 0) { // Caso o index nao seja correto
                System.out.println("Por favor escreve um numero entre 1 e " + numeroMaximo);
            }
            else {
                return opcao;
            }
        }
    }

    /** Permite escrever em ficheiros */
    private static void escreverFicheiros(Object classe, String nomeClasse) throws IOException, ClassNotFoundException {
        String nomeFicheiro = "Ficheiros/" + nomeClasse + ".dat";
        Ficheiro ficheiro = new Ficheiro();
        ficheiro.abreEscrita(nomeFicheiro);
        ficheiro.escreveObjecto(classe);
        ficheiro.fechaEscrita();
    }

    /** Permite ler ficheiros */
    public static ArrayList lerFicheiros(String nomeClasse) throws IOException, ClassNotFoundException {
        String nomeFicheiro = "Ficheiros/" + nomeClasse + "s" + ".dat";
        Ficheiro ficheiro = new Ficheiro();
        ficheiro.abreLeitura(nomeFicheiro);
        ArrayList <Object> array = null;
        try {
            array = (ArrayList <Object>) ficheiro.leObjecto();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ficheiro.fechaLeitura();
        return array;
    }

    /** Permite atualizar Ficheiro de Exames */
    public static void atualizarFicheiroExames(ArrayList<Exame> exames) throws IOException {
        Ficheiro ficheiro = new Ficheiro();
        ficheiro.abreEscrita("Ficheiros/Exames.dat");
        ficheiro.escreveObjecto(exames);
        ficheiro.fechaEscrita();
    }

    // Main
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        ArrayList<Exame> exames = new ArrayList<Exame>();
        ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
        ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
        try {
            menu(exames, pessoas, disciplinas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}