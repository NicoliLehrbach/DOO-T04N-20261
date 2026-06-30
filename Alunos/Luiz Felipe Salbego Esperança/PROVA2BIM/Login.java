import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login extends JFrame {

    private JTextField campoNome;

    public Login() {

        setTitle("FagFlix");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Color fundo = new Color(22, 22, 22);
        Color vermelho = new Color(190, 30, 45);

        JPanel painel = new JPanel();
        painel.setBackground(fundo);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("FagFlix");
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setForeground(vermelho);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel subtitulo = new JLabel("Bem-vindo ao FagFlix!");
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel login = new JLabel("Login");
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        campoNome = new JTextField();
        campoNome.setMaximumSize(new Dimension(320, 35));
        campoNome.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JButton entrar = new JButton("Entrar");
        entrar.setBackground(vermelho);
        entrar.setForeground(Color.WHITE);
        entrar.setFocusPainted(false);
        entrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        entrar.setAlignmentX(CENTER_ALIGNMENT);

        entrar.addActionListener(e -> entrar());

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(10));
        painel.add(subtitulo);
        painel.add(Box.createVerticalStrut(25));
        painel.add(login);
        painel.add(Box.createVerticalStrut(5));
        painel.add(campoNome);
        painel.add(Box.createVerticalStrut(25));
        painel.add(entrar);

        add(painel, BorderLayout.CENTER);

        setVisible(true);

    }

    private void entrar() {

        String nome = campoNome.getText().trim();

        if (nome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite um nome.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);

            return;

        }

        Usuario usuario = new Usuario(nome);

        Persistencia.salvar(usuario);

        SistemaSeries sistema = new SistemaSeries(usuario);

        dispose();

        new TelaPrincipal(sistema);

    }

}