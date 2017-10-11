/*
 * @(#)Course.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import java.util.LinkedList;
import java.util.List;

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

import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * A classe {@link Course} representa um curso ao qual
 * uma intituicao pertence.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.COURSE")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PERIOD")
	@Enumerated
	private Period period;
	
	@Setter
	@ManyToOne
	@JoinColumn(name = "ID_INSTITUTION")
	private Institution institution;
	
	@OneToMany(mappedBy = "course")
	private final List<Student> students = new LinkedList<>();
	
	@SuppressWarnings("unused")
	private Course() {
		this(null, null, null);
	}
	
	public Course(final String code, final String name, final Period period) {
		this.code = code;
		this.name = name;
		this.period = period;
	}
		
	public String getNamePeriod() {
		return period.name();
	}
	
	public String getInstitutionCode() {
		return institution.getCode();
	}
	
	public void update(final Course updateCourse) {
		this.code = updateCourse.getCode();
		this.name = updateCourse.getName();
		this.period = updateCourse.getPeriod();
	}

}
