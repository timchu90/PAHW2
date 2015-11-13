package pahw2;
import pahw2.Kmeans;
import pahw2.Kmeans.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class TestRun {
	public static void main(String[] args){
		
		//read in text database
		String textnames[] = new String[]{
				"E1.txt","E2.txt","E3.txt","E4.txt","E5.txt",
				"P1.txt","P2.txt","P3.txt","P4.txt","P5.txt",
				"T1.txt","T2.txt","T3.txt","T4.txt","T5.txt",
				"US1.txt","US2.txt","US3.txt","US4.txt","US5.txt"
		};
		String textdata[] = new String[20];
        String line = null;
		for(int i=0; i < 20;i++){
			textdata[i] = "";
			try {
				FileReader fileReader = new FileReader(textnames[i]);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
					textdata[i] = textdata[i].concat(line);
				}
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + textnames[i] + "'");                  
	        }
		}
		//results in text array with article as string
		
	
		double sample[][] = new double[][] {{1.0, 1.0}, 
		    {1.5, 2.0}, 
		    {3.0, 4.0}, 
		    {5.0, 7.0}, 
		    {3.5, 5.0}, 
		    {4.5, 5.0}, 
		    {3.5, 4.5}};
		ArrayList<ArrayList<Point>> out = Kmeans.tfidf(sample, 3);
		//print out coordinates in each cluster
		for(int i = 0; i < out.size(); i++) {   
		    for(int j = 0; j < out.get(i).size();j++){
		    	System.out.println(Arrays.toString(out.get(i).get(j).coordinates));
		    }
		    System.out.println("-------------");
		}  
		//print out index of each cluster
		for(int i = 0; i < out.size(); i++) {   
		    for(int j = 0; j < out.get(i).size();j++){
		    	System.out.print(out.get(i).get(j).index);
		    	System.out.print(", ");
		    }
		    System.out.print("\n");
		}  
	}
}
