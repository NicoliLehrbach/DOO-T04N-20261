import java.util.Comparator;
import java.util.List;

public class SistemaSeries {

    private Usuario usuario;

    public SistemaSeries(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void adicionarFavorito(Serie serie) {

        if (!usuario.getFavoritos().contains(serie)) {
            usuario.getFavoritos().add(serie);
        }

    }

    public void adicionarAssistida(Serie serie) {

        if (!usuario.getAssistidas().contains(serie)) {
            usuario.getAssistidas().add(serie);
        }

    }

    public void adicionarDesejo(Serie serie) {

        if (!usuario.getDesejoAssistir().contains(serie)) {
            usuario.getDesejoAssistir().add(serie);
        }

    }

    public void removerFavorito(Serie serie) {
        usuario.getFavoritos().remove(serie);
    }

    public void removerAssistida(Serie serie) {
        usuario.getAssistidas().remove(serie);
    }

    public void removerDesejo(Serie serie) {
        usuario.getDesejoAssistir().remove(serie);
    }

    public void ordenarFavoritosPorNome() {

        usuario.getFavoritos().sort(
                Comparator.comparing(
                        Serie::getNome,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarFavoritosPorNota() {

        usuario.getFavoritos().sort(
                Comparator.comparingDouble(Serie::getNota)
                        .reversed()
        );

    }

    public void ordenarFavoritosPorStatus() {

        usuario.getFavoritos().sort(
                Comparator.comparing(
                        Serie::getStatus,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarFavoritosPorEstreia() {

        usuario.getFavoritos().sort(
                Comparator.comparing(
                        Serie::getEstreia,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarAssistidasPorNome() {

        usuario.getAssistidas().sort(
                Comparator.comparing(
                        Serie::getNome,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarAssistidasPorNota() {

        usuario.getAssistidas().sort(
                Comparator.comparingDouble(Serie::getNota)
                        .reversed()
        );

    }

    public void ordenarAssistidasPorStatus() {

        usuario.getAssistidas().sort(
                Comparator.comparing(
                        Serie::getStatus,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarAssistidasPorEstreia() {

        usuario.getAssistidas().sort(
                Comparator.comparing(
                        Serie::getEstreia,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarDesejoPorNome() {

        usuario.getDesejoAssistir().sort(
                Comparator.comparing(
                        Serie::getNome,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarDesejoPorNota() {

        usuario.getDesejoAssistir().sort(
                Comparator.comparingDouble(Serie::getNota)
                        .reversed()
        );

    }

    public void ordenarDesejoPorStatus() {

        usuario.getDesejoAssistir().sort(
                Comparator.comparing(
                        Serie::getStatus,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public void ordenarDesejoPorEstreia() {

        usuario.getDesejoAssistir().sort(
                Comparator.comparing(
                        Serie::getEstreia,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

    }

    public List<Serie> getFavoritos() {
        return usuario.getFavoritos();
    }

    public List<Serie> getAssistidas() {
        return usuario.getAssistidas();
    }

    public List<Serie> getDesejoAssistir() {
        return usuario.getDesejoAssistir();
    }

}