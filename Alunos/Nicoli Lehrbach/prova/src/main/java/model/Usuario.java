package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidos;
    private List<Serie> queroAssistir;

    public Usuario() {
        this.favoritos = new ArrayList<>();
        this.assistidos = new ArrayList<>();
        this.queroAssistir = new ArrayList<>();
    }

    public Usuario(String nome, List<Serie> favoritos, List<Serie> assistidos, List<Serie> queroAssistir) {
        this.nome = nome;
        this.favoritos = (favoritos != null) ? favoritos : new ArrayList<>();
        this.assistidos = (assistidos != null) ? assistidos : new ArrayList<>();
        this.queroAssistir = (queroAssistir != null) ? queroAssistir : new ArrayList<>();
    }

    public void garantirListasInicializadas() {
        if (favoritos == null) favoritos = new ArrayList<>();
        if (assistidos == null) assistidos = new ArrayList<>();
        if (queroAssistir == null) queroAssistir = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Serie> getFavoritos() {
        garantirListasInicializadas();
        return favoritos;
    }

    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = (favoritos != null) ? favoritos : new ArrayList<>();
    }

    public List<Serie> getAssistidos() {
        garantirListasInicializadas();
        return assistidos;
    }

    public void setAssistidos(List<Serie> assistidos) {
        this.assistidos = (assistidos != null) ? assistidos : new ArrayList<>();
    }

    public List<Serie> getQueroAssistir() {
        garantirListasInicializadas();
        return queroAssistir;
    }

    public void setQueroAssistir(List<Serie> queroAssistir) {
        this.queroAssistir = (queroAssistir != null) ? queroAssistir : new ArrayList<>();
    }

    public void adicionarAoFavoritos(Serie serie) {
        if (serie != null && !getFavoritos().contains(serie)) getFavoritos().add(serie);
    }

    public void removerDosFavoritos(Serie serie) {
        getFavoritos().remove(serie);
    }

    public void adicionarAoAssistidos(Serie serie) {
        if (serie != null && !getAssistidos().contains(serie)) getAssistidos().add(serie);
    }

    public void removerDosAssistidos(Serie serie) {
        getAssistidos().remove(serie);
    }

    public void adicionarAoQueroAssistir(Serie serie) {
        if (serie != null && !getQueroAssistir().contains(serie)) getQueroAssistir().add(serie);
    }

    public void removerDosQueroAssistir(Serie serie) {
        getQueroAssistir().remove(serie);
    }
}
