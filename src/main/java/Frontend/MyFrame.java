package Frontend;

import Backend.Edge;
import Backend.Luxembourg.LuxembourgGraph;
import Backend.Node;
import Backend.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener, MouseListener {
    JRadioButton directed = new JRadioButton("Directed");
    JRadioButton undirected = new JRadioButton("Undirected");
    JRadioButton luxembourg=new JRadioButton("Luxembourg");
    JRadioButton wDirected = new JRadioButton("Directed with weight");
    JRadioButton wUndirected = new JRadioButton("Undirected with weight");
    JRadioButton ShowGraph = new JRadioButton("Show"+" "+ "Graph");
    JRadioButton dfs = new JRadioButton("DFS");
    JRadioButton kosaraju = new JRadioButton("Kosaraju's"+" "+ "Algorithm");
    JRadioButton topological = new JRadioButton("Topological"+ " "+"Sort");
    JRadioButton arborescence = new JRadioButton("Arborescence"+ " "+"Check");
    JRadioButton root = new JRadioButton("Root Node"+ " "+"Check");
    JRadioButton primMst=new JRadioButton("Prim's MST");
    JRadioButton kruskal=new JRadioButton("Kruskal's MST");
    JRadioButton boruvka=new JRadioButton("Boruvka's MST");
    JRadioButton ford=new JRadioButton("Ford Fulkerson Max Flow");
    JRadioButton minBellman=new JRadioButton("Bellman-Ford Min Flow");
    JRadioButton dijkstra=new JRadioButton("Dijkstra");
    JRadioButton bellman=new JRadioButton("Bellman-Ford");
    ArrayList<JRadioButton> buttons=new ArrayList<>();
    ArrayList<JRadioButton> graphsButtons=new ArrayList<>();

    Slider wSlider=new Slider("Weight: ");
    Slider cSlider=new Slider("Capacity: ");

    public static Integer m_typeOfGraph=0;
    public static MainPanel mainPanel=new MainPanel();


    MyFrame() {
        this.setTitle("Algoritmica Grafurilor");
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setResizable(false);
        this.setSize(1200, 800);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground((new Color(88, 168, 201)));
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        Label label=new Label("Select the type of graph:");
        graphsButtons.add(undirected);
        graphsButtons.add(directed);
        graphsButtons.add(wUndirected);
        graphsButtons.add(wDirected);
        graphsButtons.add(luxembourg);
        this.AddGraphButtonPanel(graphsButtons,label);
        Label newLabel=new Label("Select the algorithm: ");
        buttons.add(ShowGraph);
        buttons.add(dfs);
        buttons.add(kosaraju);
        buttons.add(topological);
        buttons.add(arborescence);
        buttons.add(root);
        buttons.add(primMst);
        buttons.add(kruskal);
        buttons.add(boruvka);
        buttons.add(ford);
        buttons.add(dijkstra);
        buttons.add(bellman);
        buttons.add(minBellman);
        this.AddTextButtonPanel(buttons,newLabel);
        this.add(mainPanel,BorderLayout.CENTER);
    }

    public void AddGraphButtonPanel(ArrayList<JRadioButton> graphsButtons, Label label) {

        ButtonGroup group = new ButtonGroup();
        Panel firstPanel=new Panel();
        Panel secondPanel=new Panel();
        firstPanel.add(label);
        firstPanel.setPreferredSize(new Dimension(100,20));

        for(JRadioButton button:graphsButtons)
        {
            button.setOpaque(false);
            Font buttonFont = new Font("Helvetica", Font.BOLD, 15);
            button.setFont(buttonFont);
            group.add(button);
            button.addActionListener(this);
            secondPanel.add(button);
        }

        secondPanel.setPreferredSize(new Dimension(100,20));
        this.add(firstPanel);
        this.add(secondPanel);
    }

    public void AddTextButtonPanel(ArrayList<JRadioButton> buttons, Label label) {
        ButtonGroup group = new ButtonGroup();

        Panel firstPanel=new Panel();
        Panel secondPanel=new Panel();
        firstPanel.add(label);
        firstPanel.setPreferredSize(new Dimension(100,20));

        for(JRadioButton button:buttons){
            button.setOpaque(false);
            Font buttonFont = new Font("Helvetica", Font.BOLD, 15);
            button.setFont(buttonFont);
            group.add(button);
            button.addActionListener(this);
            button.setVisible(false);
            secondPanel.add(button);
        }
        wSlider.setOpaque(false);
        wSlider.setVisible(false);
        cSlider.setOpaque(false);
        cSlider.setVisible(false);
        secondPanel.add(wSlider);
        secondPanel.add(cSlider);
        secondPanel.setPreferredSize(new Dimension(100,45));
        this.add(firstPanel);
        this.add(secondPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (m_typeOfGraph == 0) {
            if (e.getSource() == undirected) {
                System.out.println("undirected");
                m_typeOfGraph = 1;
                directed.setVisible(false);
                wDirected.setVisible(false);
                wUndirected.setVisible(false);
                luxembourg.setVisible(false);
                ShowGraph.setVisible(true);
                dfs.setVisible(true);
                kosaraju.setVisible(false);
                topological.setVisible(false);
                arborescence.setVisible(false);
                root.setVisible(false);
            } else if (e.getSource() == directed) {
                System.out.println("directed");
                m_typeOfGraph = 2;
                undirected.setVisible(false);
                wDirected.setVisible(false);
                wUndirected.setVisible(false);
                luxembourg.setVisible(false);
                ShowGraph.setVisible(true);
                dfs.setVisible(true);
                kosaraju.setVisible(true);
                topological.setVisible(true);
                arborescence.setVisible(true);
                root.setVisible(true);
            } else if (e.getSource() == wUndirected) {
                System.out.println("Undirected with weight");
                m_typeOfGraph = 3;
                undirected.setVisible(false);
                directed.setVisible(false);
                wDirected.setVisible(false);
                luxembourg.setVisible(false);
                wSlider.setVisible(true);
                primMst.setVisible(true);
                kruskal.setVisible(true);
                boruvka.setVisible(true);
            } else if (e.getSource() == wDirected) {
                System.out.println("Undirected with weight");
                m_typeOfGraph = 4;
                undirected.setVisible(false);
                directed.setVisible(false);
                wUndirected.setVisible(false);
                luxembourg.setVisible(false);
                cSlider.setVisible(true);
                ford.setVisible(true);
                minBellman.setVisible(true);
            }
            else if (e.getSource() == luxembourg) {
                LuxembourgGraph luxembourgGraph=new LuxembourgGraph();
                mainPanel.repaint();
                m_typeOfGraph = 5;
                dijkstra.setVisible(true);
                bellman.setVisible(true);

            }
        } else if (e.getSource() == ShowGraph && ShowGraph.isSelected()) {
            if (!MainPanel.nodeArray.isEmpty()) {
                Graph graph = new Graph();
                graph.printGraph();
            } else JOptionPane.showMessageDialog(null, "You have to draw the graph before using an algorithm.",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
        } else if (e.getSource() == dfs && dfs.isSelected()) {
            if (!MainPanel.nodeArray.isEmpty()) {
                Graph graph = new Graph();
                graph.DFS();
                JOptionPane.showMessageDialog(null, graph.dfsString,
                        "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(null, "You have to draw the graph before using an algorithm.",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
        } else if (e.getSource() == kosaraju && kosaraju.isSelected()) {
            if (!MainPanel.nodeArray.isEmpty()) {
                Graph graph = new Graph();
                List<List<Node>> components = graph.findHardComponentsConnection();
                for (List<Node> component : components) {
                    for (Node node :
                            component) {
                        System.out.print(node.m_index + " ");
                    }
                    System.out.println();
                }
            } else JOptionPane.showMessageDialog(null, "You have to draw the graph before using an algorithm.",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
        } else if (e.getSource() == topological && topological.isSelected()) {
            if (!MainPanel.nodeArray.isEmpty()) {
                Graph graph = new Graph();
                if (!graph.isCyclic(false)) {
                    List<Integer> list = graph.topologicalSort();
                    for (Integer number :
                            list) {
                        System.out.print(number + " ");
                    }
                } else {
                    System.out.println("The given graph is cyclic. Topological sorting cannot be applied.");
                    JOptionPane.showMessageDialog(null, "Topological sorting cannot be applied.",
                            "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
                }
            } else JOptionPane.showMessageDialog(null, "You have to draw the graph before using an algorithm.",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
        } else if (e.getSource() == arborescence && arborescence.isSelected()) {
            if (!MainPanel.nodeArray.isEmpty()) {
                Graph graph = new Graph();
                if (graph.IsArborescence()) {
                    System.out.println("Graful este arborescent.");
                    JOptionPane.showMessageDialog(null, "Graph is arborescence.",
                            "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Graph is not arborescence.",
                            "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
                }
            } else JOptionPane.showMessageDialog(null, "You have to draw the graph before using an algorithm.",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
        } else if (e.getSource() == root && root.isSelected()) {
            if (!MainPanel.nodeArray.isEmpty()) {
                Graph graph = new Graph();
                Node node = graph.FindRoot();
                if (node != null) {
                    System.out.println("Radacina este " + node.m_index);
                    node.colour = Color.RED;
                    mainPanel.repaint();
                    JOptionPane.showMessageDialog(null, "The node with index " + node.m_index + " is the root.",
                            "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
                } else JOptionPane.showMessageDialog(null, "There is no root.",
                        "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == primMst && primMst.isSelected()) {
            if (!MainPanel.edgeArray.isEmpty()) {
                Graph graph = new Graph();
                graph = graph.primMst();
                mainPanel.repaint();
                if (graph.MstCost != -1) {
                    JOptionPane.showMessageDialog(null, "MST Cost is " + graph.MstCost,
                            "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "MST CAN'T BE APPLIED",
                        "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == kruskal && kruskal.isSelected()) {
            if (!MainPanel.edgeArray.isEmpty()) {
                Graph graph = new Graph();
                graph = graph.kruskal(MainPanel.edgeArray, MainPanel.nodeArray.size());
                mainPanel.repaint();
                if (graph.MstCost != -1) {
                    JOptionPane.showMessageDialog(null, "MST Cost is " + graph.MstCost,
                            "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "MST CAN'T BE APPLIED",
                        "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == boruvka && boruvka.isSelected()) {
            if (!MainPanel.edgeArray.isEmpty()) {
                Graph graph = new Graph();
                graph = graph.boruvka(MainPanel.edgeArray, MainPanel.nodeArray.size());
                mainPanel.repaint();
                if (graph.MstCost != -1) {
                    JOptionPane.showMessageDialog(null, "MST Cost is " + graph.MstCost,
                            "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "MST CAN'T BE APPLIED",
                        "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource() == ford && ford.isSelected()) {
            for (Edge edge : MainPanel.edgeArray) {
                edge.m_usedCapacity = 0;
            }
            for (Edge edge : MainPanel.reversedEdgeArray) {
                edge.m_usedCapacity = 0;
            }
            Graph graph = new Graph();
            graph.initializeComponents();
            //Se verifica prin componente indirect si cazul in care nu exista nicun edge.
            if (graph.components.size() == 1) {
                Graph.FordFulkersonDFSSolver solver = new Graph.FordFulkersonDFSSolver(MainPanel.nodeArray.size(), 1, MainPanel.nodeArray.size());
                solver.execute();
                System.out.println("Flow-ul maxim posibil este " + solver.getMaxFlow());
                JOptionPane.showMessageDialog(null, "Maximul flow for the given graph is " +
                                solver.getMaxFlow() + ".",
                        "Algoritmica Grafurilor", JOptionPane.INFORMATION_MESSAGE);
                mainPanel.repaint();
            } else JOptionPane.showMessageDialog(null, "Ford Fulkerson can't be applied. " +
                            "There are more than one components in graph.",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
        }else if(e.getSource()==minBellman&&minBellman.isSelected())
        {
            Graph graph=new Graph();
            graph.initializeComponents();
            if (graph.components.size() == 1) {
              //  graph.findMinimumFlow(1,MainPanel.nodeArray.size());
            }
        }

        else if ( e.getSource() == dijkstra && dijkstra.isSelected())
        {
            if (MainPanel.nodeStart != -1 && MainPanel.nodeEnd != -1) {
                MainPanel.luxembourgGraph.DijkstraShortestPath(MainPanel.nodeStart, MainPanel.nodeEnd);
                repaint();
            }
        }
        else if(e.getSource()==bellman&&bellman.isSelected()) {
            if (MainPanel.nodeStart != -1 && MainPanel.nodeEnd != -1) {
                MainPanel.luxembourgGraph.BellmanFordShortestPath(MainPanel.nodeStart, MainPanel.nodeEnd);
                repaint();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
}