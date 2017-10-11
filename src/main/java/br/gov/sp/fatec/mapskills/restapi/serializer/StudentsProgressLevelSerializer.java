/*
 * @(#)StudentsProgressLevelSerializer.java 1.0 01/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.dashboard.InstitutionStudentsIndicator;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressLevelWrapper;
/**
 * 
 * A classe {@link StudentsProgressLevelSerializer} e responsavel
 * por serializar os resutados contidos na classe
 * <code>StudentsProgressLevelWrapper</code>. JSON utilizado
 * na tela de perfil administrador.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
public class StudentsProgressLevelSerializer extends AbstractJsonSerializer<StudentsProgressLevelWrapper> {

	@Override
	public void serialize(final StudentsProgressLevelWrapper indicatorWrapper, final JsonGenerator generator) throws IOException {

		generator.writeStartObject();
		dataGenerate(indicatorWrapper.getIndicatorResults(), generator);
		institutionGenerate(indicatorWrapper.getIndicatorResults(), generator);
		generator.writeEndObject();
	}
	
	private void dataGenerate(final List<InstitutionStudentsIndicator> indicators, final JsonGenerator generator) throws IOException {		
		generator.writeArrayFieldStart("data");
		generator.writeStartArray();
		for(final InstitutionStudentsIndicator indicator : indicators) {
			final int totalStudents = ObjectUtils.isEmpty(indicator.getTotal()) ? 0 : indicator.getTotal();
			final double totalFinalized = ObjectUtils.isEmpty(indicator.getFinalized()) ? 0 : indicator.getFinalized();
			generator.writeNumber(calcPercentage(totalFinalized, totalStudents));
		}
		generator.writeEndArray();
		generator.writeStartArray();
		for(final InstitutionStudentsIndicator indicator : indicators) {
			final int totalStudents = ObjectUtils.isEmpty(indicator.getTotal()) ? 0 : indicator.getTotal();
			final double totalNotFinalized = ObjectUtils.isEmpty(indicator.getNotFinalized()) ? 0 : indicator.getNotFinalized();
			generator.writeNumber(calcPercentage(totalNotFinalized, totalStudents));
		}
		generator.writeEndArray();
		generator.writeEndArray();
	}
	
	private void institutionGenerate(final List<InstitutionStudentsIndicator> indicators, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("institutions");
		for(final InstitutionStudentsIndicator indicator : indicators) {
			generator.writeStartObject();
			generator.writeStringField("code", indicator.getInstitutionCode());
			generator.writeStringField("company", indicator.getInstitutionName());
			generator.writeEndObject();
		}
		generator.writeEndArray();
	}
	
	/**
	 * Metodo que calcula uma porcentagem a partir de uma quantidade e um total.
	 */
	private BigDecimal calcPercentage(final double quantity, final double total) {
		final double percentage = (quantity/total) * 100;
		final BigDecimal accuratePercentage = new BigDecimal(percentage);
		return accuratePercentage.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
}