package computegrid;

import java.util.Arrays;
import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class DistributedComputeExample {

	public static void main(String[] args) throws InterruptedException {
		Ignition.setClientMode(true);
		System.setProperty("IGNITE_HOME", "C:\\Software\\apache-ignite-2.7.6-bin");
		try(Ignite ignite = Ignition.start("examples/config/example-ignite.xml")){
			
			//1. Split a sentence into words
			//2. Pass each word to closure.
			//3. Execute each closure on different cluster members.
			
			//while(true) {
				Collection<Integer> res = ignite.compute().apply(
						(String word) -> {
							System.out.println("Counting characters in word '" + word + "'");
							return word.length();
						},
						Arrays.asList("This is my long sentence".split(" "))
				);
				
				//Add all word lengths received from cluster nodes
				int total = res.stream().mapToInt(Integer::intValue).sum();
				System.out.println("Total characters: " + total);
				
				//Thread.sleep(2000); } 
				//Uncomment the while loop to process stream of characters. Try to close 1 of the cluster nodes, 
				//and see the other one do all the processing
		}
		
	}
}
