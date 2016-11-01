/*
 * @(#)XLSXParser.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * A classe <code>UserXLSXParser</code> tem objetivo de converter arquivo xlsx em objetos
 * para serem persistidos no banco de dados.
 * 
 * @author Marcelo
 *
 */
public abstract class XLSXParser {
	
	protected abstract List<?> toObjectList(final File file) throws Exception;

	protected abstract Object build(final Iterator<Cell> cellIterator);

	protected List<?> objectListFactory(final File file) throws Exception {
		final XSSFWorkbook workbook = new XSSFWorkbook(file);
		final XSSFSheet sheet = workbook.getSheetAt(0);
		final Iterator<Row> rowIterator = sheet.iterator(); 
		workbook.close();
		return objectListBuilder(rowIterator);
	}
		
	private List<?> objectListBuilder(final Iterator<Row> rowIterator) {
		final List<Object> objectList = new ArrayList<>();
		Row row;
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			final Iterator<Cell> cellIterator = row.cellIterator();
			objectList.add(build(cellIterator));
		}
		return objectList;
	}
	
	protected List<String> objectArgs(final Iterator<Cell> cellIterator) {
		final List<String> args = new LinkedList<>();
		Cell cell;
		while (cellIterator.hasNext()) {
			cell = cellIterator.next();
			cell.setCellType(Cell.CELL_TYPE_STRING);
			args.add(cell.getStringCellValue());
		}
		return args;
	}

}