/*
 * @(#)Institution.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * A classe {@link Institution}
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Setter
@Entity
@Table(name = "MAPSKILLS.INSTITUTION")
public class Institution implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INS_ID")
	private Long id;
	
	@Column(name = "INS_CODE", nullable = false, unique = true, length = 10)
	private String code;
	
	@Column(name = "INS_CNPJ", nullable = true, unique = true)
	private String cnpj;
	
	@Column(name = "INS_COMPANY", nullable = true)
	private String company;
	
	@Column(name = "INS_LEVEL", nullable = true)
	@Enumerated(value = EnumType.ORDINAL)
	private InstitutionLevel level;
	
	@Column(name = "INS_CITY", nullable = true)
	private String city;
	
	@Column(name = "GTH_ID")
	private Long gameThemeId;
	
	@OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Course> courses = new LinkedList<>();
	
	@OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Mentor> mentors = new LinkedList<>();

	@SuppressWarnings("unused")
	private Institution() {
		this(null, null, null, null, null, Collections.emptyList(), null);
	}

	public Institution (final String code, final String cnpj, final String company,
			final InstitutionLevel level, final String city, final List<Mentor> mentors, final Long gameThemeId) {
		this.code = code;
		this.cnpj = cnpj;
		this.company = company;
		this.level = level;
		this.city = city;
		addAllMentors(mentors);
		this.gameThemeId = gameThemeId;
	}
		
	public void setCourses(final List<Course> courses) {
		this.courses.clear();
		courses.stream().forEach(course -> {
			course.setInstitution(this);
			this.courses.add(course);
		});
	}
	
	public void setMentors(final List<Mentor> mentors) {
		this.mentors.clear();
		addAllMentors(mentors);
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
	
	private void addAllMentors(final List<Mentor> mentors) {
		if(!CollectionUtils.isEmpty(mentors)) {
			mentors.stream().forEach(mentor -> {
				mentor.setInstitution(this);
				this.mentors.add(mentor);
			});			
		}
	}
	
}
