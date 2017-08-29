/*
 * @(#)Course.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
/**
 * 
 * A classe {@link Course}
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.COURSE")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CRS_ID")
	private Long id;
	
	@Column(name = "CRS_CODE")
	private String code;
	
	@Column(name = "CRS_NAME")
	private String name;
	
	@Column(name = "CRS_PERIOD")
	@Enumerated(value = EnumType.STRING)
	private CoursePeriod period;
	
	@ManyToOne
	@JoinColumn(name = "INS_CODE")
	private Institution institution;
	
	@SuppressWarnings("unused")
	private Course() {
		this(null, null, null, null);
	}
	
	public Course(final String code, final String name, final CoursePeriod period, final Institution institution) {
		this.code = code;
		this.name = name;
		this.period = period;
		this.institution = institution;
	}
		
	public String getPeriod() {
		return period.name();
	}
	
	public String getInstitutionCode() {
		return institution.getCode();
	}
	
	public void setId(final long id) {
		this.id = id;
	}
	
	public void setInstitution(final Institution institution) {
		this.institution = institution;
	}

}
