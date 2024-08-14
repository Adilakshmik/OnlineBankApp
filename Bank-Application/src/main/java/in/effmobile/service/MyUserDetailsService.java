package in.effmobile.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.effmobile.entity.CustomerEntity;
import in.effmobile.repository.CustomerRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository crepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		CustomerEntity c = crepo.findByUserName(username);

		return new User(c.getUserName(), c.getPassword(), Collections.emptyList());

	}

}
