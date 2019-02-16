package java.com.hackdfw.clientm;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image img;

    public BackgroundPanel() {
        this(GameUI.createImageIcon(backgroundFromState()).getImage());
    }

    public BackgroundPanel(Image img) {
        this.img = img;
        setSize(GameUI.DEFAULT_SIZE);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    private static String backgroundFromState() {
        switch(GameUI.GAMESTATE) {
            case 1: return GameUI.IMAGE_DIR + "loading.png";
            case 2: return GameUI.IMAGE_DIR + "day.png";
            case 3: return GameUI.IMAGE_DIR + "night.png";
            case 4: return GameUI.IMAGE_DIR + "end.png";
            default: return GameUI.IMAGE_DIR + "menu.png";
        }
    }
}
