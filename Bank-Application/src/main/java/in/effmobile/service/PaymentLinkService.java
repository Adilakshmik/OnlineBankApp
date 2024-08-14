package in.effmobile.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PaymentLinkService {
	
	@Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final String PAYMENT_LINK_API_URL = "https://api.razorpay.com/v1/payment_links";

    public String createPaymentLink(double amount) {
        RestTemplate restTemplate = new RestTemplate();
        
        // Prepare the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(keyId, keySecret);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        // Prepare the request body
        JSONObject body = new JSONObject();
        body.put("amount", amount * 100); // amount in paise
        body.put("currency", "INR");
        
        // Add customer details (optional, based on documentation)
        JSONObject customer = new JSONObject();
        
        // Add other customer details like email, contact, etc. if necessary
       

        JSONObject notify = new JSONObject();
        notify.put("email", true);
        notify.put("sms", false);
        body.put("notify", notify);

        body.put("reminder_enable", true);

        HttpEntity<String> requestEntity = new HttpEntity<>(body.toString(), headers);

        // Send the request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                PAYMENT_LINK_API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Handle the response
        if (responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.CREATED) {
            JSONObject response = new JSONObject(responseEntity.getBody());
            return response.getString("short_url"); // Return the payment link URL
        } else {
            throw new RuntimeException("Failed to create payment link: " + responseEntity.getBody());
        }
    }

	

}
