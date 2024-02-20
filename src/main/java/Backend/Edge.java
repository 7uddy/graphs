package Backend;


import Frontend.MyFrame;

import java.awt.*;

public class Edge implements Comparable<Edge>{
   public Node m_startNode=null,m_endNode=null;
   public Integer m_weight;
   public Integer m_usedCapacity=0;
   public Edge residual;

    public Edge(Node startNode, Node endNode) {
        this(startNode, endNode, 0); // Apelează constructorul principal cu valoare implicită pentru weight
    }
    public Edge(Node startNode, Node endNode,Integer weight)
    {
        m_startNode=startNode;
        m_endNode=endNode;
        m_weight=weight;
    }
    public Integer getWeight()
    {
        return m_weight;
    }

    public boolean isResidual(){
        return m_weight==0;
    }

    public int remainingCapacity(){
        return m_weight-m_usedCapacity;
    }

    public void augment(int bottleneck)
    {
        m_usedCapacity+=bottleneck;
        residual.m_usedCapacity-=bottleneck;
    }
    public void drawEdge(Graphics2D g) {
            g.setColor(Color.RED);
            g.drawLine(this.m_startNode.m_xCord, this.m_startNode.m_yCord, this.m_endNode.m_xCord, this.m_endNode.m_yCord);
        }

    @Override
    public int compareTo(Edge other) {
        return m_weight-other.m_weight;
    }
}

