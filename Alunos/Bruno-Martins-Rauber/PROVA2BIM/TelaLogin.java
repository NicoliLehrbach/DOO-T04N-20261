package Prova_Final;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class TelaLogin extends JFrame {
    private JList<String> listaUsuarios;
    private DefaultListModel<String> modeloLista;
    private JTextField txtNome;

    // Cores do Design Roxo/Lilás
    Color corFundoAmestista = new Color(50, 20, 70); 
    Color corLilazClaro = new Color(210, 180, 230);    
    Color corBotaoVioleta = new Color(90, 40, 130);   
    Color corTextoTitulo = new Color(240, 230, 250);   

    public TelaLogin() {
        setTitle("Login - Gerenciador de Séries");
        setSize(500, 350); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // === Chamei a sua foto usando o arquivo do Fundo aqui ===
        PainelComFundo painelFundo = new PainelComFundo("IMG_20260413_080637.jpg");
        setContentPane(painelFundo);

        // --- Painel Lateral (Lista de Cadastros) ---
        modeloLista = new DefaultListModel<>();
        carregarListaUsuarios();
        listaUsuarios = new JList<>(modeloLista);
        
        listaUsuarios.setBackground(new Color(50, 20, 70, 220)); // Roxo translúcido
        listaUsuarios.setForeground(Color.WHITE);
        listaUsuarios.setSelectionBackground(corBotaoVioleta);
        listaUsuarios.setSelectionForeground(Color.WHITE);

        JScrollPane scrollLateral = new JScrollPane(listaUsuarios);
        scrollLateral.setPreferredSize(new Dimension(160, 0));
        scrollLateral.setOpaque(false);
        scrollLateral.getViewport().setOpaque(false);
        
        scrollLateral.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(corLilazClaro), "CADASTROS", 0, 0, null, corTextoTitulo));
        painelFundo.add(scrollLateral, BorderLayout.WEST);

        // --- Painel Central (Formulário) ---
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel lblTitulo = new JLabel("BEM-VINDO AO GERENCIADOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(corTextoTitulo);
        painelCentro.add(lblTitulo, gbc);

        gbc.gridy = 1;
        JLabel lblNome = new JLabel("NOME DO USUÁRIO:");
        lblNome.setFont(new Font("Arial", Font.BOLD, 12));
        lblNome.setForeground(corLilazClaro);
        painelCentro.add(lblNome, gbc);

        gbc.gridy = 2;
        txtNome = new JTextField(15);
        txtNome.setBackground(new Color(50, 20, 70, 220)); 
        txtNome.setForeground(Color.WHITE);
        txtNome.setCaretColor(Color.WHITE); 
        txtNome.setFont(new Font("Arial", Font.PLAIN, 14));
        painelCentro.add(txtNome, gbc);

        gbc.gridy = 3;
        JButton btnEntrar = new JButton("ENTRAR / CRIAR CONTA");
        btnEntrar.setBackground(corBotaoVioleta);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEntrar.setFocusPainted(false); 
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        painelCentro.add(btnEntrar, gbc);

        painelFundo.add(painelCentro, BorderLayout.CENTER);
        
        // --- Eventos ---
        listaUsuarios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaUsuarios.getSelectedValue() != null) {
                txtNome.setText(listaUsuarios.getSelectedValue());
            }
        });

        btnEntrar.addActionListener(e -> fazerLogin());
        txtNome.addActionListener(e -> fazerLogin());
    }

    private void carregarListaUsuarios() {
        ArrayList<DadosUsuario> usuarios = ArquivoService.carregarTodos();
        for (DadosUsuario u : usuarios) {
            modeloLista.addElement(u.nomeUsuario);
        }
    }

    private void fazerLogin() {
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite um nome ou selecione na lista lateral!");
            return;
        }
        
        DadosUsuario usuario = ArquivoService.obterOuCriarUsuario(nome);
        
        TelaPrincipal tela = new TelaPrincipal(usuario);
        tela.setLocationRelativeTo(null); 
        tela.setVisible(true);
        
        this.dispose();
    }

    // Método main (ponto de partida)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}