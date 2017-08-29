/*
 * @(#)ExcelFileHandle.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;

/**
 * 
 * A classe {@link ExcelFileHandle} tem objetivo de converter arquivo xlsx
 * em objetos para serem persistidos no banco de dados. Utiliza
 * padrao de projetos template method.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
public abstract class ExcelFileHandle<T> {
	
	private static final Logger LOGGER = Logger.getLogger(ExcelFileHandle.class.getName());
	protected static final String DEFAULT_ENCRYPTED_PASS = "$2a$10$TH9WvYSs4BYDi7NaesV.Uerv7ZyzXXrEuriWeo2qAl96i6fN3oz8G";
	
	protected abstract List<T> toObjectList(final InputStream inputStream) throws MapSkillsException;

	protected abstract T buildObject(final List<String> attArgs) throws MapSkillsException;
	
	protected abstract boolean verifyListForObject(final List<String> argsToObj);
	/**
	 * O metodo <code>objectListFactory</code> converte um arquivo do 
	 * tipo .xlsx (excel) em uma lista de objetos.
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException 
	 * @throws MapSkillsException 
	 * @throws Exception
	 */
	protected List<T> objectListFactory(final InputStream inputStream) throws MapSkillsException {
		try {
			final XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			final XSSFSheet sheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = sheet.iterator();
			workbook.close();
			return Collections.unmodifiableList(this.objectListBuilder(rowIterator));
		} catch (final MapSkillsException | IOException exception) {
			LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
			throw new ReadFileException("problema ao ler excel", exception);
		}
	}
	/**
	 * O metodo <code>objectListBuilder</code> auxilia o metodo <code>objectListFactory<code> na conversao
	 * de um arquivo em uma lista de objeto, iterando nas linhas do documento, sem pegar a primeira linha que
	 * sao os titulos das colunas.
	 * 
	 * @param rowIterator
	 * @return
	 * @throws MapSkillsException 
	 */
	private List<T> objectListBuilder(final Iterator<Row> rowIterator) throws MapSkillsException {
		final List<T> objectList = new LinkedList<>();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			final Row row = rowIterator.next();
			final Iterator<Cell> cellIterator = row.cellIterator();
			final List<String> attrForObj = new LinkedList<>();
			attrForObj.addAll(this.cellIteratorToListString(cellIterator));
			if(this.verifyListForObject(attrForObj)) {
				objectList.add(buildObject(attrForObj));				
			}
		}
		return Collections.unmodifiableList(objectList);
	}
		
	/**
	 * metodo que converte um iterator de celula do excel
	 * em uma lista de strings que servira como parametros
	 * para construcao do objeto definido pela classe filha.
	 * 
	 * @param cellIterator
	 * @return list
	 */
	private Collection<String> cellIteratorToListString(final Iterator<Cell> cellIterator) {
		final List<String> cellList = new LinkedList<>();
		while (cellIterator.hasNext()) {
			final Cell cell = cellIterator.next();
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if(this.verifyCellIsEmpty(cell)) {
				continue;
			}
			cellList.add(cell.getStringCellValue()); 
		}
		return Collections.unmodifiableList(cellList);
	}
	/**
	 * verifica se a celula esta em branco, para nao
	 * adicionala a lista de strings.
	 * 
	 * @param cell
	 * @return
	 */
	private boolean verifyCellIsEmpty(final Cell cell) {
		return StringUtils.isEmpty(cell.getStringCellValue().trim());
	}

}
