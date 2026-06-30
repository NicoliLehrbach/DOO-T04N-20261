import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends JFrame {

    private SistemaSeries sistema;

    private CardLayout cardLayout;
    private JPanel painelCentral;

    private JTable tabelaBusca;
    private JTable tabelaFavoritos;
    private JTable tabelaAssistidas;
    private JTable tabelaDesejo;

    private DefaultTableModel modeloBusca;
    private DefaultTableModel modeloFavoritos;
    private DefaultTableModel modeloAssistidas;
    private DefaultTableModel modeloDesejo;

    private JTextField campoBusca;

    private JComboBox<String> ordemFavoritos;
    private JComboBox<String> ordemAssistidas;
    private JComboBox<String> ordemDesejo;

    private List<Serie> resultadosBusca;

    private Color vermelho = new Color(185,28,28);
    private Color vermelhoEscuro = new Color(120,15,15);
    private Color preto = new Color(20,20,20);
    private Color cinza = new Color(35,35,35);
    private Color fundo = new Color(235,235,235);

    public TelaPrincipal(SistemaSeries sistema){

        this.sistema = sistema;

        setTitle("FagFlix");
        setSize(1300,750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                Persistencia.salvar(sistema.getUsuario());

            }

        });

        criarTopo();

        criarMenu();

        criarCentro();

        mostrarTela("BUSCA");

        atualizarListas();

        setVisible(true);

    }

    private void criarTopo(){

        JPanel topo = new JPanel(new BorderLayout());

        topo.setBackground(preto);

        topo.setPreferredSize(new Dimension(100,75));

        topo.setBorder(new MatteBorder(0,0,3,0,vermelho));

        JPanel esquerda = new JPanel();

        esquerda.setOpaque(false);

        esquerda.setLayout(new BoxLayout(esquerda,BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("FagFlix");

        titulo.setForeground(vermelho);

        titulo.setFont(new Font("Segoe UI",Font.BOLD,28));

        JLabel sub = new JLabel("Gerenciador de series e filmes");

        sub.setForeground(Color.WHITE);

        esquerda.add(Box.createVerticalStrut(10));

        esquerda.add(titulo);

        esquerda.add(sub);

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        direita.setOpaque(false);

        JLabel usuario = new JLabel("Ola, "+sistema.getUsuario().getNome());

        usuario.setForeground(Color.WHITE);

        usuario.setFont(new Font("Segoe UI",Font.PLAIN,16));

        JButton sair = new JButton("Sair");

        sair.setBackground(vermelho);

        sair.setForeground(Color.WHITE);

        sair.setFocusPainted(false);

        sair.addActionListener(e->{

            Persistencia.removerUsuario();

            dispose();

            new Login();

        });

        direita.add(usuario);

        direita.add(Box.createHorizontalStrut(15));

        direita.add(sair);

        topo.add(esquerda,BorderLayout.WEST);

        topo.add(direita,BorderLayout.EAST);

        add(topo,BorderLayout.NORTH);

    }

    private void criarMenu(){

        JPanel menu = new JPanel();

        menu.setBackground(cinza);

        menu.setPreferredSize(new Dimension(210,100));

        menu.setLayout(new GridLayout(8,1,10,10));

        menu.setBorder(new EmptyBorder(20,15,20,15));

        JButton buscar = criarBotaoMenu("Buscar");

        JButton favoritos = criarBotaoMenu("Favoritos");

        JButton assistidas = criarBotaoMenu("Assistidas");

        JButton desejo = criarBotaoMenu("Quero assistir");

        buscar.addActionListener(e->mostrarTela("BUSCA"));

        favoritos.addActionListener(e->{

            atualizarListas();

            mostrarTela("FAVORITOS");

        });

        assistidas.addActionListener(e->{

            atualizarListas();

            mostrarTela("ASSISTIDAS");

        });

        desejo.addActionListener(e->{

            atualizarListas();

            mostrarTela("DESEJO");

        });

        menu.add(buscar);

        menu.add(favoritos);

        menu.add(assistidas);

        menu.add(desejo);

        add(menu,BorderLayout.WEST);

    }

    private JButton criarBotaoMenu(String texto){

        JButton b = new JButton(texto);

        b.setBackground(vermelho);

        b.setForeground(Color.WHITE);

        b.setFocusPainted(false);

        b.setFont(new Font("Segoe UI",Font.BOLD,15));

        return b;

    }

        private void criarCentro() {

        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);

        painelCentral.add(criarTelaBusca(), "BUSCA");
        painelCentral.add(criarTelaFavoritos(), "FAVORITOS");
        painelCentral.add(criarTelaAssistidas(), "ASSISTIDAS");
        painelCentral.add(criarTelaDesejo(), "DESEJO");

        add(painelCentral, BorderLayout.CENTER);

    }

    private JPanel criarTelaBusca() {

        JPanel painel = new JPanel(new BorderLayout(10,10));

        painel.setBackground(fundo);

        painel.setBorder(new EmptyBorder(20,20,20,20));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topo.setBackground(fundo);

        JLabel titulo = new JLabel("Buscar series e filmes");

        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        campoBusca = new JTextField(28);

        JButton buscar = new JButton("Buscar");

        buscar.setBackground(vermelho);

        buscar.setForeground(Color.WHITE);

        buscar.addActionListener(e -> buscarSeries());

        topo.add(titulo);

        topo.add(Box.createHorizontalStrut(30));

        topo.add(campoBusca);

        topo.add(buscar);

        modeloBusca = new DefaultTableModel();

        modeloBusca.addColumn("Nome");
        modeloBusca.addColumn("Idioma");
        modeloBusca.addColumn("Nota");
        modeloBusca.addColumn("Estado");
        modeloBusca.addColumn("Estreia");

        tabelaBusca = new JTable(modeloBusca);

        tabelaBusca.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabelaBusca);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        botoes.setBackground(fundo);

        JButton detalhes = new JButton("Ver detalhes");

        JButton favorito = new JButton("Favoritos");

        JButton assistida = new JButton("Assistidas");

        JButton desejo = new JButton("Quero assistir");

        detalhes.addActionListener(e -> abrirDetalhesBusca());

        favorito.addActionListener(e -> adicionarFavorito());

        assistida.addActionListener(e -> adicionarAssistida());

        desejo.addActionListener(e -> adicionarDesejo());

        botoes.add(detalhes);

        botoes.add(favorito);

        botoes.add(assistida);

        botoes.add(desejo);

        painel.add(topo, BorderLayout.NORTH);

        painel.add(scroll, BorderLayout.CENTER);

        painel.add(botoes, BorderLayout.SOUTH);

        return painel;

    }

    private JPanel criarTelaFavoritos() {

    JPanel painel = new JPanel(new BorderLayout(10,10));

    painel.setBackground(fundo);

    painel.setBorder(new EmptyBorder(20,20,20,20));

    JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

    topo.setBackground(fundo);

    JLabel titulo = new JLabel("Favoritos");

    titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

    topo.add(titulo);

    topo.add(Box.createHorizontalStrut(30));

    topo.add(new JLabel("Ordenar por"));

    ordemFavoritos = new JComboBox<>();

    ordemFavoritos.addItem("Nome");
    ordemFavoritos.addItem("Nota");
    ordemFavoritos.addItem("Estado");
    ordemFavoritos.addItem("Estreia");

    JButton ordenar = new JButton("Aplicar");

    ordenar.addActionListener(e -> ordenarFavoritos());

    topo.add(ordemFavoritos);

    topo.add(ordenar);

    modeloFavoritos = new DefaultTableModel();

    modeloFavoritos.addColumn("Nome");
    modeloFavoritos.addColumn("Idioma");
    modeloFavoritos.addColumn("Nota");
    modeloFavoritos.addColumn("Estado");
    modeloFavoritos.addColumn("Estreia");

    tabelaFavoritos = new JTable(modeloFavoritos);

    tabelaFavoritos.setRowHeight(25);

    JScrollPane scroll = new JScrollPane(tabelaFavoritos);

    JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    botoes.setBackground(fundo);

    JButton detalhes = new JButton("Ver detalhes");

    JButton remover = new JButton("Remover");

    detalhes.addActionListener(e -> detalhesFavorito());

    remover.addActionListener(e -> removerFavorito());

    botoes.add(detalhes);

    botoes.add(remover);

    painel.add(topo, BorderLayout.NORTH);

    painel.add(scroll, BorderLayout.CENTER);

    painel.add(botoes, BorderLayout.SOUTH);

    return painel;

}

