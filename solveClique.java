
import java.io.FileNotFoundException; //for errors
import java.io.File;      //for new File in scanner
import java.util.ArrayList; //to record longest clique and vertices to explore
import java.util.Arrays;
import java.util.Scanner; // for reading the file

public class solveClique
{
    //GLOBAL VARIABLES (OH NO)
    static int version = 0;             //Version of graph for printout G1,G2 etc etc.
    static int numEdges;
    static int numNodes = 0;            //Number of nodes in a graph
    static int maxCliqueSizeRecord; //The size of the max clique

    static double startTime;        //Used to start CPU stopwatch
    static double endTime;          //Used to end CPU stopwatch

    static int[] maxClique;         //The current Max Clique represented with a 1 if connected

    static int[][] E;               //Adjacency matrix of a given graph




    public static void main(String[] args)
    {
        //Print Intro:
        System.out.println("* Max Cliques in graphs2021.txt\n(|V|,|E|) Cliques (size, ms used)");

        /////////////////////////////////////
        //FILE READING SECTION            //
        ///////////////////////////////////
        Scanner file = null;
        //start the scanner in try catch block for errors
        try
        {
            file = new Scanner(new File(args[0]));
        }
        catch (FileNotFoundException e)
        {
            System.out.print("COULD NOT OPEN FILE");
        }


        while (file != null && file.hasNext())
        {
            numEdges = 0; //reset numEdges
            String vertex = file.nextLine();
            numNodes = Integer.parseInt(vertex); //get dimension of the matrix
            if (numNodes == 0) return;
            version++;
            E = new int[numNodes][numNodes];
            maxClique = new int[numNodes];
            maxCliqueSizeRecord = 0;

            for (int i = 0; i < numNodes; i++)
            {
                vertex = file.nextLine();
                Scanner ints = new Scanner(vertex);

                for (int j = 0; j< numNodes; j++)
                {
                    int connectionVal = ints.nextInt();

                    if (i != j)
                    {
                        E[i][j] = connectionVal;
                        numEdges += connectionVal;
                    }
                }
                ints.close();
            }

            numEdges = numEdges / 2;

            /////////////////////////////////////
            //Clique Calculations             //
            ///////////////////////////////////

            maxClique();


            /////////////////////////////////////
            //Printing Calculations           //
            ///////////////////////////////////
            System.out.print("G" + version + " (" + numNodes + ", " + numEdges + ") {");

            int cliqueIndex = 0;
            int numsAdded = 0;
            for (int e: maxClique)
            {
                if (e == 1)
                {
                    //print out index of the vertex
                    System.out.print(cliqueIndex);

                    //add comma when necessary
                    if (numsAdded < (maxCliqueSizeRecord - 1))
                    {
                        System.out.print(", ");
                        numsAdded++;
                    }
                }
                cliqueIndex++;
            }
            endTime = System.currentTimeMillis()-startTime; //for printing out end time.
            System.out.println("} (size=" + maxCliqueSizeRecord + ", " + endTime + " ms)");


        }


        assert file != null;
        file.close();
    }

    public static void maxClique()
    {

        startTime = System.currentTimeMillis();

        ArrayList<Integer> clique = new ArrayList<>();
        ArrayList<Integer> unexplored = new ArrayList<>();

        for (int i = 0; i< numNodes; i++)
            unexplored.add(i);

        recursiveClique(clique,unexplored);
    }

    public static void recursiveClique(ArrayList<Integer> clique, ArrayList<Integer> unexploredVertices)
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
                if (E[vertex][nextVertex] == 1)
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
                recursiveClique(clique, recursiveUnexplored);
            }

            clique.remove(clique.size()-1);
            unexploredVertices.remove(i);
        }
    }


}
