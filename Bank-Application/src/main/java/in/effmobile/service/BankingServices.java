package in.effmobile.service;

import org.springframework.stereotype.Service;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.entity.PaymentEntity;


public interface BankingServices {
	
public boolean saveCustomer(CustomerEntity customer);
public  void transferFunds(Long fromAccountNum, Long toAccountNum, Double double1) throws Exception;

		
	
	

}