private JPanel criarTelaAssistidas() {

    JPanel painel = new JPanel(new BorderLayout(10,10));

    painel.setBackground(fundo);

    painel.setBorder(new EmptyBorder(20,20,20,20));

    JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

    topo.setBackground(fundo);

    JLabel titulo = new JLabel("Assistidas");

    titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

    topo.add(titulo);

    topo.add(Box.createHorizontalStrut(30));

    topo.add(new JLabel("Ordenar por"));

    ordemAssistidas = new JComboBox<>();

    ordemAssistidas.addItem("Nome");
    ordemAssistidas.addItem("Nota");
    ordemAssistidas.addItem("Estado");
    ordemAssistidas.addItem("Estreia");

    JButton ordenar = new JButton("Aplicar");

    ordenar.addActionListener(e -> ordenarAssistidas());

    topo.add(ordemAssistidas);

    topo.add(ordenar);

    modeloAssistidas = new DefaultTableModel();

    modeloAssistidas.addColumn("Nome");
    modeloAssistidas.addColumn("Idioma");
    modeloAssistidas.addColumn("Nota");
    modeloAssistidas.addColumn("Estado");
    modeloAssistidas.addColumn("Estreia");

    tabelaAssistidas = new JTable(modeloAssistidas);

    tabelaAssistidas.setRowHeight(25);

    JScrollPane scroll = new JScrollPane(tabelaAssistidas);

    JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    botoes.setBackground(fundo);

    JButton detalhes = new JButton("Ver detalhes");

    JButton remover = new JButton("Remover");

    detalhes.addActionListener(e -> detalhesAssistida());

    remover.addActionListener(e -> removerAssistida());

    botoes.add(detalhes);

    botoes.add(remover);

    painel.add(topo, BorderLayout.NORTH);

    painel.add(scroll, BorderLayout.CENTER);

    painel.add(botoes, BorderLayout.SOUTH);

    return painel;

}

private JPanel criarTelaDesejo() {

    JPanel painel = new JPanel(new BorderLayout(10,10));

    painel.setBackground(fundo);

    painel.setBorder(new EmptyBorder(20,20,20,20));

    JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

    topo.setBackground(fundo);

    JLabel titulo = new JLabel("Quero assistir");

    titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

    topo.add(titulo);

    topo.add(Box.createHorizontalStrut(30));

    topo.add(new JLabel("Ordenar por"));

    ordemDesejo = new JComboBox<>();

    ordemDesejo.addItem("Nome");
    ordemDesejo.addItem("Nota");
    ordemDesejo.addItem("Estado");
    ordemDesejo.addItem("Estreia");

    JButton ordenar = new JButton("Aplicar");

    ordenar.addActionListener(e -> ordenarDesejo());

    topo.add(ordemDesejo);

    topo.add(ordenar);

    modeloDesejo = new DefaultTableModel();

    modeloDesejo.addColumn("Nome");
    modeloDesejo.addColumn("Idioma");
    modeloDesejo.addColumn("Nota");
    modeloDesejo.addColumn("Estado");
    modeloDesejo.addColumn("Estreia");

    tabelaDesejo = new JTable(modeloDesejo);

    tabelaDesejo.setRowHeight(25);

    JScrollPane scroll = new JScrollPane(tabelaDesejo);

    JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    botoes.setBackground(fundo);

    JButton detalhes = new JButton("Ver detalhes");

    JButton remover = new JButton("Remover");

    detalhes.addActionListener(e -> detalhesDesejo());

    remover.addActionListener(e -> removerDesejo());

    botoes.add(detalhes);

    botoes.add(remover);

    painel.add(topo, BorderLayout.NORTH);

    painel.add(scroll, BorderLayout.CENTER);

    painel.add(botoes, BorderLayout.SOUTH);

    return painel;

}

private void mostrarTela(String tela){

    cardLayout.show(painelCentral,tela);

}

private void buscarSeries() {

    String texto = campoBusca.getText().trim();

    if (texto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Digite um nome.");
        return;
    }

    resultadosBusca = TvMazeAPI.buscarSeries(texto);

    modeloBusca.setRowCount(0);

    for (Serie s : resultadosBusca) {

        modeloBusca.addRow(new Object[]{
                s.getNome(),
                s.getIdioma(),
                s.getNota(),
                s.getStatus(),
                s.getEstreia()
        });

    }

}

private Serie getSerieBusca() {

    int linha = tabelaBusca.getSelectedRow();

    if (linha == -1) {

        JOptionPane.showMessageDialog(this, "Selecione uma serie.");

        return null;

    }

    return resultadosBusca.get(linha);

}

private void adicionarFavorito() {

    Serie s = getSerieBusca();

    if (s == null) return;

    sistema.adicionarFavorito(s);

    atualizarListas();

    Persistencia.salvar(sistema.getUsuario());

}

private void adicionarAssistida() {

    Serie s = getSerieBusca();

    if (s == null) return;

    sistema.adicionarAssistida(s);

    atualizarListas();

    Persistencia.salvar(sistema.getUsuario());

}

private void adicionarDesejo() {

    Serie s = getSerieBusca();

    if (s == null) return;

    sistema.adicionarDesejo(s);

    atualizarListas();

    Persistencia.salvar(sistema.getUsuario());

}

private void atualizarListas() {

    atualizarTabela(
            modeloFavoritos,
            sistema.getUsuario().getFavoritos()
    );

    atualizarTabela(
            modeloAssistidas,
            sistema.getUsuario().getAssistidas()
    );

    atualizarTabela(
            modeloDesejo,
            sistema.getUsuario().getDesejoAssistir()
    );

}

private void atualizarTabela(DefaultTableModel modelo, List<Serie> lista) {

    modelo.setRowCount(0);

    for (Serie s : lista) {

        modelo.addRow(new Object[]{
                s.getNome(),
                s.getIdioma(),
                s.getNota(),
                s.getStatus(),
                s.getEstreia()
        });

    }

}

private void removerFavorito() {

    int linha = tabelaFavoritos.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma serie.");
        return;
    }

    sistema.getUsuario().getFavoritos().remove(linha);

    atualizarListas();

    Persistencia.salvar(sistema.getUsuario());

}

private void removerAssistida() {

    int linha = tabelaAssistidas.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma serie.");
        return;
    }

    sistema.getUsuario().getAssistidas().remove(linha);

    atualizarListas();

    Persistencia.salvar(sistema.getUsuario());

}

private void removerDesejo() {

    int linha = tabelaDesejo.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma serie.");
        return;
    }

    sistema.getUsuario().getDesejoAssistir().remove(linha);

    atualizarListas();

    Persistencia.salvar(sistema.getUsuario());

}

private void abrirDetalhesBusca() {

    Serie s = getSerieBusca();

    if (s == null) return;

    mostrarDetalhes(s);

}

private void detalhesFavorito() {

    int linha = tabelaFavoritos.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma serie.");
        return;
    }

    mostrarDetalhes(
            sistema.getUsuario().getFavoritos().get(linha)
    );

}

private void detalhesAssistida() {

    int linha = tabelaAssistidas.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma serie.");
        return;
    }

    mostrarDetalhes(
            sistema.getUsuario().getAssistidas().get(linha)
    );

}

private void detalhesDesejo() {

    int linha = tabelaDesejo.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma serie.");
        return;
    }

    mostrarDetalhes(
            sistema.getUsuario().getDesejoAssistir().get(linha)
    );

}

private void mostrarDetalhes(Serie serie) {

    JDialog dialog = new JDialog(this, "Detalhes", true);

    dialog.setSize(700, 600);

    dialog.setLocationRelativeTo(this);

    dialog.setLayout(new BorderLayout());

    JPanel topo = new JPanel(new GridLayout(0, 2, 10, 10));

    topo.setBorder(new EmptyBorder(15, 15, 15, 15));

    topo.add(new JLabel("Nome:"));
    topo.add(new JLabel(serie.getNome()));

    topo.add(new JLabel("Idioma:"));
    topo.add(new JLabel(serie.getIdioma()));

    topo.add(new JLabel("Generos:"));
    topo.add(new JLabel(serie.getGeneros()));

    topo.add(new JLabel("Nota:"));
    topo.add(new JLabel(String.valueOf(serie.getNota())));

    topo.add(new JLabel("Status:"));
    topo.add(new JLabel(serie.getStatus()));

    topo.add(new JLabel("Estreia:"));
    topo.add(new JLabel(serie.getEstreia()));

    topo.add(new JLabel("Termino:"));
    topo.add(new JLabel(serie.getTermino()));

    topo.add(new JLabel("Emissora:"));
    topo.add(new JLabel(serie.getEmissora()));

    JTextArea resumo = new JTextArea();

    resumo.setText(serie.getResumo());

    resumo.setEditable(false);

    resumo.setLineWrap(true);

    resumo.setWrapStyleWord(true);

    resumo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    JScrollPane scroll = new JScrollPane(resumo);

    scroll.setBorder(
            BorderFactory.createTitledBorder("Resumo")
    );

    JButton fechar = new JButton("Fechar");

    fechar.addActionListener(e -> dialog.dispose());

    JPanel rodape = new JPanel();

    rodape.add(fechar);

    dialog.add(topo, BorderLayout.NORTH);

    dialog.add(scroll, BorderLayout.CENTER);

    dialog.add(rodape, BorderLayout.SOUTH);

    dialog.setVisible(true);

}

private void ordenarFavoritos() {

    String ordem = (String) ordemFavoritos.getSelectedItem();

    if (ordem.equals("Nome")) {

        sistema.getUsuario().getFavoritos().sort(
                (a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));

    } else if (ordem.equals("Nota")) {

        sistema.getUsuario().getFavoritos().sort(
                (a, b) -> Double.compare(b.getNota(), a.getNota()));

    } else if (ordem.equals("Estado")) {

        sistema.getUsuario().getFavoritos().sort(
                (a, b) -> a.getStatus().compareToIgnoreCase(b.getStatus()));

    } else {

        sistema.getUsuario().getFavoritos().sort(
                (a, b) -> a.getEstreia().compareToIgnoreCase(b.getEstreia()));

    }

    atualizarListas();

}

private void ordenarAssistidas() {

    String ordem = (String) ordemAssistidas.getSelectedItem();

    if (ordem.equals("Nome")) {

        sistema.getUsuario().getAssistidas().sort(
                (a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));

    } else if (ordem.equals("Nota")) {

        sistema.getUsuario().getAssistidas().sort(
                (a, b) -> Double.compare(b.getNota(), a.getNota()));

    } else if (ordem.equals("Estado")) {

        sistema.getUsuario().getAssistidas().sort(
                (a, b) -> a.getStatus().compareToIgnoreCase(b.getStatus()));

    } else {

        sistema.getUsuario().getAssistidas().sort(
                (a, b) -> a.getEstreia().compareToIgnoreCase(b.getEstreia()));

    }

    atualizarListas();

}

private void ordenarDesejo() {

    String ordem = (String) ordemDesejo.getSelectedItem();

    if (ordem.equals("Nome")) {

        sistema.getUsuario().getDesejoAssistir().sort(
                (a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));

    } else if (ordem.equals("Nota")) {

        sistema.getUsuario().getDesejoAssistir().sort(
                (a, b) -> Double.compare(b.getNota(), a.getNota()));

    } else if (ordem.equals("Estado")) {

        sistema.getUsuario().getDesejoAssistir().sort(
                (a, b) -> a.getStatus().compareToIgnoreCase(b.getStatus()));

    } else {

        sistema.getUsuario().getDesejoAssistir().sort(
                (a, b) -> a.getEstreia().compareToIgnoreCase(b.getEstreia()));

    }

    atualizarListas();

}

private void salvarDados() {

    Persistencia.salvar(sistema.getUsuario());

}

private void atualizarTudo() {

    atualizarListas();

    tabelaBusca.clearSelection();

    tabelaFavoritos.clearSelection();

    tabelaAssistidas.clearSelection();

    tabelaDesejo.clearSelection();

}

private void limparBusca() {

    campoBusca.setText("");

    modeloBusca.setRowCount(0);

    if (resultadosBusca != null) {

        resultadosBusca.clear();

    }

}

private void sairSistema() {

    salvarDados();

    dispose();

    new Login();

}

@Override
public void dispose() {

    salvarDados();

    super.dispose();

}

}