package Prova_Final;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Prova_Final.Serie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class TvMazeService {

    public static ArrayList<Serie> buscarSeries(String nomeSerie) throws Exception {
        ArrayList<Serie> resultados = new ArrayList<>();
        String urlFormatada = "https://api.tvmaze.com/search/shows?q=" + nomeSerie.replace(" ", "%20");
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(urlFormatada)).GET().build();
        HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapeador = new ObjectMapper();
        JsonNode nós = mapeador.readTree(resposta.body());

        for (JsonNode no : nós) {
            JsonNode show = no.path("show");

            String nome = show.path("name").asText("Desconhecido");
            String idioma = show.path("language").asText("Não informado");
            double nota = show.path("rating").path("average").asDouble(0.0);
            String estreia = show.path("premiered").asText("-");
            String termino = show.path("ended").asText("-");
            String emissora = show.path("network").path("name").asText("Streaming / Outros");

            // Tradução do Estado do Show
            String statusOriginal = show.path("status").asText("");
            String estadoStr = "Desconhecido";
            if (statusOriginal.equalsIgnoreCase("Running")) estadoStr = "Transmitindo";
            else if (statusOriginal.equalsIgnoreCase("Ended")) estadoStr = "Concluída";
            else if (statusOriginal.equalsIgnoreCase("Canceled")) estadoStr = "Cancelada";

            // Formatação dos gêneros
            String generosStr = show.path("genres").toString().replace("[", "").replace("]", "").replace("\"", "").replace(",", ", ");
            if (generosStr.trim().isEmpty()) generosStr = "Não Definido";

            resultados.add(new Serie(nome, idioma, generosStr, nota, estadoStr, estreia, termino, emissora));
        }
        return resultados;
    }
}