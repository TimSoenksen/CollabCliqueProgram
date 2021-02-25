/*
Alec Greenholdt
IT328
solve 33cn
*/
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class solve3CNF{
    //method to create the adj matirix to find the cliques
    //list is the list for input and k is the number of klauses
    public static int[][] makeAdjList(ArrayList<Integer> list, int k){
      int size= k*3;
      int[][] adjMat = new int[size][size];
      for(int i=0;i<size;i++){
        int val= list.get(i); //list value at current index
        for(int j=i; j<size;j++){// start to fill out the the rows

          int check=list.get(j);// check if can be connected
          //this if statement makes sure the values to compare
          //are both not in the same klause and also Not
          //each others negation
          if(i/3 != j/3){

            if(val!= -check){
              adjMat[i][j]=1;
              adjMat[j][i]=1;
            }
            else{
              adjMat[i][j]=0;
              adjMat[j][i]=0;
            }
          }

        }
      }
      return adjMat;
    }

    public static void main(String[] args) throws IOException {
      System.out.println("\n* Solve 3CNF in cnfs2021.txt:\t(reduced to K-Clique) *"
        +"\nX means can be either T or F\n");


      Path infile= Paths.get(args[0]);
      Scanner scan = new Scanner(infile);
      int count=0;//which number cnf were on
      int x=0;
      while(scan.hasNext()){
      long starttime = System.currentTimeMillis();
      count++;
      ArrayList<Integer> list = new ArrayList<Integer>(); //read in all the elements
      List<Boolean> boollist = new ArrayList<Boolean>(); //keep track of bools
      String nxtLine= scan.nextLine();
      Scanner line = new Scanner(nxtLine);

      x++;
      if(x>5){
        break;
      }
      while(line.hasNext()){


        if(line.hasNextInt()){
          list.add(line.nextInt());
        }
        else{
          line.next();
        }

      }
      //get size of list and divide by 3 for klauses
      int size= list.size();
      int k=(size/3);
      //set base value to find how many elements are in solve3CNF
      int elements=0;

      //make the matirix
      int[][] matrix= makeAdjList(list,k);
      utilityFunctions test = new utilityFunctions();
      ArrayList<Integer> cliqueLocations=test.findCliqueOfSize(matrix,k);
      if(cliqueLocations != null){
        
      }
      for(int i=0;i<size;i++){
        if(Math.abs(list.get(i))>elements){
          elements= Math.abs(list.get(i));
        }
      }
      System.out.print("3CNF No."+count+": [n="+elements+" k="+k+"]");

      boollist.add(null);// we dont want to have a value at 0
      //check for bool print out
      for(int i=1;i<elements+1;i++){
        if(i%2==0){
          boollist.add(true);
        }
        else if(i%3==0){
          boollist.add(null);
        }
        else{
          boollist.add(false);
        }
      }


      //create small print after top
      String topTf=" [";
      for(int i=1;i<elements+1;i++){
        if(boollist.get(i)==null){
          topTf+=" "+i+":X";
        }
        else if(boollist.get(i)==true){
          topTf+=" "+i+":T";
        }
        else{
          topTf+=" "+i+":F";
        }
      }
      topTf+="]";
      System.out.println(topTf);


      String cnf=""; // string to print out cnf
      int p=0;//position for list for printout
      for(int i=0;i<k;i++){
        cnf+="(";
        for(int j=0; j<3;){
          cnf+=list.get(p);
          j++;
          cnf+="|";
          p++;
        }
        cnf+=")^";
      }
      cnf=cnf.substring(0,cnf.length()-1);
      System.out.print(cnf);

      //create ending statement printout
      System.out.print("  "+topTf+"==>");//inbetween numbers and true false

      String cnfbool=""; // string to print out cnf
      int pos=0;//position for list for printout
      for(int i=0;i<k;i++){
        cnfbool+="(";
        for(int j=0; j<3;){
          if(list.get(pos)<0){ //checks if it is the negation so it does the opposite
            if(boollist.get((Math.abs(list.get(pos))))==null){
              cnfbool+="X";
              }
            else if(boollist.get((Math.abs(list.get(pos))))==true){
              cnfbool+="F";
              }
            else{
              cnfbool+="T";
            }
          }
          else{//if not the negation
            if(boollist.get((Math.abs(list.get(pos))))==null){
              cnfbool+="X";
              }
            else if(boollist.get((Math.abs(list.get(pos))))==true){
              cnfbool+="T";
              }
            else{
              cnfbool+="F";
            }
          }
          j++;
          cnfbool+="|";
          pos++;
        }
        cnfbool+=")^";
      }
      cnfbool=cnfbool.substring(0,cnfbool.length()-1);
      long endtime= System.currentTimeMillis()-starttime;
      System.out.println(cnfbool+" ("+endtime+" ms)\n");

    }//end of while

    }



}
