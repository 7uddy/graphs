package Frontend;

import Backend.Edge;
import Backend.Graph;
import Backend.Luxembourg.LuxembourgEdge;
import Backend.Luxembourg.LuxembourgGraph;
import Backend.Luxembourg.LuxembourgNode;
import Backend.Node;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class MainPanel extends JPanel implements MouseListener {
    public int nodeNumber=1;
    public static ArrayList<Node> nodeArray=new ArrayList<>();
    public static ArrayList<Edge> edgeArray=new ArrayList<>();
    public static ArrayList<Edge> reversedEdgeArray=new ArrayList<>();

    public static Node m_isStart=null,m_isEnd=null;
    private static boolean selectionMade = false;
    public static int nodeStart;
    public static int nodeEnd;
    public static LuxembourgGraph luxembourgGraph;

    MainPanel(){
        //205, 255, 249
        this.setBackground(new Color(205, 255, 249));
        this.setPreferredSize(new Dimension(100,600));
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (MyFrame.m_typeOfGraph == 1 || MyFrame.m_typeOfGraph == 2 ||MyFrame.m_typeOfGraph == 3||MyFrame.m_typeOfGraph == 4) {

            Integer clickedX = e.getX();
            Integer clickedY = e.getY();
            Node closestNode = NodeRadar(clickedX, clickedY);

            if (closestNode == null) {
                if(m_isStart!=null)
                {
                    m_isStart=null;
                    repaint();
                }
                else this.addNode(e.getX(), e.getY());
            }
            else if (m_isStart == null) {
                m_isStart = closestNode;
                repaint();
            }
            else if (m_isStart != closestNode) {
                m_isEnd = closestNode;
                for (Edge edge :
                        edgeArray) {

                    if (edge.m_startNode == m_isStart && edge.m_endNode == m_isEnd) {
                        System.out.println("This edge already exists");
                        JOptionPane.showMessageDialog(null,"This edge already exists.",
                                "Algoritmica Grafurilor",JOptionPane.WARNING_MESSAGE);
                        m_isStart = null;
                        m_isEnd = null;
                        repaint();
                        return;
                    }
                }
                if(MyFrame.m_typeOfGraph==1) AddEdge(m_isStart, m_isEnd,1);
                else if(MyFrame.m_typeOfGraph==2) AddEdge(m_isStart,m_isEnd,2);
                else if(MyFrame.m_typeOfGraph==3) AddEdge(m_isStart,m_isEnd,3,Slider.sliderValue);
                else if(MyFrame.m_typeOfGraph==4) AddEdge(m_isStart,m_isEnd,4,Slider.sliderValue);
                System.out.println("Edge de la " + m_isStart.m_index + " la " + m_isEnd.m_index);
                m_isStart = null;
                m_isEnd = null;
            }


        }
        else if(MyFrame.m_typeOfGraph==5)
        {
            if (!selectionMade) {
                selectionMade = true;
                Point pointStart = e.getPoint();
                nodeStart = luxembourgGraph.SearchNodeWithCoordinates(pointStart.x, pointStart.y);
                System.out.println("Node Start ID= " + nodeStart);
            } else {
                selectionMade = false;
                Point pointEnd = e.getPoint();
                nodeEnd = luxembourgGraph.SearchNodeWithCoordinates(pointEnd.x, pointEnd.y);
                repaint();
            }

        }
        else{
            JOptionPane.showMessageDialog(null,"You have to select the type of graph before drawing.",
                    "Algoritmica Grafurilor",JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    private void addNode(int x,int y){
        Node node=new Node(x,y,this.nodeNumber);
        nodeArray.add(node);
        ++nodeNumber;
        this.repaint();
    }
    private void AddEdge(Node x,Node y,int typeOfGraph)
    {
        AddEdge(x,y,typeOfGraph,0);
    }
    private void AddEdge(Node x,Node y,int typeOfGraph,int weight) {
        if (typeOfGraph == 1) {
            Edge firstEdge = new Edge(x, y);
            Edge secondEdge=new Edge(y,x);
            edgeArray.add(firstEdge);
            edgeArray.add(secondEdge);
            this.repaint();
        }
        else if (typeOfGraph == 2) {
            Edge edge = new Edge(x, y);
            edgeArray.add(edge);
            this.repaint();
        }
        if (typeOfGraph == 3) {
            Edge firstEdge = new Edge(x, y,weight);
            Edge secondEdge=new Edge(y,x,weight);
            edgeArray.add(firstEdge);
            edgeArray.add(secondEdge);
            this.repaint();
        }
        else if (typeOfGraph == 4) {
            Edge edge = new Edge(x, y,weight);
            Edge secondEdge=new Edge(y,x,0);
            edge.residual=secondEdge;
            secondEdge.residual=edge;
            edgeArray.add(edge);
            reversedEdgeArray.add(secondEdge);
            this.repaint();
        }
    }
    protected void paintComponent(Graphics g) {
        if (MyFrame.m_typeOfGraph != 5) {
            Graphics2D g2D = (Graphics2D) g;
            super.paintComponent(g2D);
            Color colour = null;
            int node_diam = 30;
            for (Node node : nodeArray) {

                node.DrawNode(g2D, node_diam);
                node.colour = Color.lightGray;
            }
            for (Edge edge :
                    edgeArray) {
                g2D.setColor(new Color(19, 21, 21));
                int startX = edge.m_startNode.m_xCord;
                int startY = edge.m_startNode.m_yCord;
                int endX = edge.m_endNode.m_xCord;
                int endY = edge.m_endNode.m_yCord;
                if (Math.abs(startX - endX) > 50) {
                    if (startX < endX) //nodul de end este la dreapta
                    {
                        startX += 10;
                        endX -= 10;
                    } else {
                        startX -= 10;
                        endX += 10;
                    }
                }
                if (Math.abs(startY - endY) > 50) {
                    if (startY < endY) //nodul de end este mai sus
                    {
                        startY += 10;
                        endY -= 10;

                    } else {
                        startY -= 10;
                        endY += 10;
                    }
                }

                g2D.setStroke(new BasicStroke(2.0f));
                g2D.drawLine(startX, startY, endX, endY);
                if (MyFrame.m_typeOfGraph == 2 || MyFrame.m_typeOfGraph == 4) {
                    DrawArrow(g2D, 15, startX, startY, endX, endY);
                }
                if (MyFrame.m_typeOfGraph == 3 || MyFrame.m_typeOfGraph == 4) {
                    DrawWeight(g2D, edge.m_weight, edge.m_usedCapacity, startX, startY, endX, endY);
                }
            }
            if (m_isStart != null) {
                m_isStart.colour = new Color(37, 179, 81);
                m_isStart.DrawNode(g2D, node_diam);
                m_isStart.colour = Color.lightGray;
            } else if (m_isEnd != null) {
                m_isEnd.colour = Color.lightGray;
                m_isStart.DrawNode(g2D, node_diam);
            }
        } else {
            super.paintComponent(g);
            for (LuxembourgNode node : luxembourgGraph.nodeList) {
                node.drawNode(g);
            }
            for (LuxembourgEdge arc : luxembourgGraph.arcList) {
                arc.drawArc(g);
            }
        }
    }

    public static Color generateRandomColor() {
        Random rand = new Random();
        int red = rand.nextInt(256);
        int green = rand.nextInt(256);
        int blue = rand.nextInt(256);

        return new Color(red, green, blue);
    }

    private Node NodeRadar(Integer clickedX,Integer clickedY)
    {
        for(Node node:nodeArray)
        {
            double distance = Math.sqrt(Math.pow(node.m_xCord - clickedX, 2) + Math.pow(node.m_yCord - clickedY, 2));
            if(distance<=40)
                return node;

        }
        return null;
    }
    public void DrawArrow(Graphics g, int arrowSize ,int startX,int startY,int endX,int endY) {

        g.setColor(Color.BLACK);

        // Calculăm unghiul dintre linia start-end și am_xCorda m_xCord
        double angle = Math.atan2(endY - startY, endX - startX);

        // Desenăm vârful săgeții
        int arrowX = (int) (endX - arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY = (int) (endY - arrowSize * Math.sin(angle - Math.PI / 6));
        g.drawLine(endX, endY, arrowX, arrowY);

        // Desenăm celălalt vârf al săgeții
        arrowX = (int) (endX - arrowSize * Math.cos(angle + Math.PI / 6));
        arrowY = (int) (endY - arrowSize * Math.sin(angle + Math.PI / 6));
        g.drawLine(endX, endY, arrowX, arrowY);

    }
    public void DrawWeight(Graphics g, int weight,int capacity, int startX, int startY, int endX, int endY) {
        g.setColor(Color.BLACK);

        // Calculăm mijlocul distanței dintre puncte
        int midX = (startX + endX) / 2;
        int midY = (startY + endY) / 2;
        Font originalFont = g.getFont();
        Font biggerFont = originalFont.deriveFont(originalFont.getSize() * 1.25F);
        g.setFont(biggerFont);
        // Desenăm numărul în mijlocul distanței
        if(MyFrame.m_typeOfGraph==3) g.drawString(String.valueOf(weight), midX, midY);
        else g.drawString(String.valueOf(capacity)+"/"+String.valueOf(weight), midX, midY);
        g.setFont(originalFont);
    }


}

