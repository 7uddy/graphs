package Backend.Luxembourg;

import java.awt.Color;
import java.awt.Graphics;

public class LuxembourgEdge implements Comparable {
    private final int idStart;
    private int idEnd;
    private final int cost;
    private Color color;

    public LuxembourgEdge(int idStart, int idEnd, int cost, Color color) {
        this.idStart = idStart;
        this.idEnd = idEnd;
        this.cost = cost;
        this.color = color;
    }

    public int getIdStart() {
        return this.idStart;
    }

    public int getIdEnd() {
        return this.idEnd;
    }

    public void setIdEnd(int idEnd) {
        this.idEnd = idEnd;
    }

    public int getCost() {
        return this.cost;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int compareTo(Object o) {
        LuxembourgEdge other = (LuxembourgEdge)o;
        return Integer.compare(this.cost, other.cost);
    }

    public void drawArc(Graphics g) {
        g.setColor(this.color);
        g.drawLine(((LuxembourgNode)LuxembourgGraph.nodeList.get(this.idStart)).getLatitude(),
                ((LuxembourgNode)LuxembourgGraph.nodeList.get(this.idStart)).getLongitude(),
                ((LuxembourgNode)LuxembourgGraph.nodeList.get(this.idEnd)).getLatitude(),
                ((LuxembourgNode)LuxembourgGraph.nodeList.get(this.idEnd)).getLongitude());
    }
}
