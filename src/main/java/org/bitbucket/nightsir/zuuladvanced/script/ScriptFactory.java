package org.bitbucket.nightsir.zuuladvanced.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ClassFilter;

/**
 * Factory for converting JS-Scripts to a {@link Invocable} object usable in the code.
 * 
 * @author Christian Sami
 */
@SuppressWarnings("restriction")
public class ScriptFactory {
	private static final NashornScriptEngineFactory engineFactory = new NashornScriptEngineFactory();
	private static final ClassFilter classFilter = new CustomClassFilter();
	
	/**
	 * Converts a given JS-Script to a {@link Invocable}.
	 * 
	 * @param jsString JS-Script to convert as String
	 * @return {@link Invocable} of the JS-Script
	 */
	public static Invocable convertJSStringToInvocable(String jsString) {
		try {
			ScriptEngine nashorn = engineFactory.getScriptEngine(classFilter);
			nashorn.eval(jsString);
			return (Invocable) nashorn;
		} catch (ScriptException | ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}
}
