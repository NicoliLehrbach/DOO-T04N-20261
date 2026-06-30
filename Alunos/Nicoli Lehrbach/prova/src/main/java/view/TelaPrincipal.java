package view;

import controller.UsuarioController;
import model.Serie;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private final UsuarioController usuarioController;

    // Paleta de Cores Moderna e Simplificada (Estilo Clean/Flat)
    protected static final Color COR_FUNDO           = new Color(248, 250, 252); // Off-white suave
    protected static final Color COR_CARD            = Color.WHITE;
    protected static final Color COR_TEXTO_MAIN       = new Color(15, 23, 42);    // Slate escuro
    protected static final Color COR_TEXTO_MUTED      = new Color(100, 116, 139); // Slate médio
    protected static final Color COR_TEXTO_CLARO      = Color.WHITE;
    protected static final Color COR_BORDA            = new Color(226, 232, 240); // Linha suave
    protected static final Color COR_DESTAQUE         = new Color(37, 99, 235);   // Azul premium
    protected static final Color COR_DESTAQUE_HOVER   = new Color(29, 78, 216);
    protected static final Color COR_SUAVE            = new Color(241, 245, 249); // Fundo cinza para campos

    public static final int LARGURA_VIEW = 1280;
    public static final int ALTURA_VIEW = 720;
    public static final Dimension TAMANHO_PADRAO_VIEW = new Dimension(LARGURA_VIEW, ALTURA_VIEW);

    public TelaPrincipal(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        configurarJanela();
        construirInterfaceGrafica();
    }

    private void configurarJanela() {
        setSize(TAMANHO_PADRAO_VIEW);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                exibirConfirmacaoSaida();
            }
        });
    }

    private void construirInterfaceGrafica() {
        JPanel painelPrincipal = criarPainelBase();
        painelPrincipal.setLayout(new BorderLayout());

        JPanel card = criarCardPrincipal();
        card.setLayout(new BorderLayout(0, 24));
        card.add(criarCabecalhoSimples(), BorderLayout.NORTH);
        card.add(criarResumoSimples(), BorderLayout.CENTER);
        card.add(criarPainelBotoes(), BorderLayout.SOUTH);

        painelPrincipal.add(card, BorderLayout.CENTER);
        add(painelPrincipal);
    }

    private JPanel criarCabecalhoSimples() {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Controle de Séries");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(COR_TEXTO_MAIN);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel saudacao = new JLabel("Olá, " + valorOuPadrao(usuarioController.getUsuario().getNome()) + ". Bem-vindo ao seu catálogo personalizado.");
        saudacao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        saudacao.setForeground(COR_TEXTO_MUTED);
        saudacao.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(4));
        painel.add(saudacao);
        return painel;
    }

    private JPanel criarResumoSimples() {
        JPanel painel = new JPanel();
        painel.setBackground(COR_SUAVE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        painel.setLayout(new GridLayout(1, 3, 20, 0));

        painel.add(criarLinhaResumo("Favoritos", usuarioController.getUsuario().getFavoritos().size()));
        painel.add(criarLinhaResumo("Assistidas", usuarioController.getUsuario().getAssistidos().size()));
        painel.add(criarLinhaResumo("Quero Assistir", usuarioController.getUsuario().getQueroAssistir().size()));
        return painel;
    }

    private JPanel criarLinhaResumo(String titulo, int valor) {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitulo.setForeground(COR_TEXTO_MUTED);

        JLabel lblValor = new JLabel(String.valueOf(valor));
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValor.setForeground(COR_DESTAQUE);

        linha.add(lblTitulo, BorderLayout.NORTH);
        linha.add(lblValor, BorderLayout.SOUTH);
        return linha;
    }

    private JPanel criarPainelBotoes() {
        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 16, 0));
        painelBotoes.setOpaque(false);

        JButton btnBusca = criarBotaoEstilizado("Buscar Novas Séries", COR_DESTAQUE);
        JButton btnListas = criarBotaoEstilizado("Minhas Listas", new Color(71, 85, 105)); // Slate Gray
        JButton btnSair = criarBotaoEstilizado("Sair do Sistema", new Color(239, 68, 68));   // Vermelho limpo

        btnBusca.addActionListener(e -> irParaTelaBusca());
        btnListas.addActionListener(e -> irParaTelaListas());
        btnSair.addActionListener(e -> exibirConfirmacaoSaida());

        painelBotoes.add(btnBusca);
        painelBotoes.add(btnListas);
        painelBotoes.add(btnSair);
        return painelBotoes;
    }

    private void irParaTelaBusca() {
        TelaBusca telaBusca = new TelaBusca(usuarioController);
        telaBusca.setVisible(true);
        this.dispose();
    }

    private void irParaTelaListas() {
        TelaListas telaListas = new TelaListas(usuarioController);
        telaListas.setVisible(true);
        this.dispose();
    }

    private void exibirConfirmacaoSaida() {
        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja fechar o aplicativo?",
                "Confirmação de Saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                usuarioController.salvarDados();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    public static JPanel criarPainelBase() {
        JPanel painel = new JPanel();
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return painel;
    }

    public static JPanel criarCardPrincipal() {
        JPanel card = new JPanel();
        card.setBackground(COR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));
        return card;
    }

    public static JButton criarBotaoEstilizado(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setForeground(COR_TEXTO_CLARO);
        botao.setBackground(corFundo);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    public static JButton criarBotaoClaro(String texto, Color corTexto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setForeground(corTexto);
        botao.setBackground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    public static JPanel criarPainelArredondado(Color corFundo, int r, Insets ins) {
        JPanel painel = new JPanel();
        painel.setBackground(corFundo);
        painel.setBorder(BorderFactory.createEmptyBorder(ins.top, ins.left, ins.bottom, ins.right));
        return painel;
    }

    private String valorOuPadrao(String valor) {
        return (valor == null || valor.isBlank()) ? "Usuário" : valor;
    }
}