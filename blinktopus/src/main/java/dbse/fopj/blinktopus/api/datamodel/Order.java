package dbse.fopj.blinktopus.api.datamodel;

import java.util.Date;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Represents Orders table from TPC-H benchmark dataset.
 * 
 * @author Pavlo Shevchenko (urmikl18)
 *
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

	/**
	 * Default constructor
	 */
	public Order() {
	}

	/**
	 * Creates a tuple of type Order, for parameters' type information check http://www.tpc.org/tpc_documents_current_versions/pdf/tpc-h_v2.17.2.pdf
	 */
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

	/**
	 * 
	 * @return orderKey
	 */
	@JsonProperty
	public long getOrderKey() {
		return orderKey;
	}
	
	/**
	 * 
	 * @return custKey
	 */
	@JsonProperty
	public long getCustKey() {
		return custKey;
	}

	/**
	 * 
	 * @return orderStatus
	 */
	@JsonProperty
	public char getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 
	 * @return totalPrice
	 */
	@JsonProperty
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 
	 * @return orderDate
	 */
	@JsonProperty
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * 
	 * @return orderPriority
	 */
	@JsonProperty
	public String getOrderPriority() {
		return orderPriority;
	}

	/**
	 * 
	 * @return clerk
	 */
	@JsonProperty
	public String getClerk() {
		return clerk;
	}

	/**
	 * 
	 * @return shipPriority
	 */
	@JsonProperty
	public int getShipPriority() {
		return shipPriority;
	}

	/**
	 * 
	 * @return comment
	 */
	@JsonProperty
	public String getComment() {
		return comment;
	}

}
