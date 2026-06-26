package Prova_Final;

import java.util.ArrayList;

public class DadosUsuario {
    public String nomeUsuario;
    public ArrayList<Serie> favoritos;
    public ArrayList<Serie> jaAssistidas;
    public ArrayList<Serie> desejaAssistir;

    public DadosUsuario() {
        this.nomeUsuario = "Usuário Padrão";
        this.favoritos = new ArrayList<>();
        this.jaAssistidas = new ArrayList<>();
        this.desejaAssistir = new ArrayList<>();
    }
}