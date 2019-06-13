package org.bitbucket.nightsir.zuuladvanced.script;

import jdk.nashorn.api.scripting.ClassFilter;

/**
 * Class to filter which class can be used in JS.
 * 
 * @author Christian Sami
 */
@SuppressWarnings("restriction")
class CustomClassFilter implements ClassFilter {
	/**
	 * Checks if a class is allowed in JS or not.
	 * 
	 * @return <code>true</code> if the class is allowed - <code>false</code> otherwise
	 */
	@Override
	public boolean exposeToScripts(String className) {
		try {
			return Class.forName(className).isAnnotationPresent(AllowedScriptClass.class);
		} catch (ClassNotFoundException e) {}
		return false;
	}
}