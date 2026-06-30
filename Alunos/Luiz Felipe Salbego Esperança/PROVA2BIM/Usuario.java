import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;

    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejoAssistir;

    public Usuario() {

        favoritos = new ArrayList<>();
        assistidas = new ArrayList<>();
        desejoAssistir = new ArrayList<>();

    }

    public Usuario(String nome) {

        this();

        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public void setAssistidas(List<Serie> assistidas) {
        this.assistidas = assistidas;
    }

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }

    public void setDesejoAssistir(List<Serie> desejoAssistir) {
        this.desejoAssistir = desejoAssistir;
    }

}