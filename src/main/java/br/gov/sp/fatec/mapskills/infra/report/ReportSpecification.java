/*
 * @(#)ReportSpecification.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra.report;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/**
 * 
 * A classe {@link ReportSpecification} é responsavel
 * por "montar" as condicionais da clausula WHERE da query
 * de acordo com os atributos da classe <code>ReportFilter</code>.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
public final class ReportSpecification {
	
	private ReportSpecification() {
	}
	
	public static Specification<ReportDefaultData> byFilter(final ReportFilter filter) {
	    return new Specification<ReportDefaultData>() {
	      @Override
	      public Predicate toPredicate(final Root<ReportDefaultData> root, final CriteriaQuery<?> query,
	    		  final CriteriaBuilder builder) {
	    	  
	        final List<Predicate> predicates = new LinkedList<>();

	        verifyFilters(filter, predicates, root, builder);
	        return builder.and(predicates.toArray(new Predicate[]{}));
	      }
	    };
	  }
	/**
	 * Metodo que inicia todos as verificacoes de filtros. 
	 */
	private static void verifyFilters(final ReportFilter filter, final List<Predicate> predicates,
			final Root<ReportDefaultData> root, final CriteriaBuilder builder) {
		
		verifyLevelInstitutionFilter(filter, predicates, root, builder);
	}
	/**
	 * Metodo que verifica se ha filtro por nivel de instituicao (Tecnico ou Superior).
	 * E chama verificacao por codigo de instituicao.
	 */
	private static void verifyLevelInstitutionFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<ReportDefaultData> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getLevelInstitution())) {
        	predicates.add(builder.equal(root.<String>get("institutionLevel"), filter.getLevelInstitution().name()));
        }
		verifyInstitutionCodeFilter(filter, predicates, root, builder);
	}
	/**
	 * Metodo que verifica se ha filtro por codigo de instituicao.
	 * E chama verificacao por codigo de curso.
	 */
	private static void verifyInstitutionCodeFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<ReportDefaultData> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getInstitutionCode())) {
        	predicates.add(builder.equal(root.<String>get("institutionCode"), filter.getInstitutionCode()));
        }
		verifyCourseCodeFilter(filter, predicates, root, builder);
	}
	/**
	 * Metodo que verifica se ha filtro por codigo de curso.
	 * E chama verificacao por periodo inicial.
	 */
	private static void verifyCourseCodeFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<ReportDefaultData> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getCourseCode())) {
        	predicates.add(builder.equal(root.<String>get("courseCode"), filter.getCourseCode()));
        }
		verifyStartDateFilter(filter, predicates, root, builder);
	}
	/**
	 * Metodo que verifica se ha filtro por uma periodo inicial. Ex. primeiro semestre de 2015,
	 * ficaria 151.
	 * E chama verificacao por periodo final.
	 */
	private static void verifyStartDateFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<ReportDefaultData> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getStartDate())) {
        	predicates.add(builder.greaterThanOrEqualTo(root.<String>get("yearSemester"), filter.getStartDate()));
        }
		verifyEndDateFilter(filter, predicates, root, builder);
	}
	/**
	 * Metodo que verifica se ha filtro por uma periodo final. Ex. primeiro segundo semestre de 2016,
	 * ficaria 162.
	 */
	private static void verifyEndDateFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<ReportDefaultData> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getEndDate())) {
        	predicates.add(builder.lessThanOrEqualTo(root.<String>get("yearSemester"), filter.getEndDate()));
	    }
	}

}
