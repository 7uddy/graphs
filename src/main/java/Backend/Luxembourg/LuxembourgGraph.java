package Backend.Luxembourg;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class LuxembourgGraph {
    public static final float WIDTH = 480.0F;
    public static final float HEIGHT = 560.0F;
    private static final int LIMIT = 5;
    protected static final String filepath = "hartaLuxembourg.xml";
    public static Vector<LuxembourgNode> nodeList;
    public static LinkedList<LuxembourgEdge>[] adjacencyList;
    public static Vector<LuxembourgEdge> arcList;
    private static int maxLongitude = 0;
    private static int maxLatitude = 0;
    private static int minLongitude = Integer.MAX_VALUE;
    private static int minLatitude = Integer.MAX_VALUE;

    public LuxembourgGraph() {
        try {
            readFile();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        ScaleCoordinates();
    }

    protected static void readFile() throws Exception {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(LuxembourgGraph.filepath);
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
        streamReader.nextTag();
        streamReader.nextTag();
        nodeList = new Vector<>();
        arcList = new Vector<>();
        // Citirea nodurilor si construirea nodeList
        while (streamReader.hasNext()) {
            if (streamReader.isStartElement() && "node".equals(streamReader.getLocalName())) {
                int id = Integer.parseInt(streamReader.getAttributeValue(null, "id"));
                int longitude = Integer.parseInt(streamReader.getAttributeValue(null, "longitude"));
                int latitude = Integer.parseInt(streamReader.getAttributeValue(null, "latitude"));
                nodeList.add(new LuxembourgNode(id, longitude, latitude));
            }
            streamReader.next();
        }

        adjacencyList = new LinkedList[nodeList.size()];
        for (int i = 0; i < nodeList.size(); i++) {
            adjacencyList[i] = new LinkedList<>();
        }

        // Resetare streamReader pentru a începe din nou să citească de la început
        in = new FileInputStream(filepath);
        streamReader = inputFactory.createXMLStreamReader(in);
        streamReader.nextTag();
        streamReader.nextTag();

        // Citirea arcelor și construirea arcList și adjacencyList
        while (streamReader.hasNext()) {
            if (streamReader.isStartElement() && "arc".equals(streamReader.getLocalName())) {
                int from = Integer.parseInt(streamReader.getAttributeValue(null, "from"));
                int to = Integer.parseInt(streamReader.getAttributeValue(null, "to"));
                int length = Integer.parseInt(streamReader.getAttributeValue(null, "length"));
                arcList.add(new LuxembourgEdge(from, to, length, Color.BLACK));
                System.out.println(from + " " + to + " " +length);
                AddArcInAdjacencyList(from, to, length);
            }
            streamReader.next();
        }

    }

    private static void SetMargins() {
        for (LuxembourgNode node : nodeList) {
            int latitude = node.getLatitude();
            int longitude = node.getLongitude();

            if (maxLatitude < latitude)
                maxLatitude = latitude;
            if (maxLongitude < longitude)
                maxLongitude = longitude;
            if (minLatitude > latitude)
                minLatitude = latitude;
            if (minLongitude > longitude)
                minLongitude = longitude;
        }
    }

    private static void AddArcInAdjacencyList(int nodeStartID, int nodeEndID, int cost) {
        LuxembourgEdge tempArc = new LuxembourgEdge(nodeStartID, nodeEndID, cost, Color.BLUE);
        adjacencyList[nodeStartID].add(tempArc);
        tempArc = new LuxembourgEdge(nodeEndID, nodeStartID, cost, Color.BLUE);
        adjacencyList[nodeEndID].add(tempArc);
    }

    protected static void ScaleCoordinates() {
        SetMargins();
        for (LuxembourgNode node : nodeList) {
            int longitude = node.getLongitude();
            int latitude = node.getLatitude();
            int length = maxLongitude - minLongitude;
            int width = maxLatitude - minLatitude;

            float scaleX = WIDTH / length;
            float scaleY = HEIGHT / width;

            longitude = (int) ((longitude - minLongitude) * scaleY);
            latitude = (int) ((latitude - minLatitude) * scaleX);

            node.setLatitude( latitude + 400);
            node.setLongitude((int)(HEIGHT -longitude+45));
        }
    }

    public static int SearchNodeWithCoordinates(int x, int y) {
        for (LuxembourgNode node : nodeList) {
            if (Math.abs(node.getLongitude() - y) < LIMIT && Math.abs(node.getLatitude() - x) < LIMIT) {
                return node.getId();
            }
        }
        return -1;
    }

    public static void DijkstraShortestPath(int start, int end) {
        int numberOfNodes = nodeList.size();
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(numberOfNodes, Comparator.comparingInt(Pair::getFirst));

        int[] distance = new int[numberOfNodes];
        for (int index = 0; index < numberOfNodes; index++)
            distance[index] = Integer.MAX_VALUE;
        pq.add(new Pair<>(0, start));
        distance[start] = 0;

        boolean[] visited = new boolean[numberOfNodes];

        int[] parent = new int[numberOfNodes];
        for (int index = 0; index < numberOfNodes; index++) {
            parent[index] = -1;
        }

        while (!pq.isEmpty()) {
            int u = pq.peek().getSecond();
            pq.poll();
            visited[u] = true;
            for (LuxembourgEdge arc : adjacencyList[u]) {
                int v = arc.getIdEnd();
                int cost = arc.getCost();
                if (!visited[v] && distance[v] > distance[u] + cost) {
                    parent[v] = u;
                    distance[v] = distance[u] + cost;
                    pq.add(new Pair<>(distance[v], v));
                }
            }
        }
        PrintPath(parent, end, Color.RED);
    }

    public static void BellmanFordShortestPath(int start, int end) {

        int numberOfNodes = nodeList.size();
        PriorityQueue<Pair<Integer,Integer>> pq = new PriorityQueue<>(numberOfNodes, Comparator.comparingInt(Pair::getFirst));

        int[] distance = new int[numberOfNodes];
        boolean[] visited = new boolean[numberOfNodes];
        int[] negativeCycle = new int[numberOfNodes];
        int[] parent = new int[numberOfNodes];
        boolean checkNegativeCycle = true;

        for (int index = 0; index < numberOfNodes; index++){
            distance[index] = Integer.MAX_VALUE;
            visited[index] = false;
            parent[index] = -1;
        }

        distance[start] = 0;
        visited[start] = true;

        pq.add(new Pair<>(0,start));
        while (!pq.isEmpty()){
            int node = pq.peek().getSecond();
            pq.poll();
            visited[node] = false;
            for (int index = 0; index < adjacencyList[node].size(); index++){
                int neighbour = adjacencyList[node].get(index).getIdEnd();
                if (distance[neighbour] > distance[node] + adjacencyList[node].get(index).getCost()){
                    distance[neighbour] = distance[node] + adjacencyList[node].get(index).getCost();
                    parent[neighbour] = node;
                    negativeCycle[neighbour] += 1;
                    if (negativeCycle[neighbour] > numberOfNodes - 1){
                        System.out.println("Negative cycle found!! \n");
                        checkNegativeCycle = false;
                        break;
                    }
                    if (!visited[neighbour]){
                        visited[neighbour] = true;
                        pq.add(new Pair<>(distance[neighbour],neighbour ));
                    }
                }
            }
            if (!checkNegativeCycle){
                break;
            }
        }
        PrintPath(parent, end, Color.magenta);
    }

    private static void PrintPath(int[] parent, int nodeID, Color color) {
        for (int node = nodeID; parent[node] != -1; node = parent[node]) {
            int start = parent[node];
            for (LuxembourgEdge arc : arcList) {
                if ((arc.getIdStart() == start || arc.getIdEnd() == start) && (arc.getIdEnd() == node || arc.getIdStart() == node)) {
                    arc.setColor(color);
                }
            }
        }
    }
}
