import java.io.Serializable;

public class Docente extends Funcionario implements Serializable {
    private String areaInvestigacao;

    public Docente() {}

    public Docente(String nome, String email, int numeroMecanografico, String categoria, String areaInvestigacao) {
        super(nome, email, numeroMecanografico, categoria);
        this.areaInvestigacao = areaInvestigacao;
    }

    public String getAreaInvestigacao() {
        return areaInvestigacao;
    }

    public void setAreaInvestigacao(String areaInvestigacao) {
        this.areaInvestigacao = areaInvestigacao;
    }

    @Override
    public String toString() {
        return "Docente: " + nome + '\n' +
                "Email: " + email + '\n' +
                "Numero mecanografico: " + numeroMecanografico + '\n' +
                "Categoria: " + categoria + '\n' +
                "areaInvestigacao: " + areaInvestigacao + '\n' +
                "----";
    }
}
