package br.gov.sp.fatec.mapskills.domain.institution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "crs_id")
	private int id;
	
	@Column(name = "crs_code")
	private int code;
	
	@Column(name = "crs_name")
	private String name;
	
	@Column(name = "ins_code")
	private int institutionCode;
	
	public Course() {}
	
	public Course(final int code, final String name, final int institutionCode) {
		this.code = code;
		this.name = name;
		this.institutionCode = institutionCode;
	}
	
	public int id() {
		return id;
	}
	
	public int code() {
		return code;
	}
	
	public String name() {
		return name;
	}
	
	public int institutionCode() {
		return institutionCode;
	}

}