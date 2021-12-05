package gui;

import com.sun.tools.javac.Main;
import logicControl.DWGraphAlgo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuFrame extends JFrame implements ActionListener {

    private JButton showGraph;
    private JButton shortPath;
    private JButton isConnected;
    private JButton tsp;
    private JButton pathDist;
    private JButton choose;
    private JPanel algoButtonsPanel;
    private JPanel allAlgo;
    private JLabel algoLabel;
    private JPanel graphButtonsPanel;
    private JLabel currG;
    private DWGraphAlgo algo;



    public MenuFrame(){
        this.setTitle("Graph Algorithms");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(529,219);

        this.algo = new DWGraphAlgo();
        this.allAlgo = new JPanel(new GridLayout(2,1,0,0));
        this.graphButtonsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        this.algoLabel = new JLabel("Algorithms you can run on the graph:");
        this.algoButtonsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        this.currG = new JLabel("please load a graph to start!");
        this.showGraph = new JButton("Show graph");
        this.shortPath = new JButton("Shortest path");
        this.isConnected = new JButton("Is connected");
        this.tsp = new JButton("TSP");
        this.pathDist = new JButton("Shortest path dist");
        this.choose = new JButton("Choose a Json graph file");


        Color color = new Color(0,160,160);
        Font font = new Font("Serif", Font.PLAIN, 20);
        this.algoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.algoLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.currG.setHorizontalAlignment(SwingConstants.CENTER);
        this.currG.setVerticalAlignment(SwingConstants.CENTER);
        this.currG.setFont(new Font("Serif", Font.BOLD, 30));
        this.algoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        this.shortPath.setFont(font);
        this.showGraph.setFont(font);
        this.pathDist.setFont(font);
        this.isConnected.setFont(font);
        this.tsp.setFont(font);
        this.choose.setFont(font);
        this.shortPath.setBackground(color);
        this.showGraph.setBackground(color);
        this.pathDist.setBackground(color);
        this.isConnected.setBackground(color);
        this.tsp.setBackground(color);
        this.choose.setBackground(color);
        this.showGraph.setEnabled(false);
        this.choose.addActionListener(this);
        this.showGraph.addActionListener(this);



        this.algoButtonsPanel.add(shortPath);
        this.algoButtonsPanel.add(isConnected);
        this.algoButtonsPanel.add(tsp);
        this.algoButtonsPanel.add(pathDist);
        this.graphButtonsPanel.add(choose);
        this.graphButtonsPanel.add(showGraph);
        this.allAlgo.add(algoLabel);
        this.allAlgo.add(algoButtonsPanel);
        this.add(currG);
        this.add(graphButtonsPanel);
        this.add(allAlgo);


        this.setSize(550,500);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == choose) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./Data"));
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                String graphPath = fileChooser.getSelectedFile().getAbsolutePath();
                this.algo.load(graphPath);
                String graphName = "";
                for (int i = graphPath.length() - 1; i > 0; i--) {
                    if (graphPath.charAt(i) == '\\') {
                        graphName = graphPath.substring(i + 1);
                        break;
                    }
                }
                this.currG.setText("Your current graph: " + graphName);
                this.showGraph.setEnabled(true);
            }
        }

        if (e.getSource() == showGraph){
            new MainFrame(algo, this);
            this.setVisible(false);
        }


    }

    public static void main(String[] args) {
        MenuFrame frame = new MenuFrame();

    }
}
