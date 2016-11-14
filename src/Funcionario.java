public class Funcionario extends Pessoa {
    protected int numeroMecanografico;
    protected String categoria;

    public Funcionario() {}

    public Funcionario(String nome, String email, int numeroMecanografico, String categoria) {
        super(nome, email);
        this.numeroMecanografico = numeroMecanografico;
        this.categoria = categoria;
    }

    public int getNumeroMecanografico() {

        return numeroMecanografico;
    }

    public void setNumeroMecanografico(int numeroMecanografico) {
        this.numeroMecanografico = numeroMecanografico;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Funcionario: " + '\n' +
                "Numero mecanografico: " + numeroMecanografico + '\n' +
                "Categoria: " + categoria + '\n' +
                "----";
    }
}
