package sql;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Person implements Serializable{

	private static final long serialVersionUID = 6596431929345782386L;
	
	@QuerySqlField(index=true)
	private long id;
	
	@QuerySqlField(index=true)
	private long orgId;
	
	@QuerySqlField	    //name not indexed
	private String name;
	
	@QuerySqlField(index=true) 
	private double salary;
	
	public Person(long id, long orgId, String name, double salary){
		this.id = id;
		this.orgId = orgId;
		this.name = name;
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "Person id = " + id + ", orgId = "+orgId + ", name = "+ name + ", salary = "+salary;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}
