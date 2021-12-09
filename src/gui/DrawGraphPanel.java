package gui;

import api.EdgeData;
import logicControl.DWGraph;
import logicControl.Edge;
import logicControl.Node;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class DrawGraphPanel extends JPanel {

    private Dimension screenSize;
    private DWGraph graph;
    private HashMap<Integer, Integer> nodeXpos;
    private HashMap<Integer, Integer> nodeYpos;
    private List colored;
    private Node center;

    public DrawGraphPanel(DWGraph g, List colored, Node center) {
        this.setLayout(null);
        this.graph = g;
        this.colored = colored;
        this.center = center;
        this.nodeXpos = new HashMap<>(this.graph.nodeSize());
        this.nodeYpos = new HashMap<>(this.graph.nodeSize());
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(screenSize);
    }

    @Override
    public void paintComponent(Graphics g){
        updateArr();
        drawEdges(g);
        drawNodes(g);

    }

    public DWGraph getGraph() {
        return this.graph;
    }

    public void setGraph(DWGraph graph) {
        this.graph = graph;
    }

    private void updateArr() {
        Iterator iter = this.graph.nodeIter();
        Node curr;
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        double x,y;
        while (iter.hasNext()){
            curr = (Node) iter.next();
            x = curr.getPos().x();
            y = curr.getPos().y();
            minX = Math.min(minX,x);
            minY = Math.min(minY,y);
            maxX = Math.max(maxX,x);
            maxY = Math.max(maxY,y);
        }
        double uintX = this.screenSize.width/(maxX - minX) * 0.9;
        double unitY = this.screenSize.height/(maxY - minY) * 0.8;

        iter = this.graph.nodeIter();

        while (iter.hasNext()){
            curr = (Node) iter.next();
            x = (curr.getPos().x() - minX) * uintX;
            y = (curr.getPos().y() - minY) * unitY;

            this.nodeXpos.put(curr.getKey(), (int) x);
            this.nodeYpos.put(curr.getKey(), (int) y);

        }
    }

    private void drawNodes(Graphics g){
        Iterator iter = this.graph.nodeIter();
        Graphics2D g2 = (Graphics2D) g;
        Node curr;
        g2.setFont(new Font("Serif", Font.CENTER_BASELINE, 12));
        while (iter.hasNext()){
            curr = (Node) iter.next();
            if (curr == center){
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Serif", Font.CENTER_BASELINE, 18));
                g2.drawString("Center",this.nodeXpos.get(curr.getKey())-7,this.nodeYpos.get(curr.getKey())-10 );
                g2.setStroke(new BasicStroke(7));
                g2.setColor(Color.WHITE);
                g2.drawOval(this.nodeXpos.get(curr.getKey())-1,this.nodeYpos.get(curr.getKey())-1,20,20);
                g2.setFont(new Font("Serif", Font.CENTER_BASELINE, 12));
            }
            g2.setColor(Color.RED);
            g2.fillOval(this.nodeXpos.get(curr.getKey()),this.nodeYpos.get(curr.getKey()),19,19);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawString(Integer.toString(curr.getKey()), this.nodeXpos.get(curr.getKey()) + 5, this.nodeYpos.get(curr.getKey()) + 14);
        }
    }

    private void drawEdges(Graphics g){
        List<EdgeData> coloredEdges = new Vector<>();
        if (this.colored != null) {
            Iterator listIter = this.colored.iterator();
            Node currNode, nextNode = null;
            if (listIter.hasNext()) {
                currNode = (Node) listIter.next();
                while (listIter.hasNext()) {
                    nextNode = (Node) listIter.next();
                    coloredEdges.add(this.graph.getEdge(currNode.getKey(), nextNode.getKey()));
                    if (listIter.hasNext())
                        currNode = nextNode;
                }
            }
        }
        Graphics2D g2 = (Graphics2D) g;
        Iterator iter = this.graph.edgeIter();
        Edge curr;
        float xAvg, yAvg;
        while (iter.hasNext()){
            curr = (Edge) iter.next();
            if (!coloredEdges.contains(curr)) {
                drawArrowLine(g2, this.nodeXpos.get(curr.getSrc()) + 10, this.nodeYpos.get(curr.getSrc()) + 10, this.nodeXpos.get(curr.getDest()) + 10
                        , this.nodeYpos.get(curr.getDest()) + 10, 25, 5, true);
            }
        }
        coloredEdges.forEach(edgeData -> {
            drawArrowLine(g2, this.nodeXpos.get(edgeData.getSrc()) + 10, this.nodeYpos.get(edgeData.getSrc()) + 10, this.nodeXpos.get(edgeData.getDest()) + 10
                    , this.nodeYpos.get(edgeData.getDest()) + 10, 25, 5, true);
        });
    }

    private void drawArrowLine(Graphics2D g2, int x1, int y1, int x2, int y2, int d, int h,boolean curvFlag) {
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

//        if(curvFlag){
//
//            // create new QuadCurve2D.Float
//            QuadCurve2D q = new QuadCurve2D.Double();
//            // draw QuadCurve2D.Float with set coordinates
//            double controlX, controlY;
//            LineFunction line = new LineFunction(-1/((y1-y2)/(x1-x2)));
//            line.findD((x1 + x2)/2, (y1 + y2)/2);
//            controlX = 10;
//            controlY = line.f(10);
//            q.setCurve(x1, y1, controlX, controlY, x2, y2);
//            g2.draw(q);
//            g2.fillPolygon(xpoints, ypoints, 3);
//            return;
//        }
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawLine(x1, y1, x2, y2);
        g2.fillPolygon(xpoints, ypoints, 3);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.WHITE);
        g2.drawPolygon(xpoints, ypoints, 3);
    }
    class LineFunction{
        int m, d;
        public LineFunction(int m) {
            this.m = m;
        }
        public void findD(int x, int y){
            this.d = y - this.m * x;
        }
        public int f(int x){
            return m*x + d;
        }
    }
}
