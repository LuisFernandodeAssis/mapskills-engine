/*
 * @(#)DefaultGrantedAuthority.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import org.springframework.security.core.GrantedAuthority;

import br.gov.sp.fatec.mapskills.domain.user.ProfileType;

/**
 * 
 * A classe {@link DefaultGrantedAuthority}
 *
 * @author Marcelo
 * @version 1.0 27/01/2017
 */
public class DefaultGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	private final ProfileType role;
	
	public DefaultGrantedAuthority(final ProfileType role) {
        super();
        this.role = role;
    }

	/** {@inheritDoc} */
	@Override
	public String getAuthority() {
		return "ROLE_" + role.name();
	}
	
	public boolean isAdmin() {
        return role.isAdmin();
    }
}