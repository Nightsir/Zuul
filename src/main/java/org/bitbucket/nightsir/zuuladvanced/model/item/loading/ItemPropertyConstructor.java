package org.bitbucket.nightsir.zuuladvanced.model.item.loading;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify the correct constructor which should be used in the {@link ItemFactory}.
 * The Annotation is specified on the class itself. The constructor is chosen by the method-head,
 * specified by the parameters.
 * 
 * @author Christian Sami
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ItemPropertyConstructor {
	public Class<? extends String>[] value();
}