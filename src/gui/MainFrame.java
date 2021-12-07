package gui;

import api.NodeData;
import logicControl.DWGraph;
import logicControl.DWGraphAlgo;
import logicControl.Node;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;


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
    private JMenu algoMenu;
    private JMenu buildMenu;
    private JMenuItem loadG;
    private JMenuItem saveG;
    private JMenuItem exitItem;
    private JMenuItem shortestPathDistItem;
    private JMenuItem shortestPathItem;
    private JMenuItem centerItem;
    private JMenuItem isConnectedItem;
    private JMenuItem tspItem;


    public MainFrame(DWGraphAlgo gAlgo) throws HeadlessException {
        this.gAlgo = gAlgo;
        this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph(), null);
        this.fsPanel = new JPanel(new GridLayout(1,1,0,0));
        this.exitFSPanel = new JPanel(new GridLayout(1,1,0,0));
        this.fullScreen = new JButton("Full screen");
        this.exitFS = new JButton("Exit full screen");
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.algoMenu = new JMenu("Algorithms");
        this.buildMenu = new JMenu("Build");
        this.exitItem = new JMenuItem("Exit");
        this.loadG = new JMenuItem("Load Graph");
        this.saveG = new JMenuItem("save Graph");
        this.shortestPathDistItem = new JMenuItem("ShortestPathDist");
        this.shortestPathItem = new JMenuItem("ShortestPath");
        this.centerItem = new JMenuItem("Center");
        this.isConnectedItem = new JMenuItem("Is connected");
        this.tspItem = new JMenuItem("tsp");


        Color color = new Color(0,160,160);
        Font font = new Font("Serif", Font.PLAIN, 20);
        this.fullScreen.setBackground(color);
        this.exitFS.setBackground(color);
        this.fullScreen.setBorder(new LineBorder(Color.BLACK));
        this.exitFS.setBorder(new LineBorder(Color.BLACK));
        this.exitFSPanel.setVisible(false);
        this.exitFS.setSize( 220,30);
        this.fileMenu.setSize(220,30);
        this.fsPanel.setBounds(1380,2,150,30);
        this.exitFSPanel.setBounds(1380,2,150,30);

        this.fullScreen.addActionListener(this);
        this.exitFS.addActionListener(this);
        this.loadG.addActionListener(this);
        this.exitItem.addActionListener(this);
        this.saveG.addActionListener(this);
        this.shortestPathDistItem.addActionListener(this);
        this.shortestPathItem.addActionListener(this);
        this.centerItem.addActionListener(this);
        this.isConnectedItem.addActionListener(this);
        this.tspItem.addActionListener(this);

        this.algoMenu.add(shortestPathDistItem);
        this.algoMenu.add(shortestPathItem);
        this.algoMenu.add(centerItem);
        this.algoMenu.add(isConnectedItem);
        this.algoMenu.add(tspItem);
        this.fileMenu.add(loadG);
        this.fileMenu.add(saveG);
        this.fileMenu.add(exitItem);
        this.menuBar.add(fileMenu);
        this.menuBar.add(algoMenu);
        this.menuBar.add(buildMenu);
        this.setJMenuBar(menuBar);
        this.fsPanel.add(fullScreen);
        this.exitFSPanel.add(exitFS);
        this.add(fsPanel);
        this.add(exitFSPanel);
        this.add(panel);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
                this.remove(panel);
                this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph(), null);
                this.add(panel);
                this.repaint();
                this.revalidate();
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

        if (e.getSource() == shortestPathItem){
            String[] pathTo = JOptionPane.showInputDialog(null,"Enter: \"src,dest\"","shortestPath"
                    ,JOptionPane.INFORMATION_MESSAGE).split(",");
            if(pathTo.length == 2){
                int src = Integer.parseInt(pathTo[0]);
                int dest = Integer.parseInt(pathTo[1]);
                Vector<NodeData> path = (Vector<NodeData>) this.gAlgo.shortestPath(src, dest);
                this.remove(panel);
                this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph(), path);
                this.add(panel);
                this.repaint();
                this.revalidate();
                System.out.println(path);
            }
        }



    }

    public static void main(String[] args) {
        DWGraphAlgo algo = new DWGraphAlgo();
        algo.load("Data/G1.json");
        new MainFrame(algo);
    }
}

