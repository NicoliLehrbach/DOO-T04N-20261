package Prova_Final;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

// Esta classe serve APENAS para desenhar a imagem na tela
public class PainelComFundo extends JPanel {
    private Image imagemFundo;

    public PainelComFundo(String caminhoImagem) {
        try {
            imagemFundo = ImageIO.read(new File(caminhoImagem));
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a imagem: " + caminhoImagem);
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemFundo != null) {
            // Desenha a imagem preenchendo o painel inteiro
            g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}