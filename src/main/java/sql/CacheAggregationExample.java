package sql;

import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;

//First execute CacheQueryExample.java to insert data into cache
public class CacheAggregationExample {

	public static void main(String[] args) {
		Ignition.setClientMode(true);
		System.setProperty("IGNITE_HOME", "C:\\Software\\apache-ignite-2.7.6-bin");
		try(Ignite ignite = Ignition.start("examples/config/example-ignite.xml")){
			CacheConfiguration<Long, Organization> orgCfg = new CacheConfiguration<Long, Organization>("orgCache");
			orgCfg.setCacheMode(CacheMode.REPLICATED);
			orgCfg.setIndexedTypes(Long.class, Organization.class);
			
			IgniteCache<Long, Organization> orgCache = ignite.getOrCreateCache(orgCfg);
			
			//Configure a PARTITIONED cache to store Person data
			CacheConfiguration<Long, Person> personCfg = new CacheConfiguration<Long, Person>("personCache");
			personCfg.setCacheMode(CacheMode.PARTITIONED);
			personCfg.setIndexedTypes(Long.class, Person.class);
			
			IgniteCache<Long, Person> personCache = ignite.getOrCreateCache(personCfg);
			
			//Calculate min, max, average salary of RamitLtd employees
			String sql = "select min(salary), max(salary), avg(salary) "
					+ "from Person p, \"orgCache\".Organization as org "
					+ "where p.orgId = org.id and org.name = ?";
			
			QueryCursor<List<?>> cursor =  personCache.query(new SqlFieldsQuery(sql).setArgs("RamitLtd"));
			System.out.println(cursor.getAll());
			
			//Calculate total no. of employees working for each organization using GROUP BY
			sql = "select org.name, count(p.id) "
					+ "from Person p, \"orgCache\".Organization as org "
					+ "where p.orgId = org.id group by org.name";
			
			cursor =  personCache.query(new SqlFieldsQuery(sql));
			System.out.println(cursor.getAll());
		}
	}
}
