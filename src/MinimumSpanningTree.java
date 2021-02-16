/*
Program Name: Minimum Spanning Tree
Programmer Name: Ahmed Mazloum
Description:The problem to solve is using a priority queue to compute a minimum spanning tree.
Given a fully connected undirected graph where each edge has a weight, find the set of edges with the least total sum of weights.
Date Created:7/20/2020
*/
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class MinimumSpanningTree {
  /*
    Description: Main opens the files and has the menu where the messages print to console and the output file
    Pre-condition: none
    Post-condition: Messages gt displayed to console and output file
   */
    public static void main(String[] args) throws IOException {



        ArrayList<String> ArrData = new ArrayList<String>();

        Scanner user = new Scanner(System.in);
        String inputFileName;

        // prepare the input file
        System.out.print("Input Your File Name: ");
        inputFileName = user.next();
        File input = new File(inputFileName);
        if(input.exists()) {
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                ArrData.add(data);
            }
            scan.close();
        }
        else {
            System.out.println("File does exist try again");
            System.out.print("Input Your File Name: ");
            inputFileName = user.next();
            File input1 = new File(inputFileName);
            Scanner scan = new Scanner(input1);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                ArrData.add(data);
            }
            scan.close();
        }
        // prepare the output file
        System.out.print("Output File Name: ");
       FileOutputStream outputFileName = new FileOutputStream(user.next());
        ObjectOutputStream output = new ObjectOutputStream(outputFileName);

        System.out.println("Welcome to CisLand");
        output.writeObject("Welcome to CisLand" + "\n");

        // processing loop

        int ogArrSize = ArrData.size();



        int V = 0;
        int E = 0;
        int E1 = 0;
        int E2 = 0;
        String E3 = null;
        String E4 = null;
        String E5 = null;

        V = Character.getNumericValue(ArrData.get(0).charAt(0));
        if(ArrData.get(0).length() == 4)  {
            E1= Character.getNumericValue(ArrData.get(0).charAt(2));
            E2 = Character.getNumericValue(ArrData.get(0).charAt(3));
            E4 = Integer.toString(E2);
            E3 = Integer.toString(E1);
            E5= E3+E4;
            E = Integer.parseInt(E5);
        }
        else{
            E = Character.getNumericValue(ArrData.get(0).charAt(2));
        }


        System.out.println(V + " vertices, " + E + " edges");
        prims.Graph graph = new prims.Graph(V);
        prims a = new prims();

        Dijkstra graph1 = new Dijkstra();


        for (int i = 1; i <= E; i++) {
            if(Character.getNumericValue(ArrData.get(i).charAt(4)) > 0 && Character.getNumericValue(ArrData.get(i).charAt(0)) < V) {
                graph.addEdge(Character.getNumericValue(ArrData.get(i).charAt(0)), Character.getNumericValue(ArrData.get(i).charAt(2)), Character.getNumericValue(ArrData.get(i).charAt(4)), output);
                graph1.addEdge(Character.getNumericValue(ArrData.get(i).charAt(0)), Character.getNumericValue(ArrData.get(i).charAt(2)), Character.getNumericValue(ArrData.get(i).charAt(4)));

            }
            else {
                if(Character.getNumericValue(ArrData.get(i).charAt(4)) <= 0){
                    System.out.println("Error invalid weight (<=): " + Character.getNumericValue(ArrData.get(i).charAt(4)) + " is not a valid weight");
                    output.writeObject("Error invalid weight (<=): " + Character.getNumericValue(ArrData.get(i).charAt(4)) + " is not a valid weight" + "\n");
                }
                else {
                    System.out.println("Error invalid vertex");
                    output.writeObject("Error invalid vertex"+ "\n");
                }
            }
        }

        char choice = 0;
        String userInput = null;

        int count = 0;
        int size = ArrData.size();


        if (size > E + 1) {
            count = size - (E +1);
        }
        System.out.println("Initial graph complete");
        output.writeObject("Initial graph complete"+"\n");


        int counter =0;
        while ( choice != 'T') {
            if(counter >= ogArrSize) {
                System.out.println("E to add edge, I to increment a edge weight, D to decrement an edge weight, S to find the shortest path of an edge, and T to quit program");
                System.out.println("Enter in Data in the format(E 1 3 5)");
                output.writeObject("E to add edge, I to increment a edge weight, D to decrement an edge weight, S to find the shortest path of an edge, and T to quit program"+ "\n");
                output.writeObject("Enter in Data in the format(E 1 3 5)"+ "\n");


                Scanner scanner = new Scanner(System.in);
                    userInput = scanner.nextLine();
                    ArrData.add(userInput);
                    size++;
            }


            choice = ArrData.get(counter).charAt(0);
            if(!Character.isLetter(choice)){
                counter++;
                continue;
            }

            int src = Character.getNumericValue(ArrData.get(counter).charAt(2));
            int dest = Character.getNumericValue(ArrData.get(counter).charAt(4));
            int weight = 0;
            if (ArrData.get(counter).length() > 5) {
                weight = Character.getNumericValue(ArrData.get(counter).charAt(6));
            }

            switch (choice) {
                case 'E':
                        for (int i = 0; i < size; i++) {
                            if ((src == Character.getNumericValue(ArrData.get(i).charAt(0)) || src == Character.getNumericValue(ArrData.get(i).charAt(2))) && (dest == Character.getNumericValue(ArrData.get(i).charAt(2)) || dest == Character.getNumericValue(ArrData.get(i).charAt(0)))) {

                                System.out.println("Edge already exist try again");
                                output.writeObject("Edge already exist try again"+ "\n");

                            }
                        }
                        while (weight <= 0 ) {
                            System.out.println("Weight is equal to or less than zero try again");
                            output.writeObject("Weight is equal to or less than zero try again"+ "\n");
                        }

                        if(src > V){
                            System.out.println("Edge doesnt exist");
                        }
                    graph1.addEdge(src, dest, weight);
                    graph.addEdge(src, dest, weight,output);
                    graph.primMST();
                    break;
                case 'I':
                    if(weight > 0 && src <= V){
                               graph.increase(src, dest, weight,output);
                                graph.increase(dest, src, weight,output);
                                System.out.println("Increment edge " + src + " " + dest + " weight by " + weight);
                                output.writeObject("Increment edge " + src + " " + dest + " weight by " + weight + "\n");
                                graph.primMST();
                            }

                    break;
                case 'D':
                    if(weight > 0) {
                        graph.decrease(src, dest, weight);
                        graph.decrease(dest, src, weight);
                        System.out.println("Decrement edge " + src + " " + dest + " weight by "+ weight);
                        output.writeObject("Decrement edge " + src + " " + dest + " weight by "+ weight+"\n");
                        graph.primMST();

                    }
                    else{
                        System.out.println("Invalid try again");
                        output.writeObject("Invalid try again"+"\n");
                    }
                    break;
                case 'S':
                    List<Integer> result = graph1.shortestPath(src, dest);
                    System.out.println("Shortest path between " + src + " and " + dest + ": " + result);
                    output.writeObject("Shortest path between " + src + " and " + dest + ": " + result+"\n");
                    break;
                case 'T':
                    System.out.println("Exit");
                    output.writeObject("Exit"+"\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error invalid command");
                    output.writeObject("Error invalid command"+"\n");
            }
            count--;
            counter++;
        }

        output.flush();
        output.close();
    }

}

