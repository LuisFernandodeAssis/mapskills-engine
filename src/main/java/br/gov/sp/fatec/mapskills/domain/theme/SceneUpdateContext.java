/*
 * @(#)SceneUpdateContext.java 1.0 1 17/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A classe {@link SceneUpdateContext}
 *
 * @author Marcelo
 * @version 1.0 17/12/2017
 */
@Getter
@AllArgsConstructor
public class SceneUpdateContext {
	
	private final SceneWrapper sceneWrapper;
	private final String oldFilename;
	
	public boolean containsBase64() {
		return sceneWrapper.containsBase64();
	}
}