package cacheapi;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import sql.Person;

public class CacheTransactionExample {

	public static void main(String[] args) {
		Ignition.setClientMode(true);
		System.setProperty("IGNITE_HOME", "C:\\Software\\apache-ignite-2.7.6-bin");
		try(Ignite ignite = Ignition.start("examples/config/example-ignite.xml")){
			
			CacheConfiguration<Long, Person> personCfg = new CacheConfiguration<Long, Person>("personCache");
			personCfg.setCacheMode(CacheMode.PARTITIONED);
			personCfg.setIndexedTypes(Long.class, Person.class);
			personCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			
			IgniteCache<Long, Person> personCache = ignite.getOrCreateCache(personCfg);
			
			Person p1 = new Person(1, 100L, "Stephen", 2000);
			personCache.put(p1.getId(), p1);
			
			//Start a transaction and update the salary of the person
			try(Transaction tx = ignite.transactions().txStart(
					TransactionConcurrency.PESSIMISTIC, TransactionIsolation.READ_COMMITTED)){
				Person p = personCache.get(1L);
				p.setSalary(99999);
				personCache.put(1L, p);
				
				//Commit transaction to distributed cache
				tx.commit();
			}
			System.out.println("Person after salary update: " + personCache.get(1L));
		}
	}
}
