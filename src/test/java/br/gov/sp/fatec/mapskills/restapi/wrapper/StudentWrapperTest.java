/*
 * @(#)StudentWrapperTest.java 1.0 17/04/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.serializer.StudentSerializerTest;
import lombok.Getter;
/**
 * A classe <code>StudentWrapperTest</code>
 * encapsula o objeto de domínio que é utilizado
 * nos casos de teste e define um serializador
 * para o mesmo.
 *  
 * @author Marcelo
 *
 */
@Getter
@JsonSerialize(using = StudentSerializerTest.class)
public class StudentWrapperTest {
	
	private final Student student;
	
	public StudentWrapperTest(final Student student) {
		this.student = student;
	}

}
