package com.example.logicPart;
import java.util.*;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;

class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
class Edge {
    float price, time;
    char mode;
    int destination;

    public Edge(float price, float time, char mode, int destination) {
        this.price = price;
        this.time = time;
        this.mode = mode;
        this.destination = destination;
    }
}

public class Graph {
	static List<List<Edge>> adj = new ArrayList<>();
    public static Map<String, Integer> stateMappingNumber = new HashMap<>();
    public static  Map<Integer, String> numberMappingState = new HashMap<>();
    public static int countOfState;

    
    public Graph() {
    	int MAX_STATES = 1000000;
    	for (int i = 0; i < MAX_STATES; i++) {
            adj.add(new ArrayList<>());
        }
        countOfState = 0;
    }

    static void pathremove(StringBuilder s) {
        int n = s.length() - 1;
        while (n >= 0) {
            char c = s.charAt(n);
            if (c == '*') {
                s.deleteCharAt(n);
                break;
            } else {
                s.deleteCharAt(n);
            }
            n--;
        }
    }
    public String allRoute(int source, int destination) {
        for (Map.Entry<String, Integer> entry : stateMappingNumber.entrySet()) {
            numberMappingState.put(entry.getValue(), entry.getKey());
        }
    	int[] vis = new int[countOfState];
        StringBuilder ans = new StringBuilder();
        StringBuilder path = new StringBuilder();
        dfs(source, destination, vis, ans, path);
        return ans.toString();
    }
    
    
    
    static void dfs(int s, int d, int[] vis, StringBuilder ans, StringBuilder path) {
        if (s == d) {
            ans.append(path);
            ans.append("?");
            return;
        }

        vis[s] = 1;

        for (Edge it : adj.get(s)) {
            if (vis[it.destination] == 0) {
                path.append('*');
                path.append(numberMappingState.get(s));
                path.append("_");
                path.append(it.mode);
                path.append("_");
                path.append(numberMappingState.get(it.destination));
                dfs(it.destination, d, vis, ans, path);
                path.append("\n");
                pathremove(path);
            }
        }
        vis[s] = 0;
    }

    private static void addInGraph(int source, int destination, float price, float time, char mode) {
        adj.get(source).add(new Edge(price, time, mode, destination));
    }

    
    public void makeGraph(String source, String destination, float price, float time, char mode) {
    	int sourceNum, destinationNum;
        if (stateMappingNumber.containsKey(source)) {
            sourceNum = stateMappingNumber.get(source);
        } else {
        	stateMappingNumber.put(source, countOfState);
            sourceNum = countOfState;
            countOfState++;
        }

        if (stateMappingNumber.containsKey(destination)) {
            destinationNum = stateMappingNumber.get(destination);
        } else {
        	stateMappingNumber.put(destination, countOfState);
            destinationNum = countOfState;
            countOfState++;
        }
        addInGraph(sourceNum, destinationNum, price, time, mode);
    }
    

    public String minPrice(int source,int destination) {
    	PriorityQueue<Pair<Float, Pair<Integer, StringBuilder>>> pq = new PriorityQueue<>(Comparator.comparingDouble(p -> p.getKey()));
        pq.add(new Pair<>(0.0f, new Pair<>(source, new StringBuilder())));
        for (Map.Entry<String, Integer> entry : stateMappingNumber.entrySet()) {
            numberMappingState.put(entry.getValue(), entry.getKey());
        }
        while (!pq.isEmpty()) {
            float price = pq.peek().getKey();
            int node = pq.peek().getValue().getKey();
            StringBuilder path = pq.peek().getValue().getValue();
            pq.poll();
            int[] vis = new int[countOfState];
            Arrays.fill(vis, 0);
            vis[node] = 1;

            if (node == destination) {
                StringBuilder ans = new StringBuilder();
                int tempNum = 0;
                String tempMode = "";
                String tempSource = numberMappingState.get(source);
                String tempDestination;

                for (int i = 1; i < path.length(); i++) {
                    char currentChar = path.charAt(i);
                    if (currentChar == ' ') {
                        if (!tempMode.isEmpty()) {
                            ans.append("Take ").append(tempMode).append(" From ").append(tempSource).append(" to ");
                            tempDestination = numberMappingState.get(tempNum);
                            ans.append(tempDestination).append(" then ");

                            tempNum = 0;
                            tempMode = "";
                            tempSource = tempDestination;
                            tempDestination = "";
                        }
                    } else if (currentChar == '_') {
                        continue;
                    } else if (currentChar >= 'A' && currentChar <= 'Z') {
                        if (currentChar == 'B') {
                            tempMode += "Bus";
                        } else if (currentChar == 'F') {
                            tempMode += "Flight";
                        } else {
                            tempMode += "Train";
                        }
                    } else {
                        tempNum = tempNum * 10 + (currentChar - '0');
                    }
                }
                ans.append(" it will take ").append(price).append("Rs that is minimum price");
                return ans.toString();
            }

            for (Edge it : adj.get(node)) {
                if (vis[it.destination] == 0) {
                    StringBuilder temp = new StringBuilder(" ").append(it.destination).append("_").append(it.mode).append(" ");
                    pq.add(new Pair<>(price + it.price, new Pair<>(it.destination, new StringBuilder(path.toString() + temp.toString()))));
                }
            }
        }
        return "no route";
        }
    
    public String minTime(int source, int destination) {
        PriorityQueue<Pair<Float, Pair<Integer, StringBuilder>>> pq = new PriorityQueue<>(Comparator.comparingDouble(p -> p.getKey()));
        pq.add(new Pair<>(0.0f, new Pair<>(source, new StringBuilder())));
        for (Map.Entry<String, Integer> entry : stateMappingNumber.entrySet()) {
            numberMappingState.put(entry.getValue(), entry.getKey());
        }
        int[] vis = new int[countOfState];
        Arrays.fill(vis, 0);

        for (Map.Entry<String, Integer> entry : stateMappingNumber.entrySet())
            numberMappingState.put(entry.getValue(), entry.getKey());

        while (!pq.isEmpty()) {
            float time = pq.peek().getKey();
            int node = pq.peek().getValue().getKey();
            StringBuilder path = pq.peek().getValue().getValue();
            pq.poll();
            vis[node] = 1;

            if (node == destination) {
                StringBuilder ans = new StringBuilder();
                int tempNum = 0;
                String tempMode = "";
                String tempSource = numberMappingState.get(source);
                String tempDestination;

                for (int i = 1; i < path.length(); i++) {
                    char currentChar = path.charAt(i);
                    if (currentChar == ' ') {
                        if (!tempMode.isEmpty()) {
                            ans.append("Take ").append(tempMode).append(" From ").append(tempSource).append(" to ");
                            tempDestination = numberMappingState.get(tempNum);
                            ans.append(tempDestination).append(" then ");

                            tempNum = 0;
                            tempMode = "";
                            tempSource = tempDestination;
                            tempDestination = "";
                        }
                    } else if (currentChar == '_') {
                        continue;
                    } else if (currentChar >= 'A' && currentChar <= 'Z') {
                        if (currentChar == 'B') {
                            tempMode += "Bus";
                        } else if (currentChar == 'F') {
                            tempMode += "Flight";
                        } else {
                            tempMode += "Train";
                        }
                    } else {
                        tempNum = tempNum * 10 + (currentChar - '0');
                    }
                }
                ans.append("it will take ").append(time).append(" hours that's Fastest Route");
                return ans.toString();
            }

            for (Edge it : adj.get(node)) {
                if (vis[it.destination] == 0) {
                    StringBuilder temp = new StringBuilder(" ").append(it.destination).append("_").append(it.mode).append(" ");
                    pq.add(new Pair<>(time + it.time, new Pair<>(it.destination, new StringBuilder(path.toString() + temp.toString()))));
                }
            }
        }
        return "no route";
    }
    
}




class Main {
    public static void main(String[] args) {
//        Graph graph = new Graph(1000000);

//        graph.makeGraph("Delhi", "Chandigarh", 5000.0f, 2.0f, 'F');
//        graph.makeGraph("Delhi", "Chandigarh", 2000.0f, 4.0f, 'T');
//        graph.makeGraph("Delhi", "Chandigarh", 1000.0f, 6.0f, 'B');
//
//
//
//        graph.makeGraph("Delhi","Patiala",1800.0f,6.0f,'T');
//        graph.makeGraph("Delhi","Patiala",800.0f,8.0f,'B');
//
//        graph.makeGraph("Delhi","Pune",6000.0f,3.0f,'F');
//        graph.makeGraph("Delhi","Pune",3500.0f,14.0f,'T');
//        graph.makeGraph("Delhi","Pune",2000.0f,20.0f,'B');
//
//        graph.makeGraph("Chandigarh","Delhi",4500.0f,2.0f,'F');
//        graph.makeGraph("Chandigarh","Delhi",1000.0f,6.0f,'B');
//        graph.makeGraph("Chandigarh","Delhi",2000.0f,4.0f,'T');
//
//
//        graph.makeGraph("Chandigarh","Patiala",400.0f,1.0f,'T');
//        graph.makeGraph("Chandigarh","Patiala",200.0f,2.0f,'B');
//
//        graph.makeGraph("Chandigarh","Pune",8000.0f,4.0f,'F');
//        graph.makeGraph("Chandigarh","Pune",4000.0f,18.0f,'T');
//        graph.makeGraph("Chandigarh","Pune",3500.0f,24.0f,'B');
//
//        graph.makeGraph("Patiala","Chandigarh",400.0f,1.0f,'T');
//        graph.makeGraph("Patiala","Chandigarh",200.0f,2.0f,'B');
//
//        graph.makeGraph("Patiala","Delhi",1800.0f,6.0f,'T');
//        graph.makeGraph("Patiala","Delhi",800.0f,8.0f,'B');
//        graph.makeGraph("Patiala","Pune",3800.0f,20.0f,'T');
//
//
//        graph.makeGraph("Pune","Delhi",6500.0f,3.0f,'F');
//        graph.makeGraph("Pune","Delhi",3500.0f,14.0f,'T');
//        graph.makeGraph("Pune","Delhi",2500.0f,20.0f,'B');
//
//        graph.makeGraph("Pune","Chandigarh",8200.0f,4.0f,'F');
//        graph.makeGraph("Pune","Chandigarh",3800.0f,18.0f,'T');
//        graph.makeGraph("Pune","Chandigarh",3200.0f,24.0f,'B');
//        graph.makeGraph("Pune","Patiala",3800.0f,20.0f,'T');
//


//        for (String state : graph.stateMappingNumber.keySet()) {
//            System.out.println("state " + state + " mapping with " + graph.stateMappingNumber.get(state) + " state");
//        }

//        System.out.println();

//        System.out.println(graph.minimumTimeRoute(graph.stateMappingNumber.get("Delhi"), graph.stateMappingNumber.get("Chandigarh")));
    }
}