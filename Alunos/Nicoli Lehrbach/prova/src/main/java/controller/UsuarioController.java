package controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.PersistenciaDados;
import model.Serie;
import model.Usuario;

public class UsuarioController {
    private Usuario usuario;
    private final PersistenciaDados gerenciadorPersistencia;

    public UsuarioController() {
        this.gerenciadorPersistencia = new PersistenciaDados(new ObjectMapper());
        inicializarUsuario();
    }

    private void inicializarUsuario() {
        try {
            Usuario usuarioCarregado = gerenciadorPersistencia.carregarDadosUsuario();
            this.usuario = (usuarioCarregado != null) ? usuarioCarregado : new Usuario();
        } catch (IOException e) {
            e.printStackTrace();
            this.usuario = new Usuario();
        }

        this.usuario.garantirListasInicializadas();
    }

    public void salvarDados() throws IOException {
        usuario.garantirListasInicializadas();
        gerenciadorPersistencia.salvarDadosUsuario(usuario);
    }

    private void salvarDadosComSeguranca() {
        try {
            salvarDados();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuario() {
        usuario.garantirListasInicializadas();
        return usuario;
    }

    public void atualizarNomeUsuario(String nome) {
        usuario.setNome(nome);
        salvarDadosComSeguranca();
    }

    public void favoritarSerie(Serie serie) {
        usuario.adicionarAoFavoritos(serie);
        salvarDadosComSeguranca();
    }

    public void desfavoritarSerie(Serie serie) {
        usuario.removerDosFavoritos(serie);
        salvarDadosComSeguranca();
    }

    public void marcarComoAssistida(Serie serie) {
        usuario.adicionarAoAssistidos(serie);
        salvarDadosComSeguranca();
    }

    public void removerDeAssistidas(Serie serie) {
        usuario.removerDosAssistidos(serie);
        salvarDadosComSeguranca();
    }

    public void adicionarListaQueroAssistir(Serie serie) {
        usuario.adicionarAoQueroAssistir(serie);
        salvarDadosComSeguranca();
    }

    public void removerListaQueroAssistir(Serie serie) {
        usuario.removerDosQueroAssistir(serie);
        salvarDadosComSeguranca();
    }

    public void ordenarListaPorNome(List<Serie> listaSeries) {
        listaSeries.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
        salvarDadosComSeguranca();
    }

    public void ordenarListaPorNota(List<Serie> listaSeries) {
        listaSeries.sort(Comparator.comparingDouble(Serie::getNota).reversed());
        salvarDadosComSeguranca();
    }

    public void ordenarListaPorStatus(List<Serie> listaSeries) {
        listaSeries.sort((s1, s2) -> {
            String status1 = (s1.getStatus() != null) ? s1.getStatus() : "Desconhecido";
            String status2 = (s2.getStatus() != null) ? s2.getStatus() : "Desconhecido";

            boolean s1IsRunning = status1.equalsIgnoreCase("Running");
            boolean s2IsRunning = status2.equalsIgnoreCase("Running");

            if (s1IsRunning && s2IsRunning) return 0;
            if (s1IsRunning) return -1;
            if (s2IsRunning) return 1;

            return status1.compareToIgnoreCase(status2);
        });

        salvarDadosComSeguranca();
    }

    public void ordenarListaPorDataEstreia(List<Serie> listaSeries) {
        listaSeries.sort((serie1, serie2) -> {
            String data1 = serie1.getDataEstreia();
            String data2 = serie2.getDataEstreia();

            boolean d1Invalida = (data1 == null || data1.isEmpty() || data1.equalsIgnoreCase("N/A"));
            boolean d2Invalida = (data2 == null || data2.isEmpty() || data2.equalsIgnoreCase("N/A"));

            if (d1Invalida && d2Invalida) return 0;
            if (d1Invalida) return 1;
            if (d2Invalida) return -1;

            return data1.compareTo(data2);
        });

        salvarDadosComSeguranca();
    }
}
