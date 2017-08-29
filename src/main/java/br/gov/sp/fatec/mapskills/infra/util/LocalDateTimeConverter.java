/*
 * @(#)LocalDateTimeConverter.java 1.0 1 24/08/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.infra.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A classe {@link LocalDateTimeConverter}e responsavel por adaptar
 * objetos <code>Date</code> para <code>LocalDateTime</code> e vice-versa, para
 * que propriedades de entidades possam ser definidas como
 * <code>LocalDate</code> e que possam, assim, serem corretamente interpretadas
 * pela JPA.
 *
 * @author Roberto Perillo
 * @version 1.0 09/02/2017
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {
	
	/** {@inheritDoc} */
    @Override
    public Date convertToDatabaseColumn(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        final ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /** {@inheritDoc} */
    @Override
    public LocalDateTime convertToEntityAttribute(final Date date) {
        if (date == null) {
            return null;
        }
        final Date copy = new Date(date.getTime());
        return LocalDateTime.ofInstant(copy.toInstant(), ZoneId.systemDefault());
    }

}
