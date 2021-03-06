/*import java.io.IOException;
import java.util.ArrayList;*/
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*import dist.AbstractConditionalDistribution;

 import func.FunctionApproximater;
 import func.KMeansClusterer;
 import func.NeuralNetworkClassifier;
 import shared.DataSet;
 import shared.DataSetWriter;
 import shared.filt.IndependentComponentAnalysis;
 import shared.filt.InsignificantComponentAnalysis;
 import shared.filt.LabelSplitFilter;
 import shared.filt.PrincipalComponentAnalysis;
 import shared.filt.RandomizedProjectionFilter;
 import shared.filt.ReversibleFilter;
 import shared.reader.ArffDataSetReader;*/
import shared.DataSet;
import shared.filt.LabelSplitFilter;
import shared.reader.ArffDataSetReader;
//import shared.reader.CSVDataSetReader;
import shared.reader.CSVDataSetReader;

public class ProjectRunner {
	private String baseDir = "data/";

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String reducedDir = "data/reduced/";
		String clustReducedDir = "data/clustered-reduced/";
		String[] reduced = { "_pca", "_ica", "_insig", "_rp" };
		String[] clustered = { "_kmeans", "_emax" };
		String[] setNames = { "hd" };
		int iterations = 4000;

		// numbers for the ThreadPoolExecutor
		int minThreads = 1;
		int maxThreads = 4;
		long keepAlive = 10;

		LinkedBlockingQueue<Runnable> q = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(minThreads, maxThreads, keepAlive, TimeUnit.SECONDS, q);
		int numTasks = 0;
		LabelSplitFilter lsl = new LabelSplitFilter();
		for (String setName : setNames) {
			for (String reducer : reduced) {
				try {
					// pull in the reduced data set
					DataSet d = (new CSVDataSetReader(reducedDir + setName + reducer + ".csv")).read();
					lsl.filter(d);
					tpe.submit(new NeuralNetTrainer(iterations, d, reducer, setName));
					numTasks++;
					// pull in the reduced->clustered data sets
					for (String clusterer : clustered) {
						DataSet a = (new ArffDataSetReader(clustReducedDir + setName + clusterer + reducer + ".arff"))
								.read();
						lsl.filter(a);
						tpe.submit(new NeuralNetTrainer(iterations, a, clusterer + reducer, setName));
						numTasks++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		int i = 0;
		while (tpe.getCompletedTaskCount() < numTasks) {
			// System.out.println("Fuck java");
			i++;
		}
		tpe.shutdown();
		while (!tpe.awaitTermination(60, TimeUnit.SECONDS)) {
			System.out.println(".");
		}
		System.out.println("Done\n========");

		System.out.println(i);

	}

	/**
	 * Not in use any more, but kept around for the filtering code as a
	 * reference
	 * 
	 * @author chronon
	 * 
	 */
	/*
	 * private static class DataSetWorker { // static variables private static
	 * final String reducedDir = "data/reduced/"; private static final String
	 * clustReducedDir = "data/creduced/"; private static final String[] reduced
	 * = {"_pca.csv", "_ica.csv", "_insig.csv", "_rp.csv"}; private static final
	 * String[] clustered = {"_kmeans", "_emax"};
	 * 
	 * // the array of DataSets corresponding to the mountain of nnets we need
	 * to train private DataSet clean;
	 * 
	 * private String setName; private final int toKeep = 5;
	 * 
	 * 
	 * public DataSetWorker(String setName) { this.setName = setName; }
	 * 
	 * public void reduce() { // Add all the filters we need
	 * ArrayList<Tuple<ReversibleFilter,String>> filters = new
	 * ArrayList<Tuple<ReversibleFilter,String>>(); filters.add(new
	 * Tuple<ReversibleFilter, String>(new PrincipalComponentAnalysis(clean),
	 * "_pca.csv")); filters.add(new Tuple<ReversibleFilter, String>(new
	 * IndependentComponentAnalysis(clean), "_ica.csv")); filters.add(new
	 * Tuple<ReversibleFilter, String>(new RandomizedProjectionFilter(toKeep,
	 * clean.get(0).size()), "_insig.csv")); filters.add(new
	 * Tuple<ReversibleFilter, String>(new
	 * InsignificantComponentAnalysis(clean), "_rp.csv")); for
	 * (Tuple<ReversibleFilter, String> tup : filters) { ReversibleFilter filter
	 * = tup.fst(); String ext = tup.snd();
	 * 
	 * filter.filter(clean); DataSetWriter wr = new DataSetWriter(clean,
	 * reducedDir+setName+ext); try { wr.write(); } catch (IOException e) {
	 * e.printStackTrace(); } filter.reverse(clean); } }
	 * 
	 * 
	 * }
	 * 
	 * public static class Tuple<X, Y> { private final X fst; private final Y
	 * snd; public Tuple(X x, Y y) { this.fst = x; this.snd = y; }
	 * 
	 * public X fst() { return this.fst; }
	 * 
	 * public Y snd() { return this.snd; } }
	 */
}
