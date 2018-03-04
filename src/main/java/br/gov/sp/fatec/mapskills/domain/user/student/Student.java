/*
 * @(#)Student.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.user.Login;
import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "MAPSKILLS.STUDENT")
public class Student extends User {	
	
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
	private Student() {
		this("0000000000000", null, null, null, null, null);
	}
			
	public Student(final String ra, final String name, final String phone,
			final Course course, final String username, final String password) {
		super(name, new Login(username, password));
		this.ra = new AcademicRegistry(ra);
		this.course = course;
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
	
	public Institution getInstitution() {
		return course.getInstitution();
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public DomainEvent completed() {
		completed = true;
		return new StudentFinishedGameEvent(getId());
	}
	
	public void update(final Student updateStudent) {
		super.update(updateStudent);
		this.ra = updateStudent.getAcademicRegistry();
		this.phone = updateStudent.getPhone();	
		this.course = updateStudent.getCourse();
	}

	@Override
	public ProfileType getProfile() {
		return ProfileType.STUDENT;
	}
}