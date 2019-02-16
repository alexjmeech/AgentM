package java.com.hackdfw.clientm;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

import static com.google.common.io.Resources.getResource;

public class GameUI extends JFrame {

    /** Default dimension */
    protected final static Dimension DEFAULT_SIZE = new Dimension(1280, 720);
    protected final static String IMAGE_DIR = "../image/";
    protected static int GAMESTATE;

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
        changeState(0);

        add(main, BorderLayout.CENTER);
        add(userOverlay, BorderLayout.CENTER);
        add(spriteLayer, BorderLayout.CENTER);
    }

    /**
     * Image icon helper
     * @param name filename
     * @return returns image icon if image was found, otherwise null
     */

    protected static ImageIcon createImageIcon(String name) {
        URL imageUrl = getResource(IMAGE_DIR + name);
        if(imageUrl != null) return new ImageIcon(imageUrl);
        return null;
    }

    /**
     * Changes panels based on GameState
     * @return
     */
    protected void changeState(int state) {
        GAMESTATE = state;
        main = new BackgroundPanel();
        userOverlay = new MenuPanel();
        spriteLayer = new SpritePanel();

        revalidate();
        repaint();
    }

}
