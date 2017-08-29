/*
 * @(#)SaveImageService.java 1.0 06/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.utils.Base64Parser;
import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.utils.ApplicationContextHolder;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link SaveImageService} e responsavel por salvar as
 * images feitas por upload pela aplicacao.
 *
 * @author Marcelo
 * @version 1.0 06/01/2017
 */
@Component
@AllArgsConstructor
public class SaveImageService {
	
	private static final Logger LOGGER = Logger.getLogger(SaveImageService.class.getName());
	/*TODO aqui deve ser o local definido no arquivo properties definido.
	 *   */
	private final ServletContext context;
	private final Base64Parser parser = ApplicationContextHolder.getBean("base64Parser", Base64Parser.class);
	
	/**
	 * Metodo que salva a imagem no diretorio do servidor definido como padrao.
	 * @param Base64 imagem no formato base 64.
	 * @param filename Nome do arquivo.
	 * @return o Nome da imagem gravada.
	 * @throws MapSkillsException
	 */
	/*TODO salvar imagem com um hash, para que não salve duas imagens com mesmo nome,
	 * DICA: salvar id do tema + id da cena + nome da imagem. Caso a imagem já exista,
	 * a mesma deve ser excluida */
	public String save(final String base64, final String filename) throws MapSkillsException {
		if(base64 == null) {
			return null;
		}
		final String path = context.getRealPath("/images/");
		try (final OutputStream stream = new FileOutputStream(path.concat(filename))) {
		    stream.write(parser.toByteArray(base64));
		    stream.close();
		    return filename;
		} catch (final IOException exception) {
			LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
			throw new SaveImageException(filename, exception);
		}
	}
	
}