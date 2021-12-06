package gui;

import logicControl.DWGraph;
import logicControl.DWGraphAlgo;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MainFrame extends JFrame implements ActionListener {

    static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];
    DWGraphAlgo gAlgo;
    private DrawGraphPanel panel;
    private JButton fullScreen;
    private JButton exitFS;
    private JPanel fsPanel;
    private JPanel exitFSPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu buildMenu;
    private JMenuItem loadG;
    private JMenuItem saveG;
    private JMenuItem exitItem;


    public MainFrame(DWGraphAlgo gAlgo) throws HeadlessException {
        this.gAlgo = gAlgo;
        this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph());
        this.fsPanel = new JPanel(new GridLayout(1,1,0,0));
        this.exitFSPanel = new JPanel(new GridLayout(1,1,0,0));
        this.fullScreen = new JButton("Full screen");
        this.exitFS = new JButton("Exit full screen");
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.editMenu = new JMenu("Edit");
        this.buildMenu = new JMenu("Build");
        this.exitItem = new JMenuItem("Exit");
        this.loadG = new JMenuItem("Load Graph");
        this.saveG = new JMenuItem("save Graph");


        Color color = new Color(0,160,160);
        Font font = new Font("Serif", Font.PLAIN, 20);
        this.fullScreen.setBackground(color);
        this.exitFS.setBackground(color);
        this.fullScreen.setBorder(new LineBorder(Color.BLACK));
        this.exitFS.setBorder(new LineBorder(Color.BLACK));
        this.exitFSPanel.setVisible(false);
        this.exitFS.setSize( 220,30);
        this.fileMenu.setSize(220,30);
        this.fsPanel.setBounds(680,2,220,30);
        this.exitFSPanel.setBounds(680,2,220,30);
        this.fullScreen.addActionListener(this);
        this.exitFS.addActionListener(this);
        this.loadG.addActionListener(this);
        this.exitItem.addActionListener(this);
        this.saveG.addActionListener(this);

        this.fileMenu.add(loadG);
        this.fileMenu.add(saveG);
        this.fileMenu.add(exitItem);
        this.menuBar.add(fileMenu);
        this.menuBar.add(editMenu);
        this.menuBar.add(buildMenu);
        this.setJMenuBar(menuBar);
        this.fsPanel.add(fullScreen);
        this.exitFSPanel.add(exitFS);
        this.add(fsPanel);
        this.add(exitFSPanel);
        this.add(panel);


        this.setSize(DrawGraphPanel.screenSize.width, DrawGraphPanel.screenSize.height);
        this.setBackground(color);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fullScreen) {
            device.setFullScreenWindow(this);
            this.fsPanel.setVisible(false);
            this.exitFSPanel.setVisible(true);
        }

        if (e.getSource() == exitFS) {
            device.setFullScreenWindow(null);
            this.fsPanel.setVisible(true);
            this.exitFSPanel.setVisible(false);
        }
        if (e.getSource() == loadG) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./Data"));
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                String graphPath = fileChooser.getSelectedFile().getAbsolutePath();
                this.gAlgo.load(graphPath);
                String graphName = "";
                for (int i = graphPath.length() - 1; i > 0; i--) {
                    if (graphPath.charAt(i) == '\\') {
                        graphName = graphPath.substring(i + 1);
                        break;
                    }
                }
                this.setTitle("Your current graph: " + graphName);
                panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph());
                this.add(panel);
                this.remove(panel);
            }
        }

        if (e.getSource() == exitItem) {
            System.exit(0);
        }

        if (e.getSource() == saveG) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./Data"));
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                String graphPath = fileChooser.getSelectedFile().getAbsolutePath();
                this.gAlgo.save(graphPath);
            }
        }



    }

    public static void main(String[] args) {
        DWGraphAlgo algo = new DWGraphAlgo();
        algo.load("Data/G1.json");
        new MainFrame(algo);
    }
}

