package computegrid;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;

public class ComputeGridExample {
	
	public static void main(String[] args) {
		Ignition.setClientMode(true);
		System.setProperty("IGNITE_HOME", "C:\\Software\\apache-ignite-2.7.6-bin");
		try(Ignite ignite = Ignition.start("examples/config/example-ignite.xml")){
			
			//Broadcast to all nodes, including itself
			//ignite.compute().broadcast(() -> System.out.println("Hello World"));
			
			//Broadcast to remote nodes only
			ClusterGroup rmts = ignite.cluster().forRemotes();
			
			ignite.compute(rmts).broadcast(() -> System.out.println("Hello remotes"));
		
		}
		
		
	}

}
