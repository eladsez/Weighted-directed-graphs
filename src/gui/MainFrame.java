package gui;

import api.NodeData;
import logicControl.DWGraph;
import logicControl.DWGraphAlgo;
import logicControl.Node;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
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
    final private JButton fullScreen;
    final private JButton exitFS;
    final private JPanel fsPanel;
    final private JPanel exitFSPanel;
    final private JMenuBar menuBar;
    final private JMenu fileMenu;
    final private JMenu algoMenu;
    final private JMenu editMenu;
    final private JMenuItem loadG;
    final private JMenuItem saveG;
    final private JMenuItem exitItem;
    final private JMenuItem shortestPathDistItem;
    final private JMenuItem shortestPathItem;
    final private JMenuItem centerItem;
    final private JMenuItem isConnectedItem;
    final private JMenuItem tspItem;
    final private JMenuItem addNodeItem;
    final private JMenuItem addEdgeItem;
    final private JMenuItem removeNodeItem;
    final private JMenuItem removeEdgeItem;


    public MainFrame(DWGraphAlgo gAlgo) throws HeadlessException {
        this.gAlgo = gAlgo;
        this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph(), null, null, null);
        this.fsPanel = new JPanel(new GridLayout(1,1,0,0));
        this.exitFSPanel = new JPanel(new GridLayout(1,1,0,0));
        this.fullScreen = new JButton("Full screen");
        this.exitFS = new JButton("Exit full screen");
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.algoMenu = new JMenu("Algorithms");
        this.editMenu = new JMenu("Edit");
        this.exitItem = new JMenuItem("Exit");
        this.loadG = new JMenuItem("Load Graph");
        this.saveG = new JMenuItem("save Graph");
        this.shortestPathDistItem = new JMenuItem("ShortestPathDist");
        this.shortestPathItem = new JMenuItem("ShortestPath");
        this.centerItem = new JMenuItem("Center");
        this.isConnectedItem = new JMenuItem("Is connected");
        this.tspItem = new JMenuItem("tsp");
        this.addNodeItem = new JMenuItem("Add Node");
        this.addEdgeItem = new JMenuItem("Add Edge");
        this.removeNodeItem = new JMenuItem("Remove Node");
        this.removeEdgeItem = new JMenuItem("Remove Edge");

        Color color = new Color(0,160,160);
        Font font = new Font("Serif", Font.PLAIN, 20);
        UIManager.put("OptionPane.messageFont", new FontUIResource(font));
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
        this.addNodeItem.addActionListener(this);
        this.addEdgeItem.addActionListener(this);
        this.removeNodeItem.addActionListener(this);
        this.removeEdgeItem.addActionListener(this);

        this.editMenu.add(addNodeItem);
        this.editMenu.add(removeNodeItem);
        this.editMenu.add(addEdgeItem);
        this.editMenu.add(removeEdgeItem);
        this.algoMenu.add(shortestPathDistItem);
        this.algoMenu.add(shortestPathItem);
        this.algoMenu.add(centerItem);
        this.algoMenu.add(isConnectedItem);
        this.algoMenu.add(tspItem);
        this.fileMenu.add(loadG);
        this.fileMenu.add(saveG);
        this.fileMenu.add(exitItem);
        this.menuBar.add(fileMenu);
        this.menuBar.add(editMenu);
        this.menuBar.add(algoMenu);
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
                if(!this.gAlgo.load(graphPath)){
                    JOptionPane.showMessageDialog(null, "Wrong file type, please load .json file."
                            ,"ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String graphName = "";
                for (int i = graphPath.length() - 1; i > 0; i--) {
                    if (graphPath.charAt(i) == '\\') {
                        graphName = graphPath.substring(i + 1);
                        break;
                    }
                }
                this.setTitle("Your current graph: " + graphName);
                this.remove(panel);
                this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph(), null, null, null);
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
            try{
                int src = Integer.parseInt(pathTo[0]);
                int dest = Integer.parseInt(pathTo[1]);
                Vector<NodeData> path = (Vector<NodeData>) this.gAlgo.shortestPath(src, dest);
                if (path == null){
                    JOptionPane.showMessageDialog(null, "There is no path from "+src+" to "+dest
                            ,"No path",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                this.remove(panel);
                this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph(), path, null, CalledFrom.shortestPath);
                this.add(panel);
                this.repaint();
                this.revalidate();
            }catch (Exception E){
                JOptionPane.showMessageDialog(null, "Invalid src,dest", "ERROR"
                        , JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == centerItem){
            Node center = (Node) this.gAlgo.center();
            if(center == null){
                JOptionPane.showMessageDialog(null, "The graph is not strongly connected!","ERROR"
                        , JOptionPane.ERROR_MESSAGE);
            }
            this.remove(panel);
            this.panel = new DrawGraphPanel((DWGraph) gAlgo.getGraph(), null, center, CalledFrom.center);
            this.add(panel);
            this.repaint();
            this.revalidate();
        }

        if (e.getSource() == shortestPathDistItem){
            String[] pathTo = JOptionPane.showInputDialog(null,"Enter: \"src,dest\"","shortestPathDist"
                    ,JOptionPane.INFORMATION_MESSAGE).split(",");
            try {
                int src = Integer.parseInt(pathTo[0]);
                int dest = Integer.parseInt(pathTo[1]);
                double dist = this.gAlgo.shortestPathDist(src, dest);
                if (dist != -1){
                    JOptionPane.showMessageDialog(null, "The shortest path distance from " + src + " to " +dest+" is: "
                                    +dist,"Distance",JOptionPane.INFORMATION_MESSAGE);
                }
                else JOptionPane.showMessageDialog(null, "There is no path from "+src+" to "+dest
                ,"No path",JOptionPane.WARNING_MESSAGE);
            }catch (Exception E) {
                JOptionPane.showMessageDialog(null, "Invalid src,dest", "ERROR"
                        , JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == isConnectedItem){
            if(this.gAlgo.isConnected())
            JOptionPane.showMessageDialog(null, "The graph is strongly connected!", "isConnected",
                    JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null, "The graph is  NOT strongly connected!", "isConnected",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource() == addNodeItem){

        }
    }

    public static void main(String[] args) {
        DWGraphAlgo algo = new DWGraphAlgo();
        algo.load("Data/G1.json");
        new MainFrame(algo);
    }
}

