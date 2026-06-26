package Prova_Final;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;

public class ArquivoService {
    private static final String ARQUIVO_NOME = "usuarios.json";
    private static final ObjectMapper mapeador = new ObjectMapper();

    // Carrega todos os usuários salvos no sistema
    public static ArrayList<DadosUsuario> carregarTodos() {
        try {
            File arquivo = new File(ARQUIVO_NOME);
            if (arquivo.exists()) {
                return mapeador.readValue(arquivo, new TypeReference<ArrayList<DadosUsuario>>() {});
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler JSON: " + e.getMessage());
        }
        return new ArrayList<>(); // Retorna lista vazia se não existir
    }

    // Salva a lista completa de usuários
    public static void salvarTodos(ArrayList<DadosUsuario> usuarios) {
        try {
            mapeador.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_NOME), usuarios);
        } catch (Exception e) {
            System.out.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }

    // Busca um usuário ou cria um novo se ele não existir
    public static DadosUsuario obterOuCriarUsuario(String nome) {
        ArrayList<DadosUsuario> usuarios = carregarTodos();
        for (DadosUsuario u : usuarios) {
            if (u.nomeUsuario.equalsIgnoreCase(nome)) {
                return u; // Encontrou, retorna ele
            }
        }
        
        // Se não encontrou, cria um novo do zero
        DadosUsuario novo = new DadosUsuario();
        novo.nomeUsuario = nome;
        usuarios.add(novo);
        salvarTodos(usuarios);
        return novo;
    }
    
    // Atualiza os dados de um usuário específico (quando ele adiciona uma série, por exemplo)
    public static void atualizarUsuario(DadosUsuario atualizado) {
        ArrayList<DadosUsuario> usuarios = carregarTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).nomeUsuario.equalsIgnoreCase(atualizado.nomeUsuario)) {
                usuarios.set(i, atualizado);
                break;
            }
        }
        salvarTodos(usuarios);
    }
}