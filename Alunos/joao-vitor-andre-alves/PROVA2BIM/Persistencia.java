import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Path;

public class Persistencia {

    private static final String ARQUIVO = "dados.json";

    public void salvar(Usuario usuario) throws Exception {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeOuApelido", usuario.getNomeOuApelido());
        dados.put("favoritas", listaParaMapas(usuario.getFavoritas()));
        dados.put("assistidas", listaParaMapas(usuario.getAssistidas()));
        dados.put("desejoAssistir", listaParaMapas(usuario.getDesejoAssistir()));

        String json = JsonUtil.escrever(dados);
        Files.writeString(Path.of(ARQUIVO), json);
    }

    public Usuario carregar() throws Exception {
        if (!Files.exists(Path.of(ARQUIVO))) {
            return null;
        }
        String json = Files.readString(Path.of(ARQUIVO));
        Map<String, Object> dados = (Map<String, Object>) JsonUtil.parse(json);

        Usuario usuario = new Usuario((String) dados.get("nomeOuApelido"));
        usuario.getFavoritas().addAll(mapasParaLista(dados.get("favoritas")));
        usuario.getAssistidas().addAll(mapasParaLista(dados.get("assistidas")));
        usuario.getDesejoAssistir().addAll(mapasParaLista(dados.get("desejoAssistir")));
        return usuario;
    }

    private List<Object> listaParaMapas(List<Serie> series) {
        List<Object> mapas = new ArrayList<>();
        for (Serie s : series) {
            mapas.add(serieParaMapa(s));
        }
        return mapas;
    }

    private Map<String, Object> serieParaMapa(Serie s) {
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("id", s.getId());
        mapa.put("nome", s.getNome());
        mapa.put("idioma", s.getIdioma());
        mapa.put("generos", s.getGeneros());
        mapa.put("nota", s.getNota());
        mapa.put("status", s.getStatus());
        mapa.put("dataEstreia", s.getDataEstreia());
        mapa.put("dataTermino", s.getDataTermino());
        mapa.put("emissora", s.getEmissora());
        return mapa;
    }

    private List<Serie> mapasParaLista(Object listaBruta) {
        List<Serie> series = new ArrayList<>();
        for (Object item : (List<Object>) listaBruta) {
            series.add(mapaParaSerie((Map<String, Object>) item));
        }
        return series;
    }

    private Serie mapaParaSerie(Map<String, Object> mapa) {
        int id = ((Double) mapa.get("id")).intValue();
        String nome = (String) mapa.get("nome");
        String idioma = (String) mapa.get("idioma");

        List<String> generos = new ArrayList<>();
        for (Object g : (List<Object>) mapa.get("generos")) {
            generos.add((String) g);
        }

        double nota = (Double) mapa.get("nota");
        String status = (String) mapa.get("status");
        String dataEstreia = (String) mapa.get("dataEstreia");
        String dataTermino = (String) mapa.get("dataTermino");
        String emissora = (String) mapa.get("emissora");

        return new Serie(id, nome, idioma, generos, nota, status, dataEstreia, dataTermino, emissora);
    }
}