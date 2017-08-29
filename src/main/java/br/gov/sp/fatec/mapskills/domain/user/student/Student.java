/*
 * @(#)Student.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.user.Login;
import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MAPSKILLS.STUDENT")
@PrimaryKeyJoinColumn(name = "USE_ID")
public class Student extends User {

	private static final long serialVersionUID = 1L;
	
	@Embedded
	private AcademicRegistry ra;
	
	@Column(name = "STU_PHONE", nullable = false)
	private String phone;
	
	@Column(name = "STU_IS_COMPLETED")
	private boolean completed;
	
	@SuppressWarnings("unused")
	private Student() throws MapSkillsException {
		this("0000000000000", null, null, null, null);
	}
			
	public Student(final String ra, final String name, final String phone, final String username,
			final String password) throws MapSkillsException {
		
		super(name, new Login(username, password), ProfileType.STUDENT);
		this.ra = new AcademicRegistry(ra);
		this.phone = phone;
		this.completed = false;
	}
	
	public String getRa() {
		return ra.getRa();
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
	
	public void update(final Student studentUpdate) {
		super.setName(studentUpdate.getName());
		super.updateLogin(studentUpdate.getLogin());
		this.ra = studentUpdate.getAcademicRegistry();
		this.phone = studentUpdate.getPhone();		
	}

}
