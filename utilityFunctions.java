import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class utilityFunctions {
    public static int maxCliqueSizeRecord = 0;
    public static int[] maxClique;
    public static int[][] E;

    //takes an adjacency matrix and returns a clique of a certain size. will return null if no such clique exists
    ArrayList<Integer> findCliqueOfSize(int[][] adjacencyMatrix, int sizeToFind){

        ArrayList<Integer> clique = new ArrayList<>();
        ArrayList<Integer> unexplored = new ArrayList<>();
        ArrayList<Integer> returnList = new ArrayList<>();

        for (int i = 0; i< adjacencyMatrix.length; i++)
            unexplored.add(i);

        recursiveClique(clique,unexplored, adjacencyMatrix);

        if(maxClique.length >= sizeToFind){
            int index = 0;
            int numsAdded = 0;
            //return an array of the sizeToFind
            for(int e: maxClique){
                if(e == 1 && numsAdded != sizeToFind){
                    returnList.add(index);
                    numsAdded++;
                }
                index++;
            }
            if(returnList.size() == sizeToFind)
                return returnList;
            else return null;
        }else{
            return null;
        }

    }

    public static void recursiveClique(ArrayList<Integer> clique, ArrayList<Integer> unexploredVertices, int[][]adjacencyMatrix)
    {

        for (int i=unexploredVertices.size()-1; i >= 0; i--)
        {

            if (clique.size() + unexploredVertices.size() <= maxCliqueSizeRecord)
            {
                return;
            }

            int vertex = unexploredVertices.get(i);
            clique.add(vertex);


            ArrayList<Integer> recursiveUnexplored = new ArrayList<>();
            for (int j=0; j<=i; j++)
            {
                int nextVertex = unexploredVertices.get(j);
                if (adjacencyMatrix[vertex][nextVertex] == 1)
                {
                    recursiveUnexplored.add(nextVertex);
                }
            }

            //if at the end of the recursiveUnexplored and we have a clique larger than the current record
            //keep track of the new record.
            if (recursiveUnexplored.isEmpty() && clique.size() > maxCliqueSizeRecord)
            {
                Arrays.fill(maxClique, 0);
                for (int k : clique)
                {
                    maxClique[k] = 1;
                    maxCliqueSizeRecord = clique.size();
                }
            }

            if (!recursiveUnexplored.isEmpty())
            {
                recursiveClique(clique, recursiveUnexplored, adjacencyMatrix);
            }

            clique.remove(clique.size()-1);
            unexploredVertices.remove(i);
        }
    }

    //TEST FUNCTION, quick way to make an adjacency list from a text file already in adjacency list format.
    public int[][] convertTestGraphInTXTToAdjacency(String filename, int numNodes) {

        Scanner file = null;

        //start the scanner in try catch block for errors
        try {
            file = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.print("COULD NOT OPEN FILE");
        }

        int numNodes2;
        boolean flag = false;
        String vertex;
        Scanner ints;
        int connectionVal;


        while (file != null && file.hasNext()) {
            flag = true;
            int numEdges = 0;
            vertex = file.nextLine();
            numNodes2 = Integer.parseInt(vertex);
            if (numNodes2 == 0) break;
            E = new int[numNodes2][numNodes2];
            maxClique = new int[numNodes2];
            maxCliqueSizeRecord = 0;

            for (int i = 0; i < numNodes2; i++) {
                vertex = file.nextLine();
                ints = new Scanner(vertex);

                for (int j = 0; j < numNodes2; j++) {
                    connectionVal = ints.nextInt();

                    if (i != j) {
                        E[i][j] = connectionVal;
                        numEdges += connectionVal;
                    }
                }
                ints.close();
            }
            numEdges = numEdges / 2;

        }
        assert file != null;
        file.close();

        return E;

    }


}
