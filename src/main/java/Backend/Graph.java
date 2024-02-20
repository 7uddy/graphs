package Backend;

import Backend.Luxembourg.LuxembourgEdge;
import Backend.Luxembourg.Pair;
import Frontend.MainPanel;
import Frontend.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.min;

public class Graph {
    public List<List<Object>> graph = new ArrayList<>();
    public String dfsString;
    public List<List<Node>> components = new ArrayList<>();
    public int MstCost=-1;

    public Graph() {
        List<Node> auxNode = MainPanel.nodeArray;

        for (Node node : auxNode) {
            List<Object> nodeAndEdges = new ArrayList<>();

            nodeAndEdges.add(node);

            List<Edge> auxEdge = new ArrayList<>();

            for (Edge edge : MainPanel.edgeArray) {
                if (edge.m_startNode == node) {
                    auxEdge.add(edge);
                }
            }
            if(MyFrame.m_typeOfGraph==4)
            {
                for(Edge edge:MainPanel.reversedEdgeArray){
                    if(edge.m_startNode==node){
                        auxEdge.add(edge);
                    }
                }
            }

            nodeAndEdges.add(auxEdge);
            graph.add(nodeAndEdges);
        }
        initializeComponents();
    }

    public void initializeComponents() {
        boolean[] visited = new boolean[MainPanel.nodeArray.size()];
        components.clear();

        Node root = FindRoot();
        if (root != null && !visited[root.m_index - 1]) {
            List<Node> toAddNodes = new ArrayList<>();
            DFSUtil(root, visited, toAddNodes,false);
            components.add(new ArrayList<>(toAddNodes));
        }

        for (List<Object> nodeAndEdges : graph) {
            Node node = (Node) nodeAndEdges.get(0);
            if (!visited[node.m_index - 1]) {
                List<Node> toAddNodes = new ArrayList<>();
                DFSUtil(node, visited, toAddNodes,false);
                components.add(new ArrayList<>(toAddNodes));
            }
        }
    }
    private int NumberOfEdges(){
        return MainPanel.edgeArray.size();
    }
    private int NumberOfNodes(){
        return MainPanel.nodeArray.size();
    }
    public void printGraph() {
        for (List<Object> nodeAndEdges : graph) {
            Node node = (Node) nodeAndEdges.get(0);
            List<Edge> edges = (List<Edge>) nodeAndEdges.get(1);

            System.out.println("Node: " + node.m_index);

            for (Edge edge : edges) {
                System.out.println("   Edge: " + edge.m_startNode.m_index + " - " + edge.m_endNode.m_index);
            }

            System.out.println();
        }
    }

    public void DFS() {
        components.clear();
        boolean[] visited = new boolean[MainPanel.nodeArray.size()];
        dfsString = "";

        Node root = FindRoot();
        if (root != null && !visited[root.m_index - 1]) {
            List<Node> toAddNodes = new ArrayList<>();
            DFSUtil(root, visited, toAddNodes,true);
            components.add(new ArrayList<>(toAddNodes));
            toAddNodes.clear();
        }

        for (List<Object> nodeAndEdges : graph) {
            Node node = (Node) nodeAndEdges.get(0);
            if (!visited[node.m_index - 1]) {
                List<Node> toAddNodes = new ArrayList<>();
                DFSUtil(node, visited, toAddNodes,true);
                components.add(new ArrayList<>(toAddNodes));
                toAddNodes.clear();
            }
        }

        System.out.println("Numarul de componente conexe: " + components.size());
        ComponentsRepaint();
    }
    public void ComponentsRepaint(){
        for (List<Node> objects :
                components) {
            Color colour=generateRandomColor();
            for (Node node :
                    objects) {
                node.colour=colour;
            }
        }
        MyFrame.mainPanel.repaint();
    }
    public static Color generateRandomColor() {
        Random rand = new Random();
        int red = rand.nextInt(256);
        int green = rand.nextInt(256);
        int blue = rand.nextInt(256);

        return new Color(red, green, blue);
    }

    private void DFSUtil(Node node, boolean[] visited, List<Node> toAddNodes,boolean wInfo) {
        int index = node.m_index - 1;
        visited[index] = true;
        if(wInfo) System.out.println("Visiting Node: " + node.m_index);
        dfsString = dfsString + " " + node.m_index;
        toAddNodes.add(node);
        List<Edge> edges = (List<Edge>) graph.get(index).get(1);
        for (Edge edge : edges) {
            Node neighbor = edge.m_endNode;
            if(wInfo) System.out.println("   Visiting Edge: " + edge.m_startNode.m_index + " - " + edge.m_endNode.m_index);
            if (!visited[neighbor.m_index - 1]) {
                DFSUtil(neighbor, visited, toAddNodes,wInfo);
            }
        }
    }

    public void dfs(Node startNode, boolean[] visited, Stack<Node> stack) {
        Stack<Node> stackDfs = new Stack<>();
        stackDfs.push(startNode);
        while (!stackDfs.isEmpty()) {
            Node currentNode = stackDfs.pop();
            int v = currentNode.m_index - 1;
            if (!visited[v]) {
                visited[v] = true;
                stack.push(currentNode);
                List<Edge> edges = (List<Edge>) graph.get(v).get(1);
                for (Edge edge : edges) {
                    Node neighbour = edge.m_endNode;
                    if (!visited[neighbour.m_index - 1]) {
                        stackDfs.push(neighbour);
                    }
                }
            }
        }
    }

    public void dfs_t(Node node, boolean[] visited, List<Node> component) { //folosit pentru findHardComponentsConnection
        Stack<Node> stackDfs = new Stack<>();
        stackDfs.push(node);
        while (!stackDfs.isEmpty()) {
            Node v = stackDfs.pop();
            if (!visited[v.m_index - 1]) {
                visited[v.m_index - 1] = true;
                component.add(v);
                List<Edge> edges = (List<Edge>) graph.get(v.m_index - 1).get(1);
                for (Edge edge : edges) {
                    Node neighbour = edge.m_endNode;
                    if (!visited[neighbour.m_index - 1]) {
                        stackDfs.push(neighbour);
                    }
                }
            }
        }
    }


    public List<List<Node>> findHardComponentsConnection() {
        List<List<Node>> result = new ArrayList<>();
        boolean[] visited = new boolean[MainPanel.nodeArray.size()];
        Stack<Node> stack = new Stack<>();
        Arrays.fill(visited, false);
        for (Node nod : MainPanel.nodeArray) {
            if (!visited[nod.m_index - 1]) {
                dfs(nod, visited, stack);
            }
        }
        Arrays.fill(visited, false);
        while (!stack.isEmpty()) {
            Node nod = stack.pop();
            if (!visited[nod.m_index - 1]) {
                List<Node> component = new ArrayList<>();
                dfs_t(nod, visited, component);
                result.add(component);
            }
        }
        return result;
    }
    public List<Integer> topologicalSort() {
        boolean[] visited = new boolean[MainPanel.nodeArray.size()];
        Arrays.fill(visited, false);
        Stack<Integer> stack = new Stack<>();

        for (Node startNode : MainPanel.nodeArray) {
            int startNodeIndex = startNode.m_index - 1;

            if (!visited[startNodeIndex]) {
                topologicalSortUtil(startNodeIndex, visited, stack);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop() + 1);
        }

        return result;
    }

    public boolean isCyclic(boolean withMessage) {
        boolean[] visited = new boolean[MainPanel.nodeArray.size()];
        boolean[] inStack = new boolean[MainPanel.nodeArray.size()];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < MainPanel.nodeArray.size(); ++i) {
            if (!visited[MainPanel.nodeArray.get(i).m_index - 1]) {
                stack.push(MainPanel.nodeArray.get(i).m_index - 1);
                while (!stack.isEmpty()) {
                    int currentNodeIndex = stack.peek();

                    if (!visited[currentNodeIndex]) {
                        visited[currentNodeIndex] = true;
                        inStack[currentNodeIndex] = true;
                    } else {
                        inStack[currentNodeIndex] = false;
                        stack.pop();
                    }

                    List<Edge> edges = (List<Edge>) graph.get(currentNodeIndex).get(1);
                    for (Edge edge : edges) {
                        Node neighbor = edge.m_endNode;
                        int neighborIndex = neighbor.m_index - 1;

                        if (!visited[neighborIndex]) {
                            stack.push(neighborIndex);
                        } else if (inStack[neighborIndex]) {
                            if(withMessage)
                                JOptionPane.showMessageDialog(null,"Graph is cyclic.",
                                    "Algoritmica Grafurilor",JOptionPane.WARNING_MESSAGE);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void topologicalSortUtil(int currentNode, boolean[] visited, Stack<Integer> stack) {
        visited[currentNode] = true;

        List<Edge> edges = (List<Edge>) graph.get(currentNode).get(1);
        for (Edge neighbourEdge : edges) {
            Node neighbourNode = neighbourEdge.m_endNode;
            int neighbourIndex = neighbourNode.m_index - 1;

            if (!visited[neighbourIndex]) {
                topologicalSortUtil(neighbourIndex, visited, stack);
            }
        }

        stack.push(currentNode);
    }

    public boolean IsArborescence()
    {
        if(this.isCyclic(true)) return false;
        if(this.NumberOfNodes()!=this.NumberOfEdges()+1){
            JOptionPane.showMessageDialog(null,"Number of nodes is not equal to number of edges-1.",
                    "Algoritmica Grafurilor",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        initializeComponents();
        return components.size() == 1;
    }

    public Node FindRoot() {
        Set<Node> possibleRoots = new HashSet<>(MainPanel.nodeArray);

        for (List<Object> nodeAndEdges : graph) {
            List<Edge> edges = (List<Edge>) nodeAndEdges.get(1);
            for (Edge edge : edges) {
                possibleRoots.remove(edge.m_endNode);
            }
        }
        if (possibleRoots.isEmpty()) {
            // Nu există noduri care să nu aibă muchii îndreptate spre ele, întoarce null.
            return null;
        } else {
            // Alege oricare dintre nodurile posibile ca rădăcină
            return possibleRoots.iterator().next();
        }
    }
 void addEdges(int nodeIndex,ArrayList<Boolean> visited, PriorityQueue<Edge> pQueue) {
     visited.set(nodeIndex, true);
     for (Edge edge : MainPanel.edgeArray) {
         if (edge.m_startNode.m_index == nodeIndex+1) {
             if (!visited.get(edge.m_endNode.m_index-1))
             {
                pQueue.add(edge);
             }
         }
     }
 }
    public Graph primMst() {

        List<Node> auxNode = MainPanel.nodeArray;
        int n = auxNode.size()-1; //numarul de noduri
        Random rand = new Random();
        int startNodeIndex = rand.nextInt(n);
        System.out.println("Se porneste de la nodul cu index " + (startNodeIndex+1));
        int m = n;
        int edgeCount = 0;
        MstCost = 0;
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            visited.add(false);
        }
        ArrayList<Edge> mstEdges = new ArrayList<>(Collections.nCopies(m, null));
        PriorityQueue<Edge> pQueue = new PriorityQueue<>(Comparator.comparing(Edge::getWeight, Comparator.nullsLast(Integer::compareTo)));
        addEdges(startNodeIndex, visited, pQueue);

        while (!pQueue.isEmpty() && edgeCount != m)
        {
            Edge edge=pQueue.poll();
            int nodeIndex=edge.m_endNode.m_index;
            System.out.println("Visiting node "+nodeIndex);
            if(visited.get(nodeIndex-1))
            {
                continue;
            }
            mstEdges.set(edgeCount++, edge);
            MstCost+=edge.m_weight;
            addEdges(nodeIndex-1,visited,pQueue);
        }
        if(edgeCount!=m)
        {
            System.out.println("MST CAN'T BE APPLIED");
            JOptionPane.showMessageDialog(null, "MST CAN'T BE APPLIED",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
            return new Graph();
        }
        else {
            MainPanel.edgeArray.clear();
            for(Edge edge:mstEdges)
            {
                Edge reversedEdge=new Edge(edge.m_endNode,edge.m_startNode,edge.m_weight);
                MainPanel.edgeArray.add(edge);
                MainPanel.edgeArray.add(reversedEdge);
            }
            System.out.println("MST cost is "+MstCost);
            Graph newGraph=new Graph();
            newGraph.MstCost=MstCost;
            return newGraph;
        }
    }


    public class UnionFind{
        //Numarul de elemente din union
        private int size;

     //Folosit pentru a afla size-ul fiecarei componente
     private int[] sz;
     //id[i]= parintele lui id, iar daca id[i]=i atunci i este nod radacina
     private int[] id;
     //numarul total de componente din uniune
     private int numComponents;
     UnionFind(int size)
     {
         if(size<=0)
             throw new IllegalArgumentException("Size found was 0");
         this.size=numComponents=size;
         sz=new int[size];
         id=new int[size];

         //La inceput fiecare componenta are size ul 1 si pointeaza catre sine (self root)
         for (int i = 0; i < size; i++) {
             id[i]=i;
             sz[i]=1;
         }
     }
     public int find(int p){
         //Cauta radacina componentei
         int root=p;
         while(root!=id[root])
             root=id[root];
        //Path compression
         while(p!=root){
             int next=id[p];
             id[p]=root;
             p=next;
         }
         return root;
     }

     public boolean connected(int p,int q)
     {
          return find(p)==find(q);
     }

     public int componentSize(int p)
     {
         return sz[find(p)];
     }
     public int getSize()
     {
         return size;
     }
     public int getNumComponentsSize()
     {
         return numComponents;
     }

     public void unify(int p, int q)
     {
         int root1=find(p);
         int root2=find(q);
         //elementele apartin deja de acelasi grup
         if(root1==root2) return;
         //compozitia a 2 elemente, dupa principiul grupul mai mic intra in grupul mai mare
         if(sz[root1]<sz[root2]) {
             sz[root2] += sz[root1];
             id[root1] = root2;
         }else{
             sz[root1] += sz[root2];
             id[root2] = root1;
         }
         numComponents--;
     }
    }

    public Graph kruskal(ArrayList<Edge> edges, int n){
        ArrayList<Edge>newEdges=new ArrayList<>();
        if(edges==null) return new Graph();
        int sum=0;
        edges.sort(Comparator.comparing(Edge::getWeight, Comparator.nullsLast(Integer::compareTo)));
        UnionFind uf=new UnionFind(n);
        for(Edge edge:edges)
        {
            if(uf.connected(edge.m_startNode.m_index-1,edge.m_endNode.m_index-1))
            {
                newEdges.add(edge);
                continue;
            }
            uf.unify(edge.m_startNode.m_index-1,edge.m_endNode.m_index-1);
            newEdges.add(edge);
            sum+=edge.m_weight;
            if(uf.componentSize(0)==n) {
                Edge auxEdge=new Edge(edge.m_endNode,edge.m_startNode,edge.m_weight);
                newEdges.add(auxEdge);
                break;
            }
        }
        if(uf.componentSize(0)!=n)
        {
            System.out.println("MST CAN'T BE APPLIED");
            JOptionPane.showMessageDialog(null, "MST CAN'T BE APPLIED",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
            return new Graph();
        }

        MainPanel.edgeArray=newEdges;
        Graph newGraph=new Graph();
        newGraph.MstCost=sum;
        System.out.println("Kruskal MST cost is "+sum);
        return newGraph;
    }

    public Graph boruvka(ArrayList<Edge> edges, int n)
    {
        initializeComponents();
        if(components.size()!=1) {

            System.out.println("MST CAN'T BE APPLIED");
            JOptionPane.showMessageDialog(null, "MST CAN'T BE APPLIED",
                    "Algoritmica Grafurilor", JOptionPane.WARNING_MESSAGE);
            return new Graph();
        }
        edges.sort(Comparator.comparing(Edge::getWeight, Comparator.nullsLast(Integer::compareTo)));
        ArrayList<Edge> newEdges=new ArrayList<>();
        ArrayList<Node> startNodes=new ArrayList<>();
        UnionFind uf=new UnionFind(n);
        int sum=0;
        for(Edge edge: MainPanel.edgeArray)
        {
            if(!startNodes.contains(edge.m_startNode))
            {
                startNodes.add(edge.m_startNode);
                Edge reversedEdge= new Edge(edge.m_endNode,edge.m_startNode,edge.m_weight);
                newEdges.add(edge);
                newEdges.add(reversedEdge);
                if(!startNodes.contains(reversedEdge.m_startNode))
                {
                    startNodes.add(reversedEdge.m_startNode);
                    if(!uf.connected(reversedEdge.m_startNode.m_index-1,reversedEdge.m_endNode.m_index-1))
                    {
                        uf.unify(reversedEdge.m_startNode.m_index-1,reversedEdge.m_endNode.m_index-1);
                        sum+=edge.m_weight;
                    }
                }
                if(!uf.connected(edge.m_startNode.m_index-1,edge.m_endNode.m_index-1))
                {
                    uf.unify(edge.m_startNode.m_index-1,edge.m_endNode.m_index-1);
                    sum+=edge.m_weight;
                }
            }
        }
        if(startNodes.size()!=MainPanel.nodeArray.size())
            throw new ArrayIndexOutOfBoundsException("Not all nodes were verified");

        for(Edge edge:MainPanel.edgeArray)
        {
            if(!newEdges.contains(edge))
            {
                if(!uf.connected(edge.m_startNode.m_index-1,edge.m_endNode.m_index-1))
                {
                    uf.unify(edge.m_startNode.m_index-1,edge.m_endNode.m_index-1);
                    newEdges.add(edge);
                    sum+=edge.m_weight;
                }
            }
        }
        MainPanel.edgeArray=newEdges;
        Graph newGraph=new Graph();
        newGraph.MstCost=sum;
        return newGraph;
    }

    private static abstract class NetworkFlowSolverBase {
        final Integer INF=Integer.MAX_VALUE/2;
        int numberOfNodes,source,sink;
        protected int visitedToken=1;
        protected int[] visited;

        protected boolean solved;
         protected int maxFlow;
         protected Graph newGraph;

         public NetworkFlowSolverBase(int n,int s,int t)
         {
             numberOfNodes=n;
             source=s;
             sink=t;
             newGraph=new Graph();
             visited=new int[n];
         }

         public Graph getNewGraph()
         {
             execute();
             return newGraph;
         }

         public void execute(){
             if(solved) return;
             solved=true;
             solve();
         }
        public abstract void solve();

        public int getMaxFlow() {
            return maxFlow;
        }
    }

    public static class FordFulkersonDFSSolver extends NetworkFlowSolverBase{
        public FordFulkersonDFSSolver(int n,int s,int t)
        {
            super(n,s,t);
        }

        @Override
        public void solve() {
            for(int f=dfs(source,INF);f!=0;f=dfs(source,INF))
            {
                visitedToken++;
                maxFlow+=f;
            }
        }
        private int dfs(int node,int flow)
        {
            if(node==sink)return flow;
            visited[node-1]=visitedToken;
            List<Edge> allEdgesForNode=new ArrayList<>();

                List<Edge> auxEdgeList=new ArrayList<>();
                auxEdgeList.addAll(MainPanel.edgeArray);
                auxEdgeList.addAll(MainPanel.reversedEdgeArray);
                for(Edge edge:auxEdgeList) {
                    if (edge.m_startNode.m_index == node || edge.m_endNode.m_index == node) {
                        allEdgesForNode.add(edge);
                    }
                }


            for(Edge edge:allEdgesForNode)
            {
                if(edge.remainingCapacity()>0&&visited[edge.m_endNode.m_index-1]!=visitedToken)
                {
                    int bottleneck=dfs(edge.m_endNode.m_index,min(flow,edge.remainingCapacity()));

                    if(bottleneck>0){
                        edge.augment(bottleneck);
                        return bottleneck;
                    }
                }
            }
            return 0;
        }
    }

}

