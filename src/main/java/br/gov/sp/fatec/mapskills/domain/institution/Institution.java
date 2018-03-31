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

import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A classe {@link Institution} representa uma unidade de ensino.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.INSTITUTION")
@ToString(of = {"code", "company", "level"})
@EqualsAndHashCode(of = {"code"})
public class Institution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CODE", nullable = false, unique = true, length = 3)
	private String code;
	
	@Column(name = "CNPJ", nullable = false, unique = true)
	private Long cnpj;
	
	@Column(name = "COMPANY", nullable = false)
	private String company;

	@Column(name = "CITY", nullable = false)
	private String city;
	
	@Enumerated
	@Column(name = "LEVEL", nullable = false)
	private InstitutionLevel level;
		
	@ManyToOne
	@JoinColumn(name = "ID_GAME_THEME")
	private GameTheme gameTheme = null;
	
	@OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Course> courses = new LinkedList<>();
	
	@OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Mentor> mentors = new LinkedList<>();

	@SuppressWarnings("unused")
	private Institution() {
		this(null, null, null, null, null, Collections.emptyList());
	}
	
	public Institution(final String code, final Long cnpj, final String company, final InstitutionLevel level,
			final String city, final List<Mentor> mentors) {
		this.code = code;
		this.cnpj = cnpj;
		this.company = company;
		this.level = level;
		this.city = city;
		mentors.stream().forEach(this::addMentor);
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
	
	public void updateGameTheme(final GameTheme theme) {
		this.gameTheme = theme;
	}
	
	public Long getGameThemeId() {
		return ObjectUtils.isEmpty(gameTheme) ? null : gameTheme.getId();
	}
	
	public void update(final Institution updateInstitution) {
		this.code = updateInstitution.getCode();
		this.cnpj = updateInstitution.getCnpj();
		this.company = updateInstitution.getCompany();
		this.level = updateInstitution.getLevel();
		this.city = updateInstitution.getCity();
		updateMentors(updateInstitution.getMentors());
	}
	
	public void updateCourse(final Long courseId, final Course courseUpdated) {
		final Optional<Course> course = getCourseById(courseId);
		if (course.isPresent()) {
			course.get().update(courseUpdated);			
		}
	}
	
	private void updateMentors(final List<Mentor> updateMentors) {
		updateMentors.stream().forEach(updateMentor -> {
			final Optional<Mentor> mentor = getMentorByUsername(updateMentor.getUsername());
			if (mentor.isPresent()) {
				mentor.get().update(updateMentor);
				return;
			}
			addMentor(updateMentor);
		});
	}
	
	private Optional<Course> getCourseById(final Long courseId) {
		return courses.stream().filter(course -> course.getId().equals(courseId)).findFirst();
	}	
	
	private Optional<Mentor> getMentorByUsername(final String username) {
		return mentors.stream().filter(mentor -> mentor.getUsername().equals(username)).findFirst();
	}
}