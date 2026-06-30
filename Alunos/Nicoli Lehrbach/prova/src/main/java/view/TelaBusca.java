package view;

import controller.TvMazeController;
import controller.UsuarioController;
import model.Serie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaBusca extends JFrame {
    private final UsuarioController usuarioController;
    private final TvMazeController tvMazeController;

    private JTextField campoEntradaBusca;
    private JList<Serie> listaResultados;
    private DefaultListModel<Serie> modeloListaResultados;
    private JLabel labelStatusBusca;

    public TelaBusca(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        this.tvMazeController = new TvMazeController();
        configurarJanela();
        construirInterfaceGrafica();
    }

    private void configurarJanela() {
        setSize(TelaPrincipal.TAMANHO_PADRAO_VIEW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirInterfaceGrafica() {
        JPanel painelPrincipal = TelaPrincipal.criarPainelBase();
        painelPrincipal.setLayout(new BorderLayout());

        JPanel cardPrincipal = TelaPrincipal.criarCardPrincipal();
        cardPrincipal.setLayout(new BorderLayout(0, 20));

        cardPrincipal.add(criarCabecalhoBusca(), BorderLayout.NORTH);
        cardPrincipal.add(criarCorpoBusca(), BorderLayout.CENTER);

        painelPrincipal.add(cardPrincipal, BorderLayout.CENTER);
        add(painelPrincipal);
    }

    private JPanel criarCabecalhoBusca() {
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setOpaque(false);

        JPanel painelTexto = new JPanel();
        painelTexto.setOpaque(false);
        painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Buscar Séries");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(TelaPrincipal.COR_TEXTO_MAIN);

        JLabel instrucao = new JLabel("Digite o nome da série para pesquisar via API externa.");
        instrucao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instrucao.setForeground(TelaPrincipal.COR_TEXTO_MUTED);

        painelTexto.add(titulo);
        painelTexto.add(Box.createVerticalStrut(4));
        painelTexto.add(instrucao);

        JButton botaoVoltar = TelaPrincipal.criarBotaoClaro("Voltar ao Menu", TelaPrincipal.COR_TEXTO_MAIN);
        botaoVoltar.addActionListener(e -> voltarAoMenuPrincipal());

        painelCabecalho.add(painelTexto, BorderLayout.CENTER);
        painelCabecalho.add(botaoVoltar, BorderLayout.EAST);
        return painelCabecalho;
    }

    private JPanel criarCorpoBusca() {
        JPanel painel = new JPanel(new BorderLayout(0, 16));
        painel.setOpaque(false);

        JPanel areaResultados = new JPanel(new BorderLayout(0, 14));
        areaResultados.setOpaque(false);
        areaResultados.add(criarBarraPesquisa(), BorderLayout.NORTH);
        areaResultados.add(criarListaResultados(), BorderLayout.CENTER);

        painel.add(areaResultados, BorderLayout.CENTER);
        painel.add(criarPainelAcoes(), BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarBarraPesquisa() {
        JPanel barra = new JPanel(new BorderLayout(12, 0));
        barra.setOpaque(false);

        campoEntradaBusca = new JTextField();
        campoEntradaBusca.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campoEntradaBusca.setForeground(TelaPrincipal.COR_TEXTO_MAIN);
        campoEntradaBusca.setBackground(TelaPrincipal.COR_SUAVE);
        campoEntradaBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TelaPrincipal.COR_BORDA, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        campoEntradaBusca.addActionListener(e -> executarBuscaApi());

        JButton botaoPesquisar = TelaPrincipal.criarBotaoEstilizado("Pesquisar", TelaPrincipal.COR_DESTAQUE);
        botaoPesquisar.addActionListener(e -> executarBuscaApi());

        barra.add(campoEntradaBusca, BorderLayout.CENTER);
        barra.add(botaoPesquisar, BorderLayout.EAST);
        return barra;
    }

    private JPanel criarListaResultados() {
        JPanel painelLista = new JPanel(new BorderLayout(0, 8));
        painelLista.setOpaque(false);

        modeloListaResultados = new DefaultListModel<>();
        listaResultados = new JList<>(modeloListaResultados);
        listaResultados.setCellRenderer(new RenderizadorResultadoBusca());
        listaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaResultados.setFixedCellHeight(70);

        JScrollPane scroll = new JScrollPane(listaResultados);
        scroll.setBorder(BorderFactory.createLineBorder(TelaPrincipal.COR_BORDA));
        scroll.getViewport().setBackground(Color.WHITE);

        painelLista.add(scroll, BorderLayout.CENTER);
        return painelLista;
    }

    private JPanel criarPainelAcoes() {
        JPanel painelAcoes = new JPanel(new BorderLayout(12, 0));
        painelAcoes.setOpaque(false);

        labelStatusBusca = new JLabel("Pronto para pesquisar.");
        labelStatusBusca.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelStatusBusca.setForeground(TelaPrincipal.COR_TEXTO_MUTED);

        JPanel botoesDireita = new JPanel(new GridLayout(1, 4, 10, 0));
        botoesDireita.setOpaque(false);

        JButton btnDetalhes = TelaPrincipal.criarBotaoEstilizado("Ver Detalhes", TelaPrincipal.COR_DESTAQUE);
        JButton btnFav = TelaPrincipal.criarBotaoClaro("Favoritar", TelaPrincipal.COR_DESTAQUE);
        JButton btnAssis = TelaPrincipal.criarBotaoClaro("Assistida", TelaPrincipal.COR_TEXTO_MAIN);
        JButton btnQuero = TelaPrincipal.criarBotaoClaro("Ver Depois", TelaPrincipal.COR_TEXTO_MUTED);

        btnDetalhes.addActionListener(e -> abrirDetalhesSerieSelecionada());
        btnFav.addActionListener(e -> adicionarSerieAALista("favoritos"));
        btnAssis.addActionListener(e -> adicionarSerieAALista("assistidos"));
        btnQuero.addActionListener(e -> adicionarSerieAALista("queroAssistir"));

        botoesDireita.add(btnDetalhes);
        botoesDireita.add(btnFav);
        botoesDireita.add(btnAssis);
        botoesDireita.add(btnQuero);

        painelAcoes.add(labelStatusBusca, BorderLayout.CENTER);
        painelAcoes.add(botoesDireita, BorderLayout.EAST);
        return painelAcoes;
    }

    private void executarBuscaApi() {
        String query = campoEntradaBusca.getText().trim();
        if (query.isEmpty()) {
            labelStatusBusca.setText("Aviso: Digite algo para buscar.");
            return;
        }

        labelStatusBusca.setText("Buscando séries...");
        modeloListaResultados.clear();

        SwingWorker<List<Serie>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Serie> doInBackground() {
                return tvMazeController.consultarSeriesPorNome(query);
            }

            @Override
            protected void done() {
                try {
                    List<Serie> resultado = get();
                    if (resultado.isEmpty()) {
                        labelStatusBusca.setText("Nenhuma série encontrada para '" + query + "'.");
                    } else {
                        for (Serie s : resultado) {
                            modeloListaResultados.addElement(s);
                        }
                        labelStatusBusca.setText("Busca concluída. " + resultado.size() + " itens encontrados.");
                    }
                } catch (Exception ex) {
                    labelStatusBusca.setText("Erro ao conectar com o serviço.");
                }
            }
        };
        worker.execute();
    }

    private void abrirDetalhesSerieSelecionada() {
        Serie selecionada = listaResultados.getSelectedValue();
        if (selecionada == null) {
            labelStatusBusca.setText("Selecione uma série na lista para ver detalhes.");
            return;
        }
        DetalhesSerieDialog.exibir(this, selecionada);
    }

    private void adicionarSerieAALista(String tipoLista) {
        Serie selecionada = listaResultados.getSelectedValue();
        if (selecionada == null) {
            labelStatusBusca.setText("Selecione um item primeiro.");
            return;
        }

        switch (tipoLista) {
            case "favoritos":
                usuarioController.favoritarSerie(selecionada);
                labelStatusBusca.setText("Adicionada aos Favoritos!");
                break;
            case "assistidos":
                usuarioController.marcarComoAssistida(selecionada);
                labelStatusBusca.setText("Marcada como Assistida!");
                break;
            case "queroAssistir":
                usuarioController.adicionarListaQueroAssistir(selecionada);
                labelStatusBusca.setText("Salva para Assistir depois!");
                break;
        }
    }

    private void voltarAoMenuPrincipal() {
        TelaPrincipal principal = new TelaPrincipal(usuarioController);
        principal.setVisible(true);
        this.dispose();
    }

    private static class RenderizadorResultadoBusca extends JPanel implements ListCellRenderer<Serie> {
        private final JLabel titulo = new JLabel();
        private final JLabel meta = new JLabel();
        private final JLabel nota = new JLabel();

        public RenderizadorResultadoBusca() {
            setLayout(new BorderLayout(12, 0));
            setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

            JPanel textos = new JPanel();
            textos.setOpaque(false);
            textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

            titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
            meta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            nota.setFont(new Font("Segoe UI", Font.BOLD, 14));

            textos.add(titulo);
            textos.add(Box.createVerticalStrut(4));
            textos.add(meta);

            add(textos, BorderLayout.CENTER);
            add(nota, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Serie> list, Serie serie, int index, boolean isSelected, boolean cellHasFocus) {
            if (serie != null) {
                titulo.setText(serie.getNome());
                String generos = (serie.getGeneros() == null || serie.getGeneros().isBlank()) ? "Gênero N/A" : serie.getGeneros();
                String ano = (serie.getDataEstreia() == null || serie.getDataEstreia().length() < 4) ? "N/A" : serie.getDataEstreia().substring(0, 4);
                meta.setText(generos + "  •  " + ano + "  •  " + serie.getStatus());
                nota.setText(String.format("%.1f ★", serie.getNota()));
            }

            if (isSelected) {
                setBackground(TelaPrincipal.COR_SUAVE);
                titulo.setForeground(TelaPrincipal.COR_DESTAQUE);
                meta.setForeground(TelaPrincipal.COR_TEXTO_MAIN);
                nota.setForeground(TelaPrincipal.COR_DESTAQUE);
            } else {
                setBackground(Color.WHITE);
                titulo.setForeground(TelaPrincipal.COR_TEXTO_MAIN);
                meta.setForeground(TelaPrincipal.COR_TEXTO_MUTED);
                nota.setForeground(TelaPrincipal.COR_TEXTO_MUTED);
            }
            return this;
        }
    }
}