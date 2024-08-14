package in.effmobile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "customer_entity")
public class CustomerEntity {
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_num", nullable = false)
    private Long accountNum;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private Double balance;
    
    @Column(name = "phone_num", nullable = false)
    private Long phoneNum;

    @Column(name = "dob", nullable = false)
    private String dob;
		
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getAccountNum() {
			return accountNum;
		}
		public void setAccountNum(Long accountNum) {
			this.accountNum = accountNum;
		}
		
		public Long getPhoneNum() {
			return phoneNum;
		}
		public void setPhoneNum(Long phoneNum) {
			this.phoneNum = phoneNum;
		}
		
		public Double getBalance() {
			return balance;
		}
		public void setBalance(Double balance) {
			this.balance = balance;
		}
		public String getDob() {
			return dob;
		}
		public void setDob(String dob) {
			this.dob = dob;
		}
		 public void debitBalance(Double amount) throws Exception {
		        this.balance -= amount;
		    }

		 public void creditBalance(Double amount) {
		        this.balance += amount;
		    }
		
		}


