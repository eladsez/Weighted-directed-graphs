package gui;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {


    public DrawPanel() {
        this.setPreferredSize(new Dimension(500,500));
    }

    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        Graphics2D g2 = (Graphics2D) g;
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
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(x1, y1, x2, y2);
        g2.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void paintComponent(Graphics g){
        drawArrowLine(g, 100,100,250,250,7,5);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        g2.fillOval(250,250,10,10);
    }
}
