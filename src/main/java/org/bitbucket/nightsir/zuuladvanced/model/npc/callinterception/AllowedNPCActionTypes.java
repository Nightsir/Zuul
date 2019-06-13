package org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;

/**
 * Annotation to specify the allowed {@link NPCActionType} on an {@link NonPlayerCharacter}.
 * 
 * @author Christian Sami
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AllowedNPCActionTypes {
	public NPCActionType[] value() default {};
}
