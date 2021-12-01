package gui;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import logicControl.DWGraph;
import logicControl.DWGraphAlgo;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    DWGraphAlgo gAlgo;
    EdgesLine edges;

    public MainFrame(DirectedWeightedGraphAlgorithms gAlgo) throws HeadlessException {
        this.gAlgo = (DWGraphAlgo) gAlgo;
        this.edges = new EdgesLine();
        this.add(edges);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        DWGraphAlgo algo = new DWGraphAlgo();
        algo.init(null);
        algo.load("Data//G1.json");
        MainFrame frame = new MainFrame(algo);
    }
}
