public class Docente extends Funcionario {
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
        return "Docente{" +
                "areaInvestigacao='" + areaInvestigacao + '\'' +
                '}';
    }
}
