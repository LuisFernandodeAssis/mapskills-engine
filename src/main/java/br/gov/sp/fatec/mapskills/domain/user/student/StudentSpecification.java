/*
 * @(#)StudentSpecification.java 1.0 1 25/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;

/**
 * A classe {@link StudentSpecification}
 *
 * @author Marcelo
 * @version 1.0 25/12/2017
 */
@AllArgsConstructor
public class StudentSpecification implements Specification<Student> {

	private final String name;
	private final String ra;
	private final String institutionCode;
	private final String courseCode;
	
	@Override
	public Predicate toPredicate(final Root<Student> root, final CriteriaQuery<?> arg1, final CriteriaBuilder builder) {
		arg1.orderBy(builder.asc(root.get("course").get("name")), builder.asc(root.get("name")));
		final Predicate namePredicate = like(root, builder, "name", name);
		final Predicate raPredicate = like(root.get("ra"), builder, "fullRa", ra);
		final Predicate coursePredicate = like(root.get("ra"), builder, "institutionCode", institutionCode);
		final Predicate institutionPredicate = like(root.get("ra"), builder, "courseCode", courseCode);
		return and(builder, namePredicate, raPredicate, coursePredicate, institutionPredicate);
	}
	
	@SuppressWarnings("unchecked")
	private Predicate like(@SuppressWarnings("rawtypes") final Path path, final CriteriaBuilder builder, final String rootParam, final String filterParam) {
		if (ObjectUtils.isEmpty(filterParam)) {
			return null;
		}
		return builder.like(lower(path, builder, rootParam), String.format("%%%s%%", filterParam.toLowerCase()));
	}
	
	private Expression<String> lower(final Path<Object> path, final CriteriaBuilder builder, final String attribute) {
		return builder.lower(path.get(attribute));
	}
	
	private Predicate and(final CriteriaBuilder builder, final Predicate... predicates) {
		final List<Predicate> predicateList = new LinkedList<>();
		for (final Predicate predicate : predicates) {
			if (ObjectUtils.isEmpty(predicate)) {
				continue;
			}
			predicateList.add(predicate);
		}
		return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
	}
}