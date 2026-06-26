package Prova_Final;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends JFrame {
    private DadosUsuario dados;
    private ArrayList<Serie> listaAtualExibida;
    
    private JTable tabelaSéries;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> seletorListas;
    private JComboBox<String> seletorOrdenacao;

    // Definição de Cores do Design
    Color corFundoAmestista = new Color(50, 20, 70); 
    Color corBotaoVioleta = new Color(90, 40, 130); 
    Color corTextoTitulo = new Color(240, 230, 250); 
    Color corRemoverVermeho = new Color(130, 40, 40);

    // Construtor agora exige o usuário logado
    public TelaPrincipal(DadosUsuario usuarioLogado) {
        this.dados = usuarioLogado;

        setTitle("Gerenciador de Séries - Usuário: " + dados.nomeUsuario);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Painel base para a cor de fundo
        JPanel painelBase = new JPanel(new BorderLayout());
        painelBase.setBackground(corFundoAmestista);
        setContentPane(painelBase);

        // Criando a Tabela e Estilizando
        String[] colunas = {"Nome", "Idioma", "Gêneros", "Nota Geral", "Estado", "Estreia", "Término", "Emissora"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaSéries = new JTable(modeloTabela);
        
        // Estilo da Tabela
        tabelaSéries.setBackground(corFundoAmestista);
        tabelaSéries.setForeground(Color.WHITE);
        tabelaSéries.setGridColor(corBotaoVioleta);
        
        // Estilo do Cabeçalho da Tabela
        tabelaSéries.getTableHeader().setBackground(corBotaoVioleta);
        tabelaSéries.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollTabela = new JScrollPane(tabelaSéries);
        scrollTabela.setOpaque(false);
        scrollTabela.getViewport().setOpaque(false); // Transparência para o fundo do scroll
        painelBase.add(scrollTabela, BorderLayout.CENTER);

        // Painel Superior (Filtros e Busca)
        JPanel painelSuperior = new JPanel();
        painelSuperior.setOpaque(false);
        JTextField txtBusca = new JTextField(15);
        txtBusca.setBackground(corFundoAmestista);
        txtBusca.setForeground(Color.WHITE);
        
        JButton btnBuscar = new JButton("Buscar Série");
        btnBuscar.setBackground(corBotaoVioleta);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        
        seletorListas = new JComboBox<>(new String[]{"Resultados da Busca", "Favoritos", "Séries Já Assistidas", "Séries que Deseja Assistir"});
        seletorOrdenacao = new JComboBox<>(new String[]{"Sem Ordenação", "Ordem Alfabética", "Nota Geral", "Estado da Série", "Data de Estreia"});

        painelSuperior.add(new JLabel("Nome:")); painelSuperior.add(txtBusca); painelSuperior.add(btnBuscar);
        painelSuperior.add(new JLabel(" | Ver:")); painelSuperior.add(seletorListas);
        painelSuperior.add(new JLabel(" | Ordenar por:")); painelSuperior.add(seletorOrdenacao);
        painelBase.add(painelSuperior, BorderLayout.NORTH);

        // Painel Inferior (Ações)
        JPanel painelInferior = new JPanel();
        painelInferior.setOpaque(false);
        JButton btnAddFav = new JButton("+ Favoritos");
        JButton btnAddAssistida = new JButton("+ Já Assistidas");
        JButton btnAddDesejo = new JButton("+ Deseja Assistir");
        JButton btnRemover = new JButton("Remover da Lista");
        
        // Estilizar os Botões de Ação
        JButton[] botoes = {btnAddFav, btnAddAssistida, btnAddDesejo, btnRemover};
        for (JButton btn : botoes) {
            btn.setBackground(corBotaoVioleta);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }
        btnRemover.setBackground(corRemoverVermeho); // Botão Remover diferente

        painelInferior.add(btnAddFav); painelInferior.add(btnAddAssistida); 
        painelInferior.add(btnAddDesejo); painelInferior.add(btnRemover);
        painelBase.add(painelInferior, BorderLayout.SOUTH);

        // ==================== EVENTOS ====================

        // Botão Buscar (Chama o serviço Web)
        btnBuscar.addActionListener(e -> {
            String termo = txtBusca.getText().trim();
            if (!termo.isEmpty()) {
                try {
                    listaAtualExibida = TvMazeService.buscarSeries(termo);
                    seletorListas.setSelectedIndex(0);
                    atualizarInterfaceTabela();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
                }
            }
        });

        // Alternar Listas
        seletorListas.addActionListener(e -> {
            int opcao = seletorListas.getSelectedIndex();
            if (opcao == 1) listaAtualExibida = dados.favoritos;
            else if (opcao == 2) listaAtualExibida = dados.jaAssistidas;
            else if (opcao == 3) listaAtualExibida = dados.desejaAssistir;
            atualizarInterfaceTabela();
        });

        // Ordenação
        seletorOrdenacao.addActionListener(e -> {
            if (listaAtualExibida == null || listaAtualExibida.isEmpty()) return;
            String criterio = (String) seletorOrdenacao.getSelectedItem();

            if (criterio.equals("Ordem Alfabética")) {
                listaAtualExibida.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
            } else if (criterio.equals("Nota Geral")) {
                listaAtualExibida.sort((s1, s2) -> Double.compare(s2.getNota(), s1.getNota()));
            } else if (criterio.equals("Estado da Série")) {
                listaAtualExibida.sort(Comparator.comparing(Serie::getEstado, String.CASE_INSENSITIVE_ORDER));
            } else if (criterio.equals("Data de Estreia")) {
                listaAtualExibida.sort(Comparator.comparing(Serie::getDataEstreia));
            }
            atualizarInterfaceTabela();
        });

        // Botões de Adicionar
        btnAddFav.addActionListener(e -> salvarNaLista(dados.favoritos, "Favoritos"));
        btnAddAssistida.addActionListener(e -> salvarNaLista(dados.jaAssistidas, "Séries Já Assistidas"));
        btnAddDesejo.addActionListener(e -> salvarNaLista(dados.desejaAssistir, "Séries que Deseja Assistir"));

        // Botão Remover
        btnRemover.addActionListener(e -> {
            int linha = tabelaSéries.getSelectedRow();
            if (linha >= 0 && seletorListas.getSelectedIndex() != 0) {
                Serie removida = listaAtualExibida.remove(linha);
                ArquivoService.atualizarUsuario(dados); // Salva as alterações para o usuário logado
                atualizarInterfaceTabela();
                JOptionPane.showMessageDialog(this, removida.getNome() + " removida.");
            }
        });

        seletorListas.setSelectedIndex(1); // Exibe favoritos por padrão
    }

    private void salvarNaLista(ArrayList<Serie> listaAlvo, String nomeLista) {
        int linha = tabelaSéries.getSelectedRow();
        if (linha >= 0 && listaAtualExibida != null) {
            Serie selecionada = listaAtualExibida.get(linha);
            if (!listaAlvo.contains(selecionada)) {
                listaAlvo.add(selecionada);
                ArquivoService.atualizarUsuario(dados); // Salva as alterações para o usuário logado
                JOptionPane.showMessageDialog(this, selecionada.getNome() + " adicionada a " + nomeLista);
            }
        }
    }

    private void atualizarInterfaceTabela() {
        modeloTabela.setRowCount(0);
        if (listaAtualExibida != null) {
            for (Serie s : listaAtualExibida) {
                modeloTabela.addRow(s.paraLinhaTabela());
            }
        }
    }
}