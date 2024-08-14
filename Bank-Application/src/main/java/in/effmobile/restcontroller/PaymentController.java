 package in.effmobile.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.entity.PaymentEntity;
import in.effmobile.service.BankingServices;
import in.effmobile.service.JwtService;
import in.effmobile.service.PaymentLinkService;
import in.effmobile.service.PaymentService;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class PaymentController {
	

	
	@Autowired
	private PaymentService paymentService;
	
	
	
	@PostMapping("/create-link")
	 public ResponseEntity<String> createPaymentLink(@RequestBody CustomerEntity customer,
			                                         @RequestParam Long fromAccountNum,
			                                         @RequestParam Long toAccountNum,
			                                         @RequestParam Double amount) {
		 
		  String paymentLink = paymentService.createPaymentLink(customer, fromAccountNum, toAccountNum, amount);
		 return new ResponseEntity<>(paymentLink,HttpStatus.OK);
	 }


	    
	 @PostMapping(value = "/verify")
	    public ResponseEntity<String> verifyPayment(@RequestBody PaymentEntity payment) throws Exception {
	 PaymentEntity verifiedPayment = paymentService. verifyPayment(payment);  
	 return new ResponseEntity<>("verified successfully",HttpStatus.OK); 
	}
}
	
