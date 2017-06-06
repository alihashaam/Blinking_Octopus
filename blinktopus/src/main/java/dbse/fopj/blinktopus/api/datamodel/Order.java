package dbse.fopj.blinktopus.api.datamodel;

import java.util.Date;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author urmikl18 Schema for Order relation
 */
public class Order extends Tuple {
	private long orderKey;
	private long custKey;
	private char orderStatus;
	private double totalPrice;
	private Date orderDate;
	@Length(max = 15)
	private String orderPriority;
	@Length(max = 15)
	private String clerk;
	private int shipPriority;
	@Length(max = 79)
	private String comment;

	public Order() {
	}

	public Order(long orderKey, long custKey, char orderStatus, double totalPrice, Date orderDate, String orderPriority, String clerk,
			int shipPriority, String comment) {
		this.table = "Order";
		this.orderKey = orderKey;
		this.custKey=custKey;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.orderPriority = orderPriority;
		this.clerk = clerk;
		this.shipPriority = shipPriority;
		this.comment = comment;
	}

	@JsonProperty
	public long getOrderKey() {
		return orderKey;
	}
	
	@JsonProperty
	public long getCustKey() {
		return custKey;
	}

	@JsonProperty
	public char getOrderStatus() {
		return orderStatus;
	}

	@JsonProperty
	public double getTotalPrice() {
		return totalPrice;
	}

	@JsonProperty
	public Date getOrderDate() {
		return orderDate;
	}

	@JsonProperty
	public String getOrderPriority() {
		return orderPriority;
	}

	@JsonProperty
	public String getClerk() {
		return clerk;
	}

	@JsonProperty
	public int getShipPriority() {
		return shipPriority;
	}

	@JsonProperty
	public String getComment() {
		return comment;
	}

}
