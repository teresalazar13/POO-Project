import java.io.Serializable;

public class NaoDocente extends Funcionario implements Serializable{
    private String cargo;

    public  NaoDocente() {}

    public NaoDocente(String nome, String email, int numeroMecanografico, String categoria, String cargo) {
        super(nome, email, numeroMecanografico, categoria);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Nao Docente: " + nome + '\n' +
                "Email: " + email + '\n' +
                "Numero mecanografico: " + numeroMecanografico + '\n' +
                "Categoria: " + categoria + '\n' +
                "Cargo: " + cargo + '\n' +
                "----";
    }
}
