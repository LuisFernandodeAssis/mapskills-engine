/*
 * @(#)SerializationKey.java 1.0 1 30/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

/**
 * A enumeracao {@link SerializationKey}
 *
 * @author Marcelo
 * @version 1.0 30/09/2017
 */
public enum SerializationKey {
	
	ID("id"),
	CODE("code"),
	DESCRIPTION("description"),
	
	/*USER*/
	NAME("name"),
	PHONE("phone"),
	USERNAME("username"),
	PASS("password"),
	EMPTY_PASS(""),
	PROFILE("profile"),
	MENTOR("mentor"),
	
	/*STUDENT*/
	STUDENT("student"),
	RA("ra"),
	COMPLETED("completed"),
	
	/*COURSE*/
	COURSE("course"),
	PERIOD("period"),
	INSTITUTION_CODE("institutionCode"),
	COURSE_CODE("courseCode"),
	
	/*INSTITUTION*/
	INSTITUTION("institution"),
	INSTITUTION_ID("institutionId"),
	INSTITUTION_LEVEL("institutionLevel"),
	LEVEL("level"),
	COURSES("courses"),
	COMPANY("company"),
	CNPJ("cnpj"),
	CITY("city"),
	MENTORS("mentors"),
	GAME_THEME_ID("gameThemeId"),
	
	/*ANSWER EVENT*/
	SCENE_ID("sceneId"),
	SCENE_INDEX("sceneIndex"),
	STUDENT_ID("studentId"),
	SKILL_ID("skillId"),
	SKILL_VALUE("skillValue"),
	REMAINING_SCENES("remainingScenes"),
	
	/*ALTERNATIVE*/
	ALTERNATIVES("alternatives"),
	QUESTION("question"),
	
	/*SCENE*/
	INDEX("index"),
	TEXT("text"),
	BACKGROUND("background"),
	
	/*SKILL*/
	TYPE("type"),
	
	/*FILE*/
	BASE_64("base64"),
	FILENAME("filename"),
	
	/*REPORT*/
	VALUES("values");
	
	private final String key;
	
	private SerializationKey(final String key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return key;
	}
}