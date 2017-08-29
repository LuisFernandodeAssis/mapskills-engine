/* @(#)StudentExcelFileHandle.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;

/**
 * 
 * A classe {@link StudentExcelFileHandle} e responsavel por
 * manipular arquivos excel em objetos {@link Student}.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@Component
public class StudentExcelFileHandle extends ExcelFileHandle<Student> {
	/**
	 * O metodo <code>toObjectList</code> converte um arqiuvo do tipo excel xlsx em uma
	 * lista de objetos do tipo Student.
	 */
	@Override
	public List<Student> toObjectList(final InputStream inputStream) throws MapSkillsException {
		return Collections.unmodifiableList(super.objectListFactory(inputStream));
	}
	/**
	 * O metodo <code>build</code> constroi um objeto do tipo Student a partir de uma lista de
	 * String devolvida da chamada do método <code>objectArgs</code>.
	 * @throws MapSkillsException 
	 */
	@Override
	protected Student buildObject(final List<String> attArgs) throws MapSkillsException {
		return new Student(attArgs.get(0), attArgs.get(1),
				attArgs.get(2), attArgs.get(3), ExcelFileHandle.DEFAULT_ENCRYPTED_PASS);
	}
	/**
	 * verifica se o numero de string da lista que servira como
	 * parametro para a criacao do aluno eh diferente do necessario.
	 */
	@Override
	protected boolean verifyListForObject(final List<String> argsToObj) {
		return argsToObj.size() == 4;
	}
		
}
