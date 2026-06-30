import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TvMazeAPI {

    public static List<Serie> buscarSeries(String nome) {

        List<Serie> lista = new ArrayList<>();

        try {

            String url = "https://api.tvmaze.com/search/shows?q="
                    + nome.replace(" ", "%20");

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request,
                            HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response.body());

            for (JsonNode item : root) {

                JsonNode show = item.get("show");

                String nomeSerie = show.get("name").asText();

                String idioma = show.get("language").asText();

                String status = show.get("status").asText();

                String estreia = show.get("premiered").isNull()
                        ? "Não informado"
                        : show.get("premiered").asText();

                String termino = show.get("ended").isNull()
                        ? "Em andamento"
                        : show.get("ended").asText();

                double nota = 0;

                if (show.get("rating") != null
                        && show.get("rating").get("average") != null
                        && !show.get("rating").get("average").isNull()) {

                    nota = show.get("rating")
                            .get("average")
                            .asDouble();

                }

                String emissora = "Não informada";

                if (show.get("network") != null
                        && show.get("network").get("name") != null) {

                    emissora = show.get("network")
                            .get("name")
                            .asText();

                }

                String generos = "";

                if (show.get("genres") != null) {

                    for (JsonNode g : show.get("genres")) {

                        if (!generos.isEmpty()) {
                            generos += ", ";
                        }

                        generos += g.asText();

                    }

                }

                String resumo = "";

                if (show.get("summary") != null
                        && !show.get("summary").isNull()) {

                    resumo = show.get("summary")
                            .asText()
                            .replaceAll("<[^>]*>", "");

                }

                Serie serie = new Serie(
                        nomeSerie,
                        idioma,
                        generos,
                        nota,
                        status,
                        estreia,
                        termino,
                        emissora,
                        resumo
                );

                lista.add(serie);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return lista;

    }

}