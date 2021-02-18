import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException; //for errors
import java.io.File;      //for new File in scanner
import java.util.ArrayList; //to record longest clique and vertices to explore
import java.util.Arrays;
import java.util.Scanner; // for reading the file


public class solveClique {

    private static int longestCliqueSize = 0;

    private static int[] cliqueRecord; //matrix representing the max clique, inverse of this is independent set.

    //main
    public static void main(String[] args){
        /////////////////////////////////////
        //FILE READING SECTION            //
        ///////////////////////////////////
        Scanner file = null; //can't find if in try/catch block apparently
        //initialize scanner
        try{
            file = new Scanner(new File(args[0]));
        }catch(FileNotFoundException e){
            System.out.print("COULD NOT OPEN FILE");
        }

        /////////////////////////////////////
        //FILE TO 2D ARRAY                //
        ///////////////////////////////////

        while(file.hasNext()){
            int numEdges = 0;
            int size = Integer.parseInt(file.nextLine()); //get the size of the graph

            if(size == 0) return;

            int[][] E = new int[size][size]; //the set of edges
            cliqueRecord = new int[size];

            //start the reading
            for(int i = 0; i <size; i++){
                String vertex = file.nextLine();
                Scanner ints = new Scanner(vertex);
                for(int j=0; j<size;j++){
                    int connectionValue = ints.nextInt(); //if 1, means that there is a connection
                    if(i != j){ //do not count vertex that leads to itself
                        E[i][j] = connectionValue;
                        numEdges += connectionValue; //if 1, will increase the number of edges (later divide by 2 for duplicates)
                    }
                }
                ints.close();

            }
            //adjacency matrix without the self connections is created.


        /////////////////////////////////////
        //Clique Calculations             //
        ///////////////////////////////////

        maxClique(E, size, 9999);
            //TODO: Print intro
            //TODO: Print out max clique (maxcliquerecord in proper format)

        }

        file.close();

    }//end main

    //Method that takes in an adjacency matrix (in the form of a 2D array) and finds max clique,
    //(last parameter tells it to stop at a certain size of clique if you are trying to see if there is a
    //clique of size K, otherwise put it to 999999 for it to output the max clique.)
    //sizeofgraph is how many nodes are in the graph
    //updates
    public static void maxClique(int[][] adjacencyMatrix,int sizeOfGraph, int kStopPoint){
        double startTime = System.currentTimeMillis(); //the starting time of the experiment in ms for printout
        ArrayList<Integer> longestClique = new ArrayList<Integer>(); //the current longest clique
        ArrayList<Integer> unexploredVertices = new ArrayList<Integer>();

        //add all vertices to explore
        for(int i=0; i < sizeOfGraph; i++){
            unexploredVertices.add(i);
        }
        recursiveClique(longestClique, unexploredVertices, adjacencyMatrix);



    }

    private static void recursiveClique(ArrayList<Integer> longestClique, @NotNull ArrayList<Integer> unexploredVertices, int[][] adjacencyMatrix) {

        for(int i = unexploredVertices.size()-1; i >= 0 ; i--){
            int vertex = unexploredVertices.get(i);
            longestClique.add(vertex);

            ArrayList<Integer> unexploredVertices2 = new ArrayList<Integer>(i);

            for(int j=0; j <=i; j++){
                int nextVertex = unexploredVertices.get(j);
                if(adjacencyMatrix[vertex][nextVertex] == 1){
                    unexploredVertices2.add(nextVertex);
                }
            }

            if(unexploredVertices2.isEmpty() && longestClique.size() > longestCliqueSize){
                //set this as the longestclique

                Arrays.fill(cliqueRecord, 0); // 0 out everything

                for(int j : longestClique){
                    cliqueRecord[j] = 1;
                    longestCliqueSize = longestClique.size();
                }
            }

            if(!unexploredVertices2.isEmpty()){
                recursiveClique(longestClique,unexploredVertices2,adjacencyMatrix);
            }

            longestClique.remove(longestClique.size()-1);
            unexploredVertices.remove(i);

        }





    }//end recursivecliquemethod


} //end class
