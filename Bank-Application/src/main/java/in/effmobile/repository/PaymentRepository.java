package in.effmobile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity,Integer>{

	public PaymentEntity findByPaymentId(String paymentId);

}
