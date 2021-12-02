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
    DrawPanel panel = new DrawPanel();

    public MainFrame(DirectedWeightedGraphAlgorithms gAlgo) throws HeadlessException {
        this.gAlgo = (DWGraphAlgo) gAlgo;

        this.add(panel);
        this.setSize(500,500);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame(null);
    }
}
