/*
 * @(#)PageWrapper.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.restapi.serializer.PageSerializer;
import lombok.Getter;

/**
 * A classe {@link PageWrapper}
 *
 * @author Marcelo
 * @version 1.0 20/09/2017
 */
@Getter
@JsonSerialize(using = PageSerializer.class)
public class PageWrapper<T> extends ListWrapper<T> {
	
	private final Integer remaningPages;
	private final Long totalElements;
	
	public PageWrapper(final Page<T> page) {
		super(page.getContent());
		this.remaningPages = page.getTotalPages() - page.getNumber() - 1;
		this.totalElements = page.getTotalElements();
	}
}