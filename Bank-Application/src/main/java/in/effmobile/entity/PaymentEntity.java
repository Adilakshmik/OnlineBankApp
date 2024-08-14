package in.effmobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="payment_entity")
public class PaymentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    private Double amount;
	private String name;
    private String paymentStatus;
    private String paymentId; // This is the ID from Razorpay
    private Long fromAccountNum; 
    private Long toAccountNum; 
    private Long phoneNum;
	
    
    
	
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(Long phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amountAfterDebit) {
		this.amount = amountAfterDebit;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public Long getFromAccountNum() {
		return fromAccountNum;
	}
	public void setFromAccountNum(Long fromAccountNum) {
		this.fromAccountNum = fromAccountNum;
	}
	public Long getToAccountNum() {
		return toAccountNum;
	}
	public void setToAccountNum(Long toAccountNum) {
		this.toAccountNum = toAccountNum;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	}