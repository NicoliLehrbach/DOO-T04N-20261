import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Persistencia {

    private static final String ARQUIVO = "usuario.json";

    public static void salvar(Usuario usuario) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            mapper.writeValue(new File(ARQUIVO), usuario);

        } catch (IOException e) {

            System.out.println("Erro ao salvar os dados.");

        }

    }

    public static Usuario carregar() {

        try {

            File arquivo = new File(ARQUIVO);

            if (!arquivo.exists()) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(arquivo, Usuario.class);

        } catch (IOException e) {

            System.out.println("Erro ao carregar os dados.");

            return null;

        }

    }

    public static boolean existeUsuario() {

        File arquivo = new File(ARQUIVO);

        return arquivo.exists();

    }

    public static void removerUsuario() {

        File arquivo = new File(ARQUIVO);

        if (arquivo.exists()) {
            arquivo.delete();
        }

    }

}