package gui;

import api.DirectedWeightedGraphAlgorithms;
import logicControl.DWGraph;
import logicControl.DWGraphAlgo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];
    DWGraphAlgo gAlgo;
    private JPanel buttonsPanel;
    private DrawGraphPanel panel;
    private JButton fullScreen;
    private JButton exitFS;
    private JFrame menuParent;
    private JButton returnToMenu;

    public MainFrame(DWGraphAlgo gAlgo, JFrame menu) throws HeadlessException {
        this.buttonsPanel = new JPanel(new GridLayout(1,2,0,0));
        this.menuParent = menu;
        this.gAlgo = gAlgo;
        this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph());
        this.fullScreen = new JButton("Full screen");
        this.exitFS = new JButton("Exit full screen");
        this.returnToMenu = new JButton("Return to menu");

        Color color = new Color(0,160,160);
        Font font = new Font("Serif", Font.PLAIN, 20);
        this.fullScreen.setBackground(color);
        this.exitFS.setBackground(color);
        this.returnToMenu.setBackground(color);
        this.fullScreen.setBorder(new LineBorder(Color.BLACK));
        this.exitFS.setBorder(new LineBorder(Color.BLACK));
        this.returnToMenu.setBorder(new LineBorder(Color.BLACK));
        this.exitFS.setVisible(false);
        this.exitFS.setBounds(680,0, 220,30);
        this.buttonsPanel.setBounds(30,2,440,30);
        this.fullScreen.addActionListener(this);
        this.exitFS.addActionListener(this);
        this.returnToMenu.addActionListener(this);

        this.buttonsPanel.add(returnToMenu);
        this.buttonsPanel.add(fullScreen);
        this.add(exitFS);
        this.add(buttonsPanel);
        this.add(panel);

        this.setResizable(false);
        this.pack();
        this.setBackground(color);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fullScreen) {
            device.setFullScreenWindow(this);
            this.buttonsPanel.setVisible(false);
            this.exitFS.setVisible(true);
        }

        if (e.getSource() == exitFS){
            device.setFullScreenWindow(null);
            this.buttonsPanel.setVisible(true);
            this.exitFS.setVisible(false);
        }

        if (e.getSource() == returnToMenu){
            this.menuParent.setVisible(true);
            this.dispose();
        }



    }
}

