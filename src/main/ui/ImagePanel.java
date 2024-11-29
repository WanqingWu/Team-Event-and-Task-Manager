package ui;

import javax.swing.*;
import java.awt.*;

// Represents the image panel
public class ImagePanel extends JPanel {
    private Image backgroundImage;

    // EFFECTS: constructs a panel with a background image
    public ImagePanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
        }
    }

    @Override
    // EFFECTS: paints graph
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
