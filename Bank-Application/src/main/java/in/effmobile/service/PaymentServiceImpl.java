package in.effmobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.entity.PaymentEntity;
import in.effmobile.repository.CustomerRepository;
import in.effmobile.repository.PaymentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwt;
	
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private BankingServices customerService;
	
	@Autowired
	private PaymentLinkService paymentLinkService;
	
	private RazorpayClient client;

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;


	@PostConstruct
	public void init() throws RazorpayException {
	    this.client = new RazorpayClient(keyId, keySecret);
	}
	
	@Override
	public String createPaymentLink( CustomerEntity customer,Long fromAccountNum, Long toAccountNum,Double amount) {
       // Authenticate the user
       UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customer.getUserName(), customer.getPassword());

       try {
           Authentication authenticate = authManager.authenticate(token);

              if (authenticate.isAuthenticated()) {
               try {
           // Create a payment link
             if(customer.getBalance()!= 0 && customer.getBalance()> amount) {
              String paymentLink = paymentLinkService.createPaymentLink(amount);
                 if (paymentLink != null) {
                customerService.transferFunds(fromAccountNum, toAccountNum, amount);
                    return paymentLink;

                  } else {
                   return "Failed to create payment link";
                 }
                 }else {
                    return "InsufficientFunds";
           }
                 } catch (Exception e) {
                return "Error creating payment link: " + e.getMessage();
         }
            } else {
              return "Invalid Credentials";
           }
                  } catch (Exception e) {
                return "Authentication Error: " + e.getMessage();
                }
     }
	



	@Override
	public PaymentEntity verifyPayment(PaymentEntity payment) throws Exception {
		try {
            // Print payment ID to ensure it is being passed correctly
            System.out.println("Fetching payment with ID: " + payment.getPaymentId());
            String paymentId= payment.getPaymentId();
            Payment fetchedPayment = client.Payments.fetch(paymentId);

            // Check if fetchedPayment is null
            if (fetchedPayment == null) {
                throw new RazorpayException("Payment with ID " + paymentId + " does not exist.");
            }

            // Continue with further processing
            if (fetchedPayment != null && fetchedPayment.get("id").equals(paymentId)) {
                String paymentStatus = fetchedPayment.get("status");
                System.out.println("Fetched Payment Status: " + paymentStatus);

                if ("captured".equals(paymentStatus) || "success".equals(paymentStatus)) {
                    payment.setPaymentId(paymentId);
                    payment.setPaymentStatus("SUCCESS");
                    
                } else {
                    payment.setPaymentStatus("FAILED");
                }
            } 

            PaymentEntity savePayment = savePayment(payment);
            return savePayment;

        } catch (RazorpayException e) {
            System.err.println("Razorpay Exception: " + e.getMessage());
            payment.setPaymentStatus("FAILED");
            PaymentEntity savePayment = savePayment(payment);
            return savePayment;
        } 
    }


	

    
    @Transactional
    public  void transferFunds(Long fromAccountNum, Long toAccountNum, Double double1) throws Exception {
        CustomerEntity sender = customerRepo.findById(fromAccountNum)
                .orElseThrow(() -> new Exception("Sender not found"));
        CustomerEntity receiver = customerRepo.findById(toAccountNum)
                .orElseThrow(() -> new Exception("Receiver not found"));

        // Transactional behavior to ensure atomicity
        synchronized (this) {
            sender.debitBalance(double1);
            receiver.creditBalance(double1);
            customerRepo.save(sender);
            customerRepo.save(receiver);
        }
    }
    
    public PaymentEntity savePayment(PaymentEntity payment) throws Exception {
        return paymentRepo.save(payment);
    }


    
    
    
    

}
