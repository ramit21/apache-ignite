package sql;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Organization implements Serializable{

	private static final long serialVersionUID = -8802776585730657937L;

	@QuerySqlField(index=true) //indexing the Org id
	private long id;
	
	@QuerySqlField(index=true) 
	private String name;
	
	public Organization(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Organization id = "+id+", name = " + name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
