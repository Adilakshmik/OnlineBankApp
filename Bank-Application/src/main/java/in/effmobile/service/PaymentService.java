package in.effmobile.service;

import org.springframework.stereotype.Service;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.entity.PaymentEntity;


public interface PaymentService {
	
	public PaymentEntity savePayment(PaymentEntity payment) throws Exception;
	public  void transferFunds(Long fromAccountNum, Long toAccountNum, Double amount) throws Exception;
	public String createPaymentLink( CustomerEntity customer,Long fromAccountNum, Long toAccountNum,Double amount);
	public PaymentEntity verifyPayment(PaymentEntity payment) throws Exception;	
}
