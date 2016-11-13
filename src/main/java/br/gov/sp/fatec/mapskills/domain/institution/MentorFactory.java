package br.gov.sp.fatec.mapskills.domain.institution;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserFactory;

@Component
public class MentorFactory implements UserFactory {

	@Override
	public Mentor create(User user) {
		return (Mentor) user;
	}

}