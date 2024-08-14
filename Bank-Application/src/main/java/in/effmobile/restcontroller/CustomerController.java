package in.effmobile.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.service.BankingServices;
import in.effmobile.service.JwtService;

@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private BankingServices service;
	
	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwt;
	
	@PostMapping("/customer")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerEntity customer){
        try {
            if (customer.getAccountNum() == null) {
                return new ResponseEntity<>("Account number cannot be null.", HttpStatus.BAD_REQUEST);
            }
            String encodedPwd = pwdEncoder.encode(customer.getPassword());
    		customer.setPassword(encodedPwd);
    		boolean savedCustomer = service.saveCustomer(customer);
            if (savedCustomer) {
                return new ResponseEntity<>("Customer created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Customer not created.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
