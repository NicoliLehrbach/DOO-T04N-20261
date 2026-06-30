public class Serie {

    private String nome;
    private String idioma;
    private String generos;
    private double nota;
    private String status;
    private String estreia;
    private String termino;
    private String emissora;
    private String resumo;

    public Serie() {

    }

    public Serie(String nome,
                 String idioma,
                 String generos,
                 double nota,
                 String status,
                 String estreia,
                 String termino,
                 String emissora,
                 String resumo) {

        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.estreia = estreia;
        this.termino = termino;
        this.emissora = emissora;
        this.resumo = resumo;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstreia() {
        return estreia;
    }

    public void setEstreia(String estreia) {
        this.estreia = estreia;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    @Override
    public String toString() {
        return nome;
    }

}