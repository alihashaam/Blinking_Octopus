package dbse.fopj.blinktopus.api.sv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author urmikl18
 * Class represents Storage View. Stores relevant information.
 */
public class SV {
	
	private String id;
	private String type;
	private String table;
	private String attr;
	private double lower;
	private double higher;
	private int size;

	
	public SV() {
	}

	public SV(String id, String type, String table, String attr, double lower, double higher) {
		this.id = id;
		this.type = type;
		this.table=table;
		this.attr = attr;
		this.lower = lower;
		this.higher = higher;
	}
	
	@JsonProperty
	public String getId()
	{
		return id;
	}
	
	@JsonProperty
	public String getType()
	{
		return type;
	}
	
	@JsonProperty
	public String getTable()
	{
		return table;
	}
	
	@JsonProperty
	public String getAttr()
	{
		return attr;
	}
	
	@JsonProperty
	public double getLower()
	{
		return lower;
	}
	
	@JsonProperty
	public double getHigher()
	{
		return higher;
	}
	
	public int getSize()
	{
		return size;
	}
	
	protected void setSize(int size)
	{
		this.size=size;
	}

}
