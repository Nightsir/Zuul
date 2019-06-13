package org.bitbucket.nightsir.zuuladvanced.script;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class which should be allowed to be used in JS.
 * 
 * @author Christian Sami
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AllowedScriptClass {

}
