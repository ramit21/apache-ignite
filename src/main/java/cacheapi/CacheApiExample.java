package cacheapi;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;

import sql.Organization;

public class CacheApiExample {

	public static void main(String[] args) {
		Ignition.setClientMode(true);
		System.setProperty("IGNITE_HOME", "C:\\Software\\apache-ignite-2.7.6-bin");
		try(Ignite ignite = Ignition.start("examples/config/example-ignite.xml")){
			
			CacheConfiguration<Long, Organization> orgCfg = new CacheConfiguration<Long, Organization>("orgCache");
			orgCfg.setCacheMode(CacheMode.REPLICATED);
			orgCfg.setIndexedTypes(Long.class, Organization.class);
			
			IgniteCache<Long, Organization> orgCache = ignite.getOrCreateCache(orgCfg);
			
			//Put some data into cache
			Organization org1 = new Organization(101, "RamitLtd");
			//orgCache.put(org1.getId(), org1);
			
			//Fetch data from cache using Asynchronous mode
			IgniteCache<Long, Organization> asyncCache = orgCache.withAsync();
			
			//Asynchronoulsy store data in distributed cache
			asyncCache.getAndPut(org1.getId(), org1);
			
			//Get future for above operation
			IgniteFuture<Organization> fut = asyncCache.future();
			
			//Asynchronoulsy listen for the operation to complete
			fut.listen(f-> System.out.println("Retrieved cache value = " + f.get()));
			
			//Wait for async notification to happen
			fut.get();
		}
	}
}
