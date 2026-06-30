import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        Usuario usuario = Persistencia.carregar();

        if (usuario != null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Bem-vindo de volta, " + usuario.getNome() + "!",
                    "FagFlix",
                    JOptionPane.INFORMATION_MESSAGE
            );

            SistemaSeries sistema = new SistemaSeries(usuario);

            new TelaPrincipal(sistema);

        } else {

            new Login();

        }

    }

}