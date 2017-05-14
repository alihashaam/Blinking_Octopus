package dbse.fopj.blinktopus.api.datamodel;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineItem extends Tuple {
	private long orderKey;
	private int lineNumber;
	private double quantity;
	private double extendedPrice;
	private double discount;
	private double tax;
	private char returnFlag;
	private char lineStatus;
	private Date shipDate;
	private Date commitDate;
	private Date receiptDate;
	@Length(max = 25)
	private String shipInstruct;
	@Length(max = 10)
	private String shipMode;
	@Length(max = 44)
	private String comment;

	public LineItem() {
	}

	public LineItem(long orderKey, int lineNumber, double quantity, double extendedPrice, double discount, double tax,
			char returnFlag, char lineStatus, Date shipDate, Date commitDate, Date receiptDate, String shipInstruct,
			String shipMode, String comment) {
		this.type="LineItem";
		this.orderKey = orderKey;
		this.lineNumber = lineNumber;
		this.quantity = quantity;
		this.extendedPrice = extendedPrice;
		this.discount = discount;
		this.tax = tax;
		this.returnFlag = returnFlag;
		this.lineStatus = lineStatus;
		this.shipDate = shipDate;
		this.commitDate = commitDate;
		this.receiptDate = receiptDate;
		this.shipInstruct = shipInstruct;
		this.shipMode = shipMode;
		this.comment = comment;
	}

	@JsonProperty
	public long getOrderKey() {
		return orderKey;
	}

	@JsonProperty
	public int getLineNumber() {
		return lineNumber;
	}

	@JsonProperty
	public double getQuantity() {
		return quantity;
	}

	@JsonProperty
	public double getExtendedPrice() {
		return extendedPrice;
	}

	@JsonProperty
	public double getDiscount() {
		return discount;
	}

	@JsonProperty
	public double getTax() {
		return tax;
	}

	@JsonProperty
	public char getReturnFlag() {
		return returnFlag;
	}

	@JsonProperty
	public char getLineStatus() {
		return lineStatus;
	}

	@JsonProperty
	public Date getShipDate() {
		return shipDate;
	}

	@JsonProperty
	public Date getCommitDate() {
		return commitDate;
	}

	@JsonProperty
	public Date getReceiptDate() {
		return receiptDate;
	}

	@JsonProperty
	public String getShipInstruct() {
		return shipInstruct;
	}

	@JsonProperty
	public String getShipMode() {
		return shipMode;
	}

	@JsonProperty
	public String getComment() {
		return comment;
	}
}
