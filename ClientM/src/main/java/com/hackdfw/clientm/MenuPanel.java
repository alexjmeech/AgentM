package com.hackdfw.clientm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

    protected MenuPanel() {
        this(GameUI.GAMESTATE);
    }

    private MenuPanel(int state) {
        setLayout(new BorderLayout());
        add(westMenu(state), BorderLayout.WEST);
//        add(topMenu(state), BorderLayout.NORTH);
//        add(eastMenu(state), BorderLayout.EAST);
//        add(midPanel(state), BorderLayout.CENTER);
    }

    /** Menus */

    private JPanel westMenu(int state) {
        JPanel menu = new JPanel();

        switch(state) {
            default: {
                menu.setLayout(new BorderLayout(20, 20));
                menu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
                menu.add(setHomeButton(), BorderLayout.NORTH);
                menu.add(setCharStatsButton(), BorderLayout.SOUTH);
            }
        }

        return menu;
    }

//    private JPanel topMenu(int state) {
//        JPanel menu = new JPanel();
//
//        switch(state) {
//            default: {
//                menu.setLayout(new BorderLayout(20, 20));
//                menu.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
//            }
//        }
//
//        return menu;
//    }
//
//    private JPanel eastMenu(int state) {
//        JPanel menu = new JPanel();
//
//        switch(state) {
//            default: {
//                menu.setLayout(new BorderLayout(20, 20));
//                menu.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
//            }
//        }
//
//        return menu;
//    }
//
//    private JPanel midPanel(int state) {
//        JPanel menu = new JPanel();
//
//        switch(state) {
//            default: {
//
//            }
//        }
//
//        return menu;
//    }

    /** Buttons */

    private JButton setHomeButton() {
        JButton home = getGenericButton(KeyEvent.VK_H);
        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("hello");
                // midPanel = null;
            }


        });
        return home;
    }

    private JButton setCharStatsButton() {
        JButton stats = getGenericButton(KeyEvent.VK_C);

        stats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Hello");
                //midPanel = charStats();
            }


        });
        return stats;
    }

    private JButton getGenericButton(int command) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(35, 35));
        button.setIcon(GameUI.createImageIcon("metabutton.jpg"));
        button.setBackground(Color.WHITE);
        button.setBorder(null);
        button.setFocusable(false);
        button.setMnemonic(command);
        return button;
    }
}
