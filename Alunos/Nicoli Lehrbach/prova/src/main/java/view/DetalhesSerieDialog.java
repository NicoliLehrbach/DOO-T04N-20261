package view;

import model.Serie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class DetalhesSerieDialog extends JDialog {
    private final Serie serie;
    private final JLabel labelImagem;

    private DetalhesSerieDialog(Window janelaPai, Serie serie) {
        super(janelaPai, "Detalhes da Série - " + serie.getNome(), ModalityType.APPLICATION_MODAL);
        this.serie = serie;
        this.labelImagem = new JLabel("Carregando imagem...", SwingConstants.CENTER);

        configurarJanela();
        construirInterface();
        carregarImagem();
    }

    public static void exibir(Component componentePai, Serie serie) {
        if (serie == null) {
            JOptionPane.showMessageDialog(componentePai, "Selecione uma série primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Window janelaPai = SwingUtilities.getWindowAncestor(componentePai);
        DetalhesSerieDialog dialog = new DetalhesSerieDialog(janelaPai, serie);
        dialog.setVisible(true);
    }

    private void configurarJanela() {
        setSize(TelaPrincipal.TAMANHO_PADRAO_VIEW);
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }

    private void construirInterface() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(20, 0));
        painelPrincipal.setBackground(TelaPrincipal.COR_FUNDO);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel painelImagem = criarPainelImagem();
        JPanel painelInformacoes = criarPainelInformacoes();

        painelPrincipal.add(painelImagem, BorderLayout.WEST);
        painelPrincipal.add(painelInformacoes, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private JPanel criarPainelImagem() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setOpaque(false);
        painel.setPreferredSize(new Dimension(200, 0));

        labelImagem.setPreferredSize(new Dimension(200, 280));
        labelImagem.setBackground(Color.WHITE);
        labelImagem.setOpaque(true);
        labelImagem.setBorder(BorderFactory.createLineBorder(TelaPrincipal.COR_BORDA));

        JLabel nota = new JLabel("Nota: " + String.format("%.1f", serie.getNota()) + " / 10", SwingConstants.CENTER);
        nota.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nota.setForeground(TelaPrincipal.COR_TEXTO_MAIN);

        JLabel status = new JLabel("Status: " + valorOuPadrao(serie.getStatus()), SwingConstants.CENTER);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        status.setForeground(TelaPrincipal.COR_TEXTO_MUTED);

        JPanel blocoTexto = new JPanel(new GridLayout(2, 1, 4, 0));
        blocoTexto.setOpaque(false);
        blocoTexto.add(nota);
        blocoTexto.add(status);

        painel.add(labelImagem, BorderLayout.CENTER);
        painel.add(blocoTexto, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarPainelInformacoes() {
        JPanel painel = new JPanel(new BorderLayout(0, 16));
        painel.setOpaque(false);

        JPanel topo = new JPanel();
        topo.setOpaque(false);
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel(serie.getNome());
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(TelaPrincipal.COR_TEXTO_MAIN);
        topo.add(titulo);
        topo.add(Box.createVerticalStrut(4));

        JPanel gridMeta = new JPanel(new GridLayout(2, 2, 12, 12));
        gridMeta.setOpaque(false);
        gridMeta.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        gridMeta.add(criarItemInfo("GÊNEROS", serie.getGeneros()));
        gridMeta.add(criarItemInfo("IDIOMA", serie.getIdioma()));
        gridMeta.add(criarItemInfo("EMISSORA", serie.getEmissora()));
        gridMeta.add(criarItemInfo("ESTREIA", serie.getDataEstreia()));

        topo.add(gridMeta);
        painel.add(topo, BorderLayout.NORTH);

        // Área da Sinopse / Sumário
        JPanel centro = new JPanel(new BorderLayout(0, 6));
        centro.setOpaque(false);

        JLabel lblSinopse = new JLabel("Sinopse");
        lblSinopse.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSinopse.setForeground(TelaPrincipal.COR_TEXTO_MAIN);

        JTextArea txtSumario = new JTextArea(valorOuPadrao(serie.getSumario()));
        txtSumario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSumario.setForeground(TelaPrincipal.COR_TEXTO_MUTED);
        txtSumario.setLineWrap(true);
        txtSumario.setWrapStyleWord(true);
        txtSumario.setEditable(false);
        txtSumario.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(txtSumario);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TelaPrincipal.COR_BORDA),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        centro.add(lblSinopse, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        painel.add(centro, BorderLayout.CENTER);

        // Rodapé de Fechamento
        JButton btnFechar = TelaPrincipal.criarBotaoEstilizado("Fechar", TelaPrincipal.COR_DESTAQUE);
        btnFechar.addActionListener(e -> dispose());

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rodape.setOpaque(false);
        rodape.add(btnFechar);

        painel.add(rodape, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarItemInfo(String rotulo, String valor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel lblRotulo = new JLabel(rotulo);
        lblRotulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblRotulo.setForeground(TelaPrincipal.COR_TEXTO_MUTED);

        JLabel lblValor = new JLabel(valorOuPadrao(valor));
        lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblValor.setForeground(TelaPrincipal.COR_TEXTO_MAIN);

        p.add(lblRotulo, BorderLayout.NORTH);
        p.add(lblValor, BorderLayout.CENTER);
        return p;
    }

    private String valorOuPadrao(String valor) {
        return (valor == null || valor.isBlank() || valor.equalsIgnoreCase("N/A")) ? "Não informado" : valor;
    }

    private void carregarImagem() {
        String imagemUrl = serie.getImagemUrl();
        if (imagemUrl == null || imagemUrl.isBlank()) {
            labelImagem.setText("Sem Imagem");
            return;
        }

        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                BufferedImage imagem = ImageIO.read(new URL(imagemUrl));
                if (imagem == null) return null;
                Image redimensionada = imagem.getScaledInstance(200, 280, Image.SCALE_SMOOTH);
                return new ImageIcon(redimensionada);
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icone = get();
                    if (icone == null) {
                        labelImagem.setText("Sem Imagem");
                    } else {
                        labelImagem.setText("");
                        labelImagem.setIcon(icone);
                    }
                } catch (Exception e) {
                    labelImagem.setText("Erro ao Carregar");
                }
            }
        };
        worker.execute();
    }
}