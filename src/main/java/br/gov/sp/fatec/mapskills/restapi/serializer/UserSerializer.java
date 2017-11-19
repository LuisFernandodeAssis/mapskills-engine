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

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link UserSerializer} e responsavel por serializar um perfil de usuario.
 * A serializacao e feita a partir da recuperacao do serializador especifico esta
 * contido no mapa de serializadores, cuja chave e o perfil do usuario.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@Component
@AllArgsConstructor
public class UserSerializer extends AbstractSerializer<User> {
	
	/**
	 * Mapa de definicao de serializadores para os perfis.
	 * @see <code>SerializersConfig</code>.
	 */
	private final Map<ProfileType, AbstractSerializer<User>> userSerializerMap = new EnumMap<>(ProfileType.class);
	

	@Override
	public void serialize(final User user, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		final AbstractSerializer<User> serializer = userSerializerMap.get(user.getProfile());
		serializer.serialize(user, writer);		
	}
}