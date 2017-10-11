/*
 * @(#)Institution.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import lombok.Getter;

/**
 * 
 * A classe {@link Institution}
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.INSTITUTION")
public class Institution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CODE", nullable = false, unique = true, length = 5)
	private String code;
	
	@Column(name = "CNPJ", nullable = true, unique = true)
	private Long cnpj;
	
	@Column(name = "COMPANY", nullable = true)
	private String company;

	@Column(name = "CITY", nullable = true)
	private String city;
	
	@Enumerated
	@Column(name = "LEVEL", nullable = true)
	private InstitutionLevel level;
		
	@ManyToOne
	@JoinColumn(name = "ID_GAME_THEME")
	private GameTheme gameTheme;
	
	@OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Course> courses = new LinkedList<>();
	
	@OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Mentor> mentors = new LinkedList<>();

	@SuppressWarnings("unused")
	private Institution() {
		this(null, null, null, null, null, Collections.emptyList(), Collections.emptyList(), null);
	}

	public Institution (final String code, final Long cnpj, final String company, final InstitutionLevel level,
			final String city, final List<Mentor> mentors, final List<Course> courses, final GameTheme gameTheme) {
		this.code = code;
		this.cnpj = cnpj;
		this.company = company;
		this.level = level;
		this.city = city;
		addAllMentors(mentors);
		addAllCourses(courses);
		this.gameTheme = gameTheme;
	}
		
	public void addAllCourses(final List<Course> courses) {
		this.courses.clear();
		courses.stream().forEach(course -> {
			addCourse(course);
		});
	}
		
	public List<Mentor> getMentors() {
		return Collections.unmodifiableList(mentors);
	}
	
	public List<Course> getCourses() {
		return Collections.unmodifiableList(courses);
	}

	public void addMentor(final Mentor newMentor) {
		newMentor.setInstitution(this);
		this.mentors.add(newMentor);
	}
	
	public void addCourse(final Course newCourse) {
		newCourse.setInstitution(this);
		this.courses.add(newCourse);
	}
	
	public Course getCourseByCode(final String code) {
		final Optional<Course> courseFind = courses.stream().filter(course -> course.getCode().equals(code)).findFirst();
		return courseFind.isPresent() ? courseFind.get() : null;
	}
	
	public Mentor getMentorByUsername(final String username) {
		Optional<Mentor> mentorFind = mentors.stream().filter(mentor -> mentor.getUsername().equals(username)).findFirst();
		return mentorFind.isPresent() ? mentorFind.get() : null;
	}
	
	public void updateGameTheme(final GameTheme theme) {
		this.gameTheme = theme;
	}
	
	public void update(final Institution updateInstitution) {
		this.code = updateInstitution.getCode();
		this.cnpj = updateInstitution.getCnpj();
		this.company = updateInstitution.getCompany();
		this.level = updateInstitution.getLevel();
		this.city = updateInstitution.getCity();
	}
	
	private void addAllMentors(final List<Mentor> mentors) {
		if(!CollectionUtils.isEmpty(mentors)) {
			mentors.stream().forEach(mentor -> {
				addMentor(mentor);
			});			
		}
	}
}