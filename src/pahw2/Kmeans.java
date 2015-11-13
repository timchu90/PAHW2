package pahw2;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class Kmeans {
	public static double[] VectorMean(ArrayList<Point> input){
		int vLength = input.get(0).coordinates.length;
		int aLength = input.size();
		double[] meanVector = new double[vLength];
		for(int j = 0; j < vLength;j++){
			meanVector[j] = 0;
		}
		for(int i = 0; i < aLength;i++){
			for(int j = 0; j < vLength;j++){
				meanVector[j] = input.get(i).coordinates[j] + meanVector[j];
			}
		}
		for(int j = 0; j < vLength;j++){
			meanVector[j] = meanVector[j]/input.size();
		}
		return meanVector;
	}
	
	public static class Point{
		public double[] coordinates;
		public int index;
		
		public Point(){
			return;
		}
		public Point(int ind, double[] coor){
			this.coordinates = coor;
			this.index = ind;
		}
	}
	
	public static ArrayList<ArrayList<Point>> tfidf(double[][] input, int clusters){
		EuclideanDistance euclid = new EuclideanDistance();
		//list of centroids
		ArrayList<double[]> centroids = new ArrayList<double[]>();
		//list of list of points
		ArrayList<ArrayList<Point>> output = new ArrayList<ArrayList<Point>>();
		//shuffle input
		ArrayList<Point> data = new ArrayList<Point>();
		for(int i=0; i<input.length;i++){
			data.add(new Point(i,input[i]));
		}
		Collections.shuffle(data);
		
		//init first n centroids
		for(int i = 0;i<clusters;i++){
			//add to list of centroids
			centroids.add(data.get(i).coordinates);
			//add to data matrix
			ArrayList<Point> temp = new ArrayList<Point>();
			temp.add(data.get(i));
			output.add(temp);
		}
		
		for(int i = clusters;i<input.length;i++){
			double minimum = euclid.compute(data.get(0).coordinates,data.get(i).coordinates);
			int targetCluster = 0;
			for(int j = 1; j < clusters; j++){
				//compare each other point to all centroids. 
				//add to cluster of closest centroid
				if(euclid.compute(data.get(j).coordinates,data.get(i).coordinates)<minimum){
					//save minimum
					minimum = euclid.compute(data.get(j).coordinates,data.get(i).coordinates);
					targetCluster = j;
				}
			}
			//add to target cluster
			output.get(targetCluster).add(data.get(i));
		}
		
		//compute mean of each cluster and make new centroid
		for(int i = 0;i<clusters;i++){
			//add to list of centroids
			centroids.set(i,VectorMean(output.get(i)));
		}
		double totalMeanDiff = 1;
		//while total mean difference is not 0, repeat.
		while(totalMeanDiff != 0){
			totalMeanDiff = 0;
			//clear output 
			output = new ArrayList<ArrayList<Point>>();
			for(int i = 0;i<clusters;i++){
				ArrayList<Point> temp = new ArrayList<Point>();
				output.add(temp);
			}
			
			//compare each other point to all centroids
			//add to cluster of closest centroid
			for(int i = 0;i<input.length;i++){
				double minimum = euclid.compute(centroids.get(0),data.get(i).coordinates);
				int targetCluster = 0;
				for(int j = 1; j < clusters; j++){
					if(euclid.compute(centroids.get(j),data.get(i).coordinates)<minimum){
						//save minimum
						minimum = euclid.compute(centroids.get(j),data.get(i).coordinates);
						targetCluster = j;
					}
				}
				//add to target cluster
				output.get(targetCluster).add(data.get(i));
			}
			for(int i = 0;i<clusters;i++){
				//add to list of centroids
				totalMeanDiff = totalMeanDiff 
						+ euclid.compute(centroids.get(i),VectorMean(output.get(i)));
				centroids.set(i,VectorMean(output.get(i)));
			}
			//System.out.println(totalMeanDiff);
		}
		
		return output;
	}
}

