package sql;

import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;

public class CacheQueryExample {

	public static void main(String[] args) {
		Ignition.setClientMode(true); //Do start server nodes from IgniteStart.java first
		System.setProperty("IGNITE_HOME", "C:\\Software\\apache-ignite-2.7.6-bin");
		try(Ignite ignite = Ignition.start("examples/config/example-ignite.xml")){
			//Configure a REPLICATED cache to store Organization data
			CacheConfiguration<Long, Organization> orgCfg = new CacheConfiguration<Long, Organization>("orgCache");
			orgCfg.setCacheMode(CacheMode.REPLICATED);
			orgCfg.setIndexedTypes(Long.class, Organization.class);
			
			IgniteCache<Long, Organization> orgCache = ignite.getOrCreateCache(orgCfg);
			
			//Configure a PARTITIONED cache to store Person data
			CacheConfiguration<Long, Person> personCfg = new CacheConfiguration<Long, Person>("personCache");
			personCfg.setCacheMode(CacheMode.PARTITIONED);
			personCfg.setIndexedTypes(Long.class, Person.class);
			
			IgniteCache<Long, Person> personCache = ignite.getOrCreateCache(personCfg);
			
			//Put some data into cache
			Organization org1 = new Organization(101, "RamitLtd");
			Organization org2 = new Organization(202, "RamitCorp");
			orgCache.put(org1.getId(), org1);
			orgCache.put(org2.getId(), org2);
			
			Person p1 = new Person(1, org1.getId(), "Stephen", 2000);
			Person p2 = new Person(2, org1.getId(), "Muiris", 1000);
			Person p3 = new Person(3, org2.getId(), "Alan Dent", 1500);
			personCache.put(p1.getId(), p1);
			personCache.put(p2.getId(), p2);
			personCache.put(p3.getId(), p3);
			
			//Get names of all employees of a specific organization
			String sql = "select p.name from Person as p, \"orgCache\".Organization as org "+
						 "where p.orgId = org.id and org.name = ?";
			
			//Execute the query and obtain the query result cursor.
			QueryCursor<List<?>> cursor = personCache.query(new SqlFieldsQuery(sql).setArgs("RamitLtd"));
			
			System.out.println(cursor.getAll());
			
		}
	}
}
