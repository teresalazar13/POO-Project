import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Projeto {

    private static ArrayList<Exame> exames = new ArrayList<Exame>();

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