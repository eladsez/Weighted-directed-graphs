package gui;

import logicControl.DWGraph;
import logicControl.Edge;
import logicControl.Node;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class DrawPanel extends JPanel {

    private DWGraph graph;
    private int[] nodeXpos;
    private int[] nodeYpos;

    public DrawPanel(DWGraph g) {
        this.graph = g;
        this.nodeXpos = new int[g.nodeSize()];
        this.nodeYpos = new int[g.nodeSize()];
        this.setPreferredSize(new Dimension(500,500));
    }

    @Override
    public void paintComponent(Graphics g){
//        drawArrowLine(g, 100,100,250,250,7,5);
        updateArr();
        drawEdges(g);
        drawNodes(g);
    }

    private void updateArr() {
        Iterator iter = this.graph.nodeIter();
        Node curr;
        int x, y;
        while (iter.hasNext()) {
            curr = (Node) iter.next();
            x = (int) ((curr.getPos().x() * 100000) % 1000);
            y = (int) ((curr.getPos().y() * 100000) % 1000);
            this.nodeXpos[curr.getKey()] = x;
            this.nodeYpos[curr.getKey()] = y;
        }
    }

    private void drawNodes(Graphics g){
        Iterator iter = this.graph.nodeIter();
        Graphics2D g2 = (Graphics2D) g;
        Node curr;
        int x, y;
        while (iter.hasNext()){
            curr = (Node) iter.next();
//            g2.setColor(new Color(160,160,160));
//            g2.setStroke(new BasicStroke(3));
//            g2.setFont(new Font("Microsoft YaHei", Font.CENTER_BASELINE, 10));
//            g2.drawString(Integer.toString(curr.getKey()), this.nodeXpos[curr.getKey()], this.nodeYpos[curr.getKey()] - 2);
            g2.setColor(Color.BLACK);
            g2.fillOval(this.nodeXpos[curr.getKey()],this.nodeYpos[curr.getKey()],10,10);
        }
    }

    private void drawEdges(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Iterator iter = this.graph.edgeIter();
        Edge curr;
        g.setColor(new Color(0,0,0));
        while (iter.hasNext()){
            curr = (Edge) iter.next();
            drawArrowLine(g2,this.nodeXpos[curr.getSrc()], this.nodeYpos[curr.getSrc()], this.nodeXpos[curr.getDest()]
            , this.nodeYpos[curr.getDest()], 10, 7);
        }
    }

    private void drawArrowLine(Graphics2D g2, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2 + 2, (int) xm, (int) xn};
        int[] ypoints = {y2 + 2, (int) ym, (int) yn};
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawLine(x1, y1, x2, y2);
        g2.setColor(new Color(204,229,255));
        g2.fillPolygon(xpoints, ypoints, 3);
    }
}
