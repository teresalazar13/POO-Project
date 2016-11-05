public class Aluno extends Pessoa {
    private int numero, anoMatricula;
    private Curso curso;
    private String regime;

    public Aluno(int numero, int anoMatricula, Curso curso, String regime) {
        this.numero = numero;
        this.anoMatricula = anoMatricula;
        this.curso = curso;
        this.regime = regime;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAnoMatricula() {
        return anoMatricula;
    }

    public void setAnoMatricula(int anoMatricula) {
        this.anoMatricula = anoMatricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }
}
