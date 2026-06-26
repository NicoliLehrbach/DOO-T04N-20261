package Prova_Final;

public class Serie {
    private String nome;
    private String idioma;
    private String generos;
    private double nota;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;

    public Serie() {} // Necessário para o Jackson

    public Serie(String nome, String idioma, String generos, double nota, String estado, String dataEstreia, String dataTermino, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
    }

    // Getters
    public String getNome() { return nome; }
    public String getIdioma() { return idioma; }
    public String getGeneros() { return generos; }
    public double getNota() { return nota; }
    public String getEstado() { return estado; }
    public String getDataEstreia() { return dataEstreia != null ? dataEstreia : ""; }
    public String getDataTermino() { return dataTermino != null ? dataTermino : ""; }
    public String getEmissora() { return emissora; }

    public Object[] paraLinhaTabela() {
        return new Object[]{nome, idioma, generos, nota, estado, dataEstreia, dataTermino, emissora};
    }
}