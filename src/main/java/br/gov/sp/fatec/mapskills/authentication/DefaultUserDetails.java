/*
 * @(#)DefaultUserDetails.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.user.Login;
import br.gov.sp.fatec.mapskills.domain.user.UserRepository;
/**
 * 
 * A classe {@link DefaultUserDetails}
 *
 * @author Marcelo
 * @version 1.0 27/01/2017
 */
@Component
public class DefaultUserDetails implements UserDetailsService {
	
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(final String username) {
		final br.gov.sp.fatec.mapskills.domain.user.User user = repository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			final Login login = user.getLogin();
			return new User(login.getUsername(), login.getPassword(), getRoles(user));			
		}
	}
	
	private Collection<GrantedAuthority> getRoles(final br.gov.sp.fatec.mapskills.domain.user.User user) {
		final List<GrantedAuthority> authorities = new ArrayList<>(1);
		authorities.add(new DefaultGrantedAuthority(user.getProfile()));
		return authorities;
	}
	
	@Autowired
	public void setUserRepository(final UserRepository userRepository) {
		this.repository = userRepository;
	}
}