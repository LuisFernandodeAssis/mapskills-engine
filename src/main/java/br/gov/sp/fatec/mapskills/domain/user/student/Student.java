/*
 * @(#)Student.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.user.Login;
import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "MAPSKILLS.STUDENT")
@PrimaryKeyJoinColumn(name = "ID_USER")
@DiscriminatorValue("2")
public class Student extends User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Embedded
	private AcademicRegistry ra;
	
	@Column(name = "PHONE", nullable = false)
	private String phone;
	
	@Column(name = "COMPLETED")
	private Boolean completed;
	
	@Setter
	@ManyToOne
	@JoinColumn(name = "ID_COURSE")
	private Course course;
	
	@SuppressWarnings("unused")
	private Student() throws MapSkillsException {
		this("0000000000000", null, null, null, null);
	}
			
	public Student(final String ra, final String name, final String phone,
			final String username, final String password) {
		
		super(name, new Login(username, password));
		this.ra = new AcademicRegistry(ra);
		this.phone = phone;
		this.completed = false;
	}
	
	public String getFullRa() {
		return ra.getFullRa();
	}
	
	public AcademicRegistry getAcademicRegistry() {
		return ra;
	}
	
	public String getCourseCode() {
		return ra.getCourseCode();
	}
	
	public String getInstitutionCode() {
		return ra.getInstitutionCode();
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void completed() {
		completed = true;
	}
	
	public void update(final Student updateStudent) {
		super.update(updateStudent);
		this.ra = updateStudent.getAcademicRegistry();
		this.phone = updateStudent.getPhone();		
	}

	@Override
	public ProfileType getProfile() {
		return ProfileType.STUDENT;
	}
}