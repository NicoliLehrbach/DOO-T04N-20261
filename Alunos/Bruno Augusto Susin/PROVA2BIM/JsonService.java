import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;


/*
 * Responsável por salvar e carregar os dados do usuário em JSON.
 */
public class JsonService {


    // Converte objeto Java em JSON e JSON em objeto Java.
    private final ObjectMapper mapper =
            new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    // Cria a pasta dados no local do projeto.
    private final File pastaDados = new File(System.getProperty("user.dir"), "dados");


    // Arquivo onde o usuário será salvo.
    private final File arquivo = new File(pastaDados, "usuario.json");



    /*
     * Carrega o usuário ao abrir o programa.
     */
    public Usuario carregarUsuario() {

        try {

            // Cria a pasta caso não exista.
            if (!pastaDados.exists()) {
                pastaDados.mkdirs();
            }


            // Primeira execução.
            if (!arquivo.exists()) {

                Usuario usuario = criarUsuarioPadrao();

                salvarUsuario(usuario);

                return usuario;
            }


            // Lê o JSON salvo.
            return mapper.readValue(arquivo, Usuario.class);


        } catch (Exception e) {

            e.printStackTrace();

            return criarUsuarioPadrao();
        }
    }




    /*
     * Salva o usuário no JSON.
     */
    public void salvarUsuario(Usuario usuario) throws Exception {


        if (!pastaDados.exists()) {
            pastaDados.mkdirs();
        }


        mapper.writeValue(arquivo, usuario);


        System.out.println("Salvo em: " + arquivo.getAbsolutePath());
    }




    /*
     * Usuário inicial.
     */
    private Usuario criarUsuarioPadrao() {

        return new Usuario("Aluno");

    }

}