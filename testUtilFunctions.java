import java.util.ArrayList;

public class testUtilFunctions {
    public static void main(String[] args) {
        //initialize a new instance of utilityfunctions
        utilityFunctions test = new utilityFunctions();
        //create an adjacency matrix (i just made this from a text file from another test function
        int[][] adjacencyList = test.convertTestGraphInTXTToAdjacency("testgraph.txt",10);

        //findcliqueofsize will return an arraylist of a clique, will return null if sizeToFind is greater than
        //the size of the max clique of the function
        ArrayList<Integer> clickOfSize2 = test.findCliqueOfSize(adjacencyList,2);

        if(clickOfSize2 != null) {
            for (int integer : clickOfSize2) {
                System.out.println(integer);
            }
        }else{
            System.out.println("could not find click of that size");
        }

    }
}
