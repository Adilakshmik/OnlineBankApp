package in.effmobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.entity.PaymentEntity;
import in.effmobile.repository.CustomerRepository;
import jakarta.transaction.Transactional;

@Service
public class BankingServicesImpl implements BankingServices{

	
	@Autowired
	private CustomerRepository customerRepo;
	
	
	  @Override
	    public boolean saveCustomer(CustomerEntity customer) {
	        if (customer.getAccountNum() == null) {
	            throw new IllegalArgumentException("Account number cannot be null");
	        }
	        CustomerEntity savedCustomer = customerRepo.save(customer);
	        return savedCustomer != null;
	    }
	  
	  
	  
	  @Transactional
	    public  void transferFunds(Long fromAccountNum, Long toAccountNum, Double amount) throws Exception {
	        CustomerEntity sender = customerRepo.findByAccountNum(fromAccountNum);
	        if(sender == null) {
	        	System.out.println("sender not found");
	        }
	        CustomerEntity receiver = customerRepo.findByAccountNum(toAccountNum);
	        if(receiver == null) {
	        	System.out.println("receiver not found");
	        }     

	        // Transactional behavior to ensure atomicity
	        synchronized (this) {
	            sender.debitBalance(amount);
	            receiver.creditBalance(amount);
	            customerRepo.save(sender);
	            customerRepo.save(receiver);
	        }
	    }

	

}
