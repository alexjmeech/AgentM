package java.com.hackdfw.clientm;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class GameUI extends JFrame {

    /** Default dimension */
    private final static Dimension DEFAULT_SIZE = new Dimension(1280, 720);
    private final static String IMAGE_DIR = "../image/";
    protected static int GAMESTATE = 0;

    private static JPanel main = new JPanel();
    private static JPanel userOverlay = new JPanel();
    private static JPanel spriteLayer = new JPanel();


    /** Load UI */

    public GameUI() {
        this(Toolkit.getDefaultToolkit().getScreenSize());
    }


    private GameUI(Dimension dim) {
        super("Sleeper Agent Force: M");
        setLocation(dim.width/2-155, dim.height/2-225);
        setSize(DEFAULT_SIZE);
        configureUI();
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     *
     */
    protected void configureUI() {
        setIconImage(Objects.requireNonNull(createImageIcon("logo.png")).getImage());
        setLayout(new BorderLayout());

        main = getMain(GAMESTATE);
        userOverlay = getButtons(GAMESTATE);
        spriteLayer = getSprites(GAMESTATE);

        add(main, BorderLayout.CENTER);
        add(userOverlay, BorderLayout.CENTER);
        add(spriteLayer, BorderLayout.CENTER);
    }

    /**
     * Image icon helper
     * @param name filename
     * @return returns image icon if image was found, otherwise null
     */

    protected ImageIcon createImageIcon(String name) {
        URL imageUrl = getClass().getResource(IMAGE_DIR + name);
        if(imageUrl != null) return new ImageIcon(imageUrl);
        return null;
    }

    /**
     * Changes panels based on GameState
     * @return
     */
    protected void changeState(int state) {
        GAMESTATE = state;
        main = getMain(GAMESTATE);
        userOverlay = getButtons(GAMESTATE);
        spriteLayer = getSprites(GAMESTATE);

        main.revalidate();
        userOverlay.revalidate();
        spriteLayer.revalidate();
        revalidate();

        main.repaint();
        userOverlay.repaint();
        spriteLayer.repaint();
        repaint();
    }
}
