/*
 * @(#)ListWrapper.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.restapi.serializer.ListSerializer;

/**
 * A classe {@link ListWrapper}
 *
 * @author Marcelo
 * @version 1.0 20/09/2017
 */
@JsonSerialize(using = ListSerializer.class)
public class ListWrapper<T> {
	
	private final List<T> list = new LinkedList<>();
	
	public ListWrapper(final List<T> list) {
		this.list.addAll(list);
	}
	
	public List<T> getList() {
		return Collections.unmodifiableList(list);
	}
}