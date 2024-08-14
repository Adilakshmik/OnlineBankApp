package in.effmobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.effmobile.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long>{
	
	public CustomerEntity findByUserName(String cuname);
	public CustomerEntity findByAccountNum(Long fromAccountNum);

}
