import java.io.*;

public class Ficheiro {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public boolean abreLeitura(String nomeDoFicheiro){
        try {
            ois = new ObjectInputStream(new FileInputStream(nomeDoFicheiro));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void abreEscrita(String nomeDoFicheiro) throws IOException {
        oos = new ObjectOutputStream(new FileOutputStream(nomeDoFicheiro));
    }

    public void escreveObjecto(Object o) throws IOException {
        oos.writeObject(o);
    }

    public Object leObjecto() throws IOException, ClassNotFoundException{
        return ois.readObject();
    }

    public void fechaLeitura() throws  IOException{
        ois.close();
    }

    public void fechaEscrita() throws IOException{
        oos.close();
    }
}
