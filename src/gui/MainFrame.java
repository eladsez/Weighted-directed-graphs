package gui;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import logicControl.DWGraph;
import logicControl.DWGraphAlgo;
import logicControl.Edge;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    DWGraphAlgo gAlgo;
    DrawPanel panel;

    public MainFrame(DirectedWeightedGraphAlgorithms gAlgo) throws HeadlessException {
        this.gAlgo = (DWGraphAlgo) gAlgo;
        this.panel = new DrawPanel((DWGraph) gAlgo.getGraph());
        this.add(panel);
        this.setSize(500,500);
        this.pack();
        this.setBackground(new Color(0,51,51));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        DWGraphAlgo algo = new DWGraphAlgo();
        algo.load("Data//G1.json");
        MainFrame frame = new MainFrame(algo);
    }
}
