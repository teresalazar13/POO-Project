import java.util.StringTokenizer;

public class Curso {
    private String nome;
    private String grau;

    public Curso(String nome, String grau) {
        this.nome = nome;
        this.grau = grau;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }
}
