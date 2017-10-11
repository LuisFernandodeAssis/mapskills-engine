/*
 * @(#)UserSerializer.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.restapi.wrapper.UserWrapper;
import br.gov.sp.fatec.mapskills.utils.ApplicationContextHolder;
/**
 * 
 * A classe {@link UserSerializer} e responsavel por serializar um perfil de usuario.
 * A serializacao e feita a partir da recuperacao do serializador especifico esta
 * contido no mapa de serializadores, cuja chave e o perfil do usuario.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
public class UserSerializer extends AbstractJsonSerializer<UserWrapper> {
	
	private final Map<ProfileType, UserSerilizerStrategy<User>> mapSerializer = new EnumMap<>(ProfileType.class);
	
	/**
	 * No construtor da classe e recuperado o mapa de serializadores que se encontra
	 * no cluster de objetos do spring, instanciado no pacote de configuracoes
	 * na classe <code>SerializersConfig</code>.
	 */
	@SuppressWarnings("unchecked")
	public UserSerializer() {
		super();
		mapSerializer.putAll(ApplicationContextHolder.getBean("mapSerializerStrategy", Map.class));
	}

	@Override
	public void serialize(final UserWrapper userWrapper, final JsonGenerator generator)	throws IOException {
		
		final UserSerilizerStrategy<User> serializer = mapSerializer.get(userWrapper.getProfile());
		serializer.serialize(userWrapper.getUser(), generator);
		
	}

}