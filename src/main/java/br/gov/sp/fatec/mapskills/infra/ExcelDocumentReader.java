/*
 * @(#)ExcelFileHandle.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import java.io.IOException;
import java.io.InputStream;
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

/**
 * 
 * A classe {@link ExcelDocumentReader} tem objetivo de converter arquivo xlsx
 * em objetos para serem persistidos no banco de dados. Utiliza
 * padrao de projetos template method.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
public abstract class ExcelDocumentReader<T> {
	
	private static final Logger LOGGER = Logger.getLogger(ExcelDocumentReader.class.getName());
	protected static final String DEFAULT_ENCRYPTED_PASS = "$2a$10$TH9WvYSs4BYDi7NaesV.Uerv7ZyzXXrEuriWeo2qAl96i6fN3oz8G";
	
	protected abstract T buildEntity(final List<String> attArgs);
	
	protected abstract boolean isValidAttributes(final List<String> attToObj);
	
	/**
	 * 
	 * Metodo que converte um arquivo do tipo .xlsx (excel) em uma lista de objetos.
	 */
	public List<T> readDocument(final InputStream inputStream) {
		try {
			final XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			final XSSFSheet sheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = sheet.iterator();
			workbook.close();
			return Collections.unmodifiableList(objectListBuilder(rowIterator));
		} catch (final IOException exception) {
			LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
			throw new ReadFileException("problema ao ler excel", exception);
		}
	}
	
	/**
	 * 
	 * Metodo que converte de um arquivo em uma lista de objeto,
	 * iterando nas linhas do documento, sem pegar a primeira
	 * linha que sao os titulos das colunas.
	 */
	private List<T> objectListBuilder(final Iterator<Row> rowIterator) {
		final List<T> objects = new LinkedList<>();
		rowIterator.next();
		
		rowIterator.forEachRemaining(row -> {
			final Iterator<Cell> cellIterator = row.cellIterator();
			final List<String> attributesForEntity = new LinkedList<>();
			attributesForEntity.addAll(parseToStringList(cellIterator));
			if(isValidAttributes(attributesForEntity)) {
				objects.add(buildEntity(attributesForEntity));				
			}
		});

		return Collections.unmodifiableList(objects);
	}
		
	/**
	 * 
	 * Metodo que converte um iterator de celula do excel
	 * em uma lista de strings que servira como parametros
	 * para construcao do objeto definido pela classe filha.
	 */
	private List<String> parseToStringList(final Iterator<Cell> cellIterator) {
		final List<String> cellList = new LinkedList<>();
		
		cellIterator.forEachRemaining(cell -> {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if(isEmptyCell(cell)) {
				return;
			}
			cellList.add(cell.getStringCellValue()); 
		});
		
		return cellList;
	}
	
	/**
	 * Metodo responsavel por verificar se a celula se
	 * encontra vazia.
	 */
	private boolean isEmptyCell(final Cell cell) {
		return StringUtils.isEmpty(cell.getStringCellValue().trim());
	}
}