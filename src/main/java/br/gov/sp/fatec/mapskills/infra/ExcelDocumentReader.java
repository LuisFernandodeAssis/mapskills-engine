/*
 * @(#)ExcelDocumentReader.java 1.0 03/11/2016
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;

/**
 * A classe {@link ExcelDocumentReader} tem objetivo de converter arquivo xlsx
 * em objetos para serem persistidos no banco de dados. Utiliza
 * padrao de projetos template method.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@AllArgsConstructor
public abstract class ExcelDocumentReader<T> {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final PasswordEncoder encoder;
	
	/**
	 * Constroi um objeto de dominio a partir de uma lista de atributos.
	 * 
	 * @param attArgs Atributos a serem preenchidos ao objeto.
	 * @return Objeto de dominio da implementacao.
	 */
	protected abstract T buildEntity(final List<String> attArgs);
	
	/**
	 * Verifica se a quantidade de atributos atende
	 * para o construcao do objeto de dominio.
	 * 
	 * @param attToObj Atributos a serem preenchidos ao objeto.
	 * @return condicional da verificacao.
	 */
	protected abstract boolean isValidAttributes(final List<String> attToObj);
	
	/**
	 * Codifica a senha.
	 */
	protected String encodePassword(final String password) {
		return encoder.encode(password);
	}
	
	/**
	 * Converte um arquivo do tipo .xlsx (excel) em uma lista de objetos de dominio.
	 */
	public List<T> readDocument(final InputStream inputStream) {
		try {
			final XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			final XSSFSheet sheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = sheet.iterator();
			workbook.close();
			return Collections.unmodifiableList(objectListBuilder(rowIterator));
		} catch (final IOException exception) {
			logger.error("Problema ao realizar leitura de arquivo excel.", exception);
			throw new ReadFileException("problema ao ler excel", exception);
		}
	}
	
	/**
	 * Converte de um arquivo em uma lista de objeto, iterando nas linhas do
	 * documento, sem pegar a primeira linha que sao os titulos das colunas.
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
	 * Converte um iterator de celula do excel em uma lista de strings que
	 * servira como parametros para construcao do objeto definido pela classe filha.
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
	 * Verificar se a celula se encontra vazia.
	 */
	private boolean isEmptyCell(final Cell cell) {
		return StringUtils.isEmpty(cell.getStringCellValue().trim());
	}
}