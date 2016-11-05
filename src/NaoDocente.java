public class NaoDocente extends Funcionario {
    private String cargo;

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
}
