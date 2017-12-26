/*
 * @(#)StudentSpecificationArgumentResolver.java 1.0 1 25/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import br.gov.sp.fatec.mapskills.domain.user.student.StudentSpecification;

/**
 * A classe {@link StudentSpecificationArgumentResolver} monta
 * uma {@link StudentSpecification} para injecao na
 * {@link InstitutionController}.
 *
 * @author Marcelo
 * @version 1.0 25/12/2017
 */
public class StudentSpecificationArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		return parameter.getParameterType().equals(StudentSpecification.class);
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
			final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		return new StudentSpecification(getParameter("name", webRequest), getParameter("ra", webRequest),
				getParameter("institutionCode", webRequest), getParameter("courseCode", webRequest));
	}

	/**
	 * Metodo responsavel por recuperar um parametro da requisicao.
	 * 
	 * @param parameter
	 * 		Nome do parametro a ser recuperado.
	 * @param webRequest
	 * 		Requisicao de onde sera recuperado o parametro.
	 * @return
	 * 		O valor do parametro.
	 */
	private String getParameter(final String parameter, final NativeWebRequest webRequest) {
		return webRequest.getParameter(parameter);
	}
}