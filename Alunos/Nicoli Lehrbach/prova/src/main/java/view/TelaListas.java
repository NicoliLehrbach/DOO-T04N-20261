package view;

import controller.UsuarioController;
import model.Serie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListas extends JFrame {
    private final UsuarioController usuarioController;

    private JTabbedPane abasCategorias;
    private JList<Serie> listaFavoritos;
    private JList<Serie> listaAssistidas;
    private JList<Serie> listaQueroAssistir;
    private DefaultListModel<Serie> modeloFav;
    private DefaultListModel<Serie> modeloAssis;
    private DefaultListModel<Serie> modeloQuero;
    private JLabel labelStatus;

    public TelaListas(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        configurarJanela();
        construirInterfaceGrafica();
        sincronizarListasInterface();
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
        cardPrincipal.setLayout(new BorderLayout(0, 16));

        cardPrincipal.add(criarCabecalho(), BorderLayout.NORTH);
        cardPrincipal.add(criarConteudoListas(), BorderLayout.CENTER);
        cardPrincipal.add(criarRodapeAcoes(), BorderLayout.SOUTH);

        painelPrincipal.add(cardPrincipal, BorderLayout.CENTER);
        add(painelPrincipal);
    }

    private JPanel criarCabecalho() {
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setOpaque(false);

        JPanel painelTitulo = new JPanel();
        painelTitulo.setOpaque(false);
        painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Minhas Listas Personalizadas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(TelaPrincipal.COR_TEXTO_MAIN);

        JLabel instrucao = new JLabel("Gerencie, ordene e explore suas séries arquivadas.");
        instrucao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instrucao.setForeground(TelaPrincipal.COR_TEXTO_MUTED);

        painelTitulo.add(titulo);
        painelTitulo.add(Box.createVerticalStrut(4));
        painelTitulo.add(instrucao);

        JButton voltar = TelaPrincipal.criarBotaoClaro("Voltar ao Menu", TelaPrincipal.COR_TEXTO_MAIN);
        voltar.addActionListener(e -> voltarAoMenuPrincipal());

        painelCabecalho.add(painelTitulo, BorderLayout.CENTER);
        painelCabecalho.add(voltar, BorderLayout.EAST);
        return painelCabecalho;
    }

    private JPanel criarConteudoListas() {
        JPanel conteudo = new JPanel(new BorderLayout(0, 16));
        conteudo.setOpaque(false);

        conteudo.add(criarAbasListas(), BorderLayout.CENTER);
        conteudo.add(criarPainelAcoesHorizontal(), BorderLayout.SOUTH);
        return conteudo;
    }

    private JPanel criarPainelAcoesHorizontal() {
        JPanel painel = new JPanel(new BorderLayout(16, 0));
        painel.setBackground(TelaPrincipal.COR_SUAVE);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JPanel painelOrdenacao = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painelOrdenacao.setOpaque(false);

        JLabel lblOrdenar = new JLabel("Ordenar:");
        lblOrdenar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblOrdenar.setForeground(TelaPrincipal.COR_TEXTO_MAIN);
        painelOrdenacao.add(lblOrdenar);

        JButton btnNome = TelaPrincipal.criarBotaoClaro("Nome A-Z", TelaPrincipal.COR_TEXTO_MAIN);
        JButton btnNota = TelaPrincipal.criarBotaoClaro("Nota", TelaPrincipal.COR_TEXTO_MAIN);
        JButton btnStatus = TelaPrincipal.criarBotaoClaro("Status", TelaPrincipal.COR_TEXTO_MAIN);
        JButton btnData = TelaPrincipal.criarBotaoClaro("Estreia", TelaPrincipal.COR_TEXTO_MAIN);

        btnNome.addActionListener(e -> dispararOrdenacao("nome"));
        btnNota.addActionListener(e -> dispararOrdenacao("nota"));
        btnStatus.addActionListener(e -> dispararOrdenacao("status"));
        btnData.addActionListener(e -> dispararOrdenacao("data"));

        painelOrdenacao.add(btnNome);
        painelOrdenacao.add(btnNota);
        painelOrdenacao.add(btnStatus);
        painelOrdenacao.add(btnData);

        JPanel painelGerenciar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelGerenciar.setOpaque(false);

        JButton botaoVerDetalhes = TelaPrincipal.criarBotaoEstilizado("Ver Detalhes", TelaPrincipal.COR_DESTAQUE);
        JButton botaoRemover = TelaPrincipal.criarBotaoEstilizado("Remover Item", new Color(239, 68, 68));

        botaoVerDetalhes.addActionListener(e -> exibirDetalhesDoItemSelecionado());
        botaoRemover.addActionListener(e -> removerItemSelecionadoDaListaAtual());

        painelGerenciar.add(botaoVerDetalhes);
        painelGerenciar.add(botaoRemover);

        painel.add(painelOrdenacao, BorderLayout.WEST);
        painel.add(painelGerenciar, BorderLayout.EAST);
        return painel;
    }

    private JTabbedPane criarAbasListas() {
        abasCategorias = new JTabbedPane();
        abasCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        modeloFav = new DefaultListModel<>();
        listaFavoritos = new JList<>(modeloFav);
        configurarEstiloLista(listaFavoritos);

        modeloAssis = new DefaultListModel<>();
        listaAssistidas = new JList<>(modeloAssis);
        configurarEstiloLista(listaAssistidas);

        modeloQuero = new DefaultListModel<>();
        listaQueroAssistir = new JList<>(modeloQuero);
        configurarEstiloLista(listaQueroAssistir);

        abasCategorias.addTab("Favoritos", criarScrollLista(listaFavoritos));
        abasCategorias.addTab("Assistidas", criarScrollLista(listaAssistidas));
        abasCategorias.addTab("Quero Assistir", criarScrollLista(listaQueroAssistir));

        return abasCategorias;
    }

    private void configurarEstiloLista(JList<Serie> lista) {
        lista.setCellRenderer(new RenderizadorListaItens());
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFixedCellHeight(64);
    }

    private JScrollPane criarScrollLista(JList<Serie> lista) {
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(TelaPrincipal.COR_BORDA));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private JPanel criarRodapeAcoes() {
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setOpaque(false);

        labelStatus = new JLabel("Clique em uma série para gerenciar.");
        labelStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelStatus.setForeground(TelaPrincipal.COR_TEXTO_MUTED);
        painelRodape.add(labelStatus, BorderLayout.CENTER);
        return painelRodape;
    }

    private void sincronizarListasInterface() {
        modeloFav.clear();
        for (Serie s : usuarioController.getUsuario().getFavoritos()) modeloFav.addElement(s);

        modeloAssis.clear();
        for (Serie s : usuarioController.getUsuario().getAssistidos()) modeloAssis.addElement(s);

        modeloQuero.clear();
        for (Serie s : usuarioController.getUsuario().getQueroAssistir()) modeloQuero.addElement(s);
    }

    private void dispararOrdenacao(String criterio) {
        int indexAba = abasCategorias.getSelectedIndex();
        List<Serie> listaAlvo = null;

        if (indexAba == 0) listaAlvo = usuarioController.getUsuario().getFavoritos();
        else if (indexAba == 1) listaAlvo = usuarioController.getUsuario().getAssistidos();
        else if (indexAba == 2) listaAlvo = usuarioController.getUsuario().getQueroAssistir();

        if (listaAlvo == null || listaAlvo.isEmpty()) {
            labelStatus.setText("Aviso: A lista atual está vazia.");
            return;
        }

        switch (criterio) {
            case "nome": usuarioController.ordenarListaPorNome(listaAlvo); break;
            case "nota": usuarioController.ordenarListaPorNota(listaAlvo); break;
            case "status": usuarioController.ordenarListaPorStatus(listaAlvo); break;
            case "data": usuarioController.ordenarListaPorDataEstreia(listaAlvo); break;
        }

        sincronizarListasInterface();
        labelStatus.setText("Lista ordenada com sucesso por: " + criterio.toUpperCase() + ".");
    }

    private void exibirDetalhesDoItemSelecionado() {
        Serie selecionada = obterSerieSelecionadaAbaAtual();
        if (selecionada == null) {
            labelStatus.setText("Por favor, selecione uma série na lista.");
            return;
        }
        DetalhesSerieDialog.exibir(this, selecionada);
    }

    private void removerItemSelecionadoDaListaAtual() {
        int aba = abasCategorias.getSelectedIndex();
        Serie selecionada = obterSerieSelecionadaAbaAtual();

        if (selecionada == null) {
            labelStatus.setText("Selecione um item para remover.");
            return;
        }

        if (aba == 0) usuarioController.desfavoritarSerie(selecionada);
        else if (aba == 1) usuarioController.removerDeAssistidas(selecionada);
        else if (aba == 2) usuarioController.removerListaQueroAssistir(selecionada);

        sincronizarListasInterface();
        labelStatus.setText("Série '" + selecionada.getNome() + "' removida.");
    }

    private Serie obterSerieSelecionadaAbaAtual() {
        int aba = abasCategorias.getSelectedIndex();
        if (aba == 0) return listaFavoritos.getSelectedValue();
        if (aba == 1) return listaAssistidas.getSelectedValue();
        if (aba == 2) return listaQueroAssistir.getSelectedValue();
        return null;
    }

    private void voltarAoMenuPrincipal() {
        TelaPrincipal p = new TelaPrincipal(usuarioController);
        p.setVisible(true);
        this.dispose();
    }

    private static class RenderizadorListaItens extends JPanel implements ListCellRenderer<Serie> {
        private final JLabel titulo = new JLabel();
        private final JLabel meta = new JLabel();
        private final JLabel indicador = new JLabel();

        public RenderizadorListaItens() {
            setLayout(new BorderLayout(12, 0));
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

            JPanel textos = new JPanel();
            textos.setOpaque(false);
            textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

            titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
            meta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            indicador.setFont(new Font("Segoe UI", Font.BOLD, 13));

            textos.add(titulo);
            textos.add(Box.createVerticalStrut(3));
            textos.add(meta);

            add(textos, BorderLayout.CENTER);
            add(indicador, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Serie> list, Serie serie, int index, boolean isSelected, boolean cellHasFocus) {
            if (serie != null) {
                titulo.setText(serie.getNome());
                String generos = (serie.getGeneros() == null || serie.getGeneros().isBlank()) ? "Não Informado" : serie.getGeneros();
                meta.setText(generos + "  •  " + serie.getStatus());
                indicador.setText(String.format("%.1f ★", serie.getNota()));
            }

            if (isSelected) {
                setBackground(TelaPrincipal.COR_SUAVE);
                titulo.setForeground(TelaPrincipal.COR_DESTAQUE);
                meta.setForeground(TelaPrincipal.COR_TEXTO_MAIN);
                indicador.setForeground(TelaPrincipal.COR_DESTAQUE);
            } else {
                setBackground(Color.WHITE);
                titulo.setForeground(TelaPrincipal.COR_TEXTO_MAIN);
                meta.setForeground(TelaPrincipal.COR_TEXTO_MUTED);
                indicador.setForeground(TelaPrincipal.COR_TEXTO_MUTED);
            }
            return this;
        }
    }
}