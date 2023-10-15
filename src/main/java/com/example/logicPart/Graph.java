package com.example.logicPart;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

public class Graph {
    public static List<Pair<Pair<Float, Float>, Pair<String, Integer>>>[] adj;
    public Map<String, Integer> stateMappingNumber;
    public  Map<Integer, String> numberMappingState;
    public static int countOfState;

    
    public Graph() {
        adj = new ArrayList[1000000];
        for (int i = 0; i < adj.length; i++) {
            adj[i]=new ArrayList<>();
        }
        stateMappingNumber = new HashMap<>();
        numberMappingState = new HashMap<>();
        countOfState = 0;
    }

    private static void addInGraph(int source, int destination, float price, float time, String mode) {
        adj[source].add(new Pair<>(new Pair<>(price, time), new Pair<>(mode, destination)));
    }

    public void makeGraph(String source, String destination, float price, float time, String mode) {
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
    
//    public void invertMap() {
////        for (Entry<String, Integer> entry : stateMappingNumber.entrySet()) {
////            System.out.println(entry.getKey() + ": " + entry.getValue());
////          }
//        System.out.println("*******************");
//        Map<Integer,String> numberStateMapping = stateMappingNumber.entrySet()
//        		.stream()
//        		.collect(Collectors.toMap(Entry::getValue,Entry::getKey));
////        for (Entry<Integer, String> entry : numberStateMapping.entrySet()) {
////            System.out.println(entry.getKey() + ": " + entry.getValue());
////          }
//    }
    public String minPrice(int src,int desc) {
    	PriorityQueue<Pair<Integer, Pair<Integer, String>>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));
        pq.add(new Pair<>(0, new Pair<>(src, "")));
//        invertMap();
        List<Integer> vis = new ArrayList<>(countOfState);
        for (int i = 0; i < countOfState; i++)
            vis.add(0);
        
        for (Map.Entry<String, Integer> entry : stateMappingNumber.entrySet())
            numberMappingState.put(entry.getValue(), entry.getKey());

        while (!pq.isEmpty()) {
            Pair<Integer, Pair<Integer, String>> top = pq.poll();
            int price = top.getKey();
            int node = top.getValue().getKey();
            String path = top.getValue().getValue();
            vis.set(node, 1);

            if (node == desc) {
                StringBuilder ans = new StringBuilder();
                System.out.println(path);
                int tempNum = 0;
                String tempMode = "";
                String tempSource = numberMappingState.get(src);
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

                ans.append(" It will take ").append(price).append(" price that is the minimum price");
                return ans.toString();
            }
//            String tmps="";
//            int nexPrice=0;
            for (Pair<Pair<Float, Float>, Pair<String, Integer>> it : adj[node]) {
                if (vis.get(it.getValue().getValue()) == 0) {
                    StringBuilder temp = new StringBuilder(" ").append(it.getValue().getValue()).append("_")
                            .append(it.getValue().getKey()).append(" ");
                    pq.add(new Pair<>( (int) (price +  it.getKey().getKey() ),
                            new Pair<>(it.getValue().getValue(), path + temp.toString())));
                }
            }
        }
        return "no route";
    }
    
    public String minTime(int src ,int desc) {
    	PriorityQueue<Pair<Integer, Pair<Integer, String>>> pq =
                new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));
        pq.add(new Pair<>(0, new Pair<>(src, "")));

        List<Integer> vis = new ArrayList<>(countOfState);
        for (int i = 0; i < countOfState; i++)
            vis.add(0);

        for (Map.Entry<String, Integer> entry : stateMappingNumber.entrySet())
            numberMappingState.put(entry.getValue(), entry.getKey());

        while (!pq.isEmpty()) {
            Pair<Integer, Pair<Integer, String>> top = pq.poll();
            int time = top.getKey();
            int node = top.getValue().getKey();
            String path = top.getValue().getValue();
            vis.set(node, 1);

            if (node == desc) {
                StringBuilder ans = new StringBuilder();
                int tempNum = 0;
                String tempMode = "";
                String tempSource = numberMappingState.get(src);
                String tempDestination;

                for (int i = 1; i < path.length(); i++) {
                    char currentChar = path.charAt(i);
                    if (currentChar == ' ') {
                        ans.append("Take ").append(tempMode).append(" From ").append(tempSource).append(" to ");
                        tempDestination = numberMappingState.get(tempNum);
                        ans.append(tempDestination).append(" then ");

                        tempNum = 0;
                        tempMode = "";
                        tempSource = tempDestination;
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

                ans.append(" it will take ").append(time).append(" hours that is minimum time");
                return ans.toString();
            }

            for (Pair<Pair<Float, Float>, Pair<String, Integer>> it : adj[node]) {
                if (vis.get(it.getValue().getValue()) == 0) {
                    StringBuilder temp = new StringBuilder(" ").append(it.getValue().getValue()).append("_")
                            .append(it.getValue().getKey()).append(" ");
                    pq.add(new Pair<>( (int)  (time + (it.getKey().getValue())), new Pair<>(it.getValue().getValue(), path + temp.toString())));
                }
            }
        }
        return "no route";
    }
    
//    public int cheapestRoute(int src, int desc) {
////    	int source = 1;
////    	int destination = 3;
//        PriorityQueue<Pair<Integer, Pair<Integer, String>>> pq = new PriorityQueue<>(
//                (a, b) -> Integer.compare(a.getFirst(), b.getFirst())
//        );
//
//        pq.add(new Pair<>(0, new Pair<>(src, "")));
//
//        int[] vis = new int[countOfState];
//
//        while (!pq.isEmpty()) {
//            Pair<Integer, Pair<Integer, String>> top = pq.poll();
//
//            int time = top.getFirst();
//            int node = top.getSecond().getFirst();
//            String path = top.getSecond().getSecond();
//
//            vis[node] = 1;
//
//            if (node == desc) {
//                System.out.println(path);
//                return time;
//            }
//
//            for (Pair<Float, Pair<String, Integer>> it : adj.get(node)) {
//                if (vis[it.getSecond().getSecond()] == 0) {
//                    String temp = " ";
//                    temp += it.getSecond().getSecond() + "_";
//                    temp += it.getSecond().getFirst();
//                    pq.add(new Pair<>(Math.round(time + it.getFirst()), new Pair<>(it.getSecond().getSecond(), path + temp)));
//                }
//            }
//        }
//
//        System.out.println("No route");
//        return -1;
//    }

	

	
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
