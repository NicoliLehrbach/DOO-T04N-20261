package model;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersistenciaDados {
    private static final String CAMINHO_ARQUIVO = "dados.json";
    private final ObjectMapper objectMapper;

    public PersistenciaDados(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void salvarDadosUsuario(Usuario usuario) throws IOException {
        if (usuario == null) {
            return;
        }

        usuario.garantirListasInicializadas();

        File arquivo = new File(CAMINHO_ARQUIVO);
        File pastaPai = arquivo.getAbsoluteFile().getParentFile();
        if (pastaPai != null && !pastaPai.exists()) {
            pastaPai.mkdirs();
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, usuario);
    }

    public Usuario carregarDadosUsuario() throws IOException {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return null;
        }

        Usuario usuario = objectMapper.readValue(arquivo, Usuario.class);
        usuario.garantirListasInicializadas();
        return usuario;
    }
}
