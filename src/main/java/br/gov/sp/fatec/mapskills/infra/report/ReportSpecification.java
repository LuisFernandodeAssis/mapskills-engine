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

import br.gov.sp.fatec.mapskills.dashboard.CsvReport;
import br.gov.sp.fatec.mapskills.dashboard.ReportFilter;

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
	
	private final ReportFilter filter;
	
	public ReportSpecification(final ReportFilter filter) {
		this.filter = filter;
	}
	
	public Specification<CsvReport> get() {
	    return new Specification<CsvReport>() {
	    	
	    	@Override
	    	public Predicate toPredicate(final Root<CsvReport> root,
	    			final CriteriaQuery<?> query, final CriteriaBuilder builder) {
	    		
	    		final List<Predicate> predicates = new LinkedList<>();
	    		addFilters(filter, predicates, root, builder);
	    		query.orderBy(builder.asc(root.get("studentRA")));
	    		
	    		return builder.and(predicates.toArray(new Predicate[]{}));
	    	}
	    };
	}
	
	/**
	 * Metodo que inicia todos as verificacoes de filtros. 
	 */
	private void addFilters(final ReportFilter filter, final List<Predicate> predicates,
			final Root<CsvReport> root, final CriteriaBuilder builder) {
		
		addLevelInstitutionFilter(filter, predicates, root, builder);
	}
	
	/**
	 * Metodo que verifica se ha filtro por nivel de instituicao (Tecnico ou Superior).
	 * E chama verificacao por codigo de instituicao.
	 */
	private void addLevelInstitutionFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<CsvReport> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getLevelInstitution())) {
        	predicates.add(builder.equal(root.get("institutionLevel"), filter.getLevelInstitution().name()));
        }
		addInstitutionCodeFilter(filter, predicates, root, builder);
	}
	
	/**
	 * Metodo que verifica se ha filtro por codigo de instituicao.
	 * E chama verificacao por codigo de curso.
	 */
	private void addInstitutionCodeFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<CsvReport> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getInstitutionCode())) {
        	predicates.add(builder.equal(root.get("institutionCode"), filter.getInstitutionCode()));
        }
		addCourseCodeFilter(filter, predicates, root, builder);
	}
	
	/**
	 * Metodo que verifica se ha filtro por codigo de curso.
	 * E chama verificacao por periodo inicial.
	 */
	private void addCourseCodeFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<CsvReport> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getCourseCode())) {
        	predicates.add(builder.equal(root.get("courseCode"), filter.getCourseCode()));
        }
		addStartDateFilter(filter, predicates, root, builder);
	}
	
	/**
	 * Metodo que verifica se ha filtro por uma periodo inicial. Ex. primeiro semestre de 2015,
	 * ficaria 151.
	 * E chama verificacao por periodo final.
	 */
	private void addStartDateFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<CsvReport> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getStartDate())) {
        	predicates.add(builder.greaterThanOrEqualTo(root.get("yearSemester"), filter.getStartDate()));
        }
		addEndDateFilter(filter, predicates, root, builder);
	}
	
	/**
	 * Metodo que verifica se ha filtro por uma periodo final. Ex. primeiro segundo semestre de 2016,
	 * ficaria 162.
	 */
	private void addEndDateFilter(final ReportFilter filter, final List<Predicate> predicates,
			final Root<CsvReport> root, final CriteriaBuilder builder) {
		
		if (!StringUtils.isEmpty(filter.getEndDate())) {
        	predicates.add(builder.lessThanOrEqualTo(root.get("yearSemester"), filter.getEndDate()));
	    }
	}
}