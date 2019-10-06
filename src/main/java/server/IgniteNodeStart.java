package server;

import org.apache.ignite.Ignition;

public class IgniteNodeStart {

	public static void main(String[] args) {
		//Can be set runtime arguments as well, ie -DIGNITE_HOME
		System.setProperty("IGNITE_HOME", "C:\\Software\\apache-ignite-2.7.6-bin");
		//Programatically starting the nodes. Can be started from command line as well.
		Ignition.start("examples/config/example-ignite.xml");
	}
}
