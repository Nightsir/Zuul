package org.bitbucket.nightsir.zuuladvanced.model.npc.callinterception;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.bitbucket.nightsir.zuuladvanced.model.npc.NPCActionType;
import org.bitbucket.nightsir.zuuladvanced.model.npc.NonPlayerCharacter;

/**
 * Class for intercepting method calls on {@link NonPlayerCharacter} classes.
 * 
 * @author Christian Sami
 */
public class ActionArgumentInterceptor implements InvocationHandler {
	private NonPlayerCharacter npc;
	private NPCActionType[] allowedActionTypes;
	
	/**
	 * Creates an intercepter for a given NPC and the allowed {@link NPCActionType}.
	 * 
	 * @param npc to intercept
	 * @param allowedActionTypes allowed {@link NPCActionType}
	 */
	public ActionArgumentInterceptor(NonPlayerCharacter npc, NPCActionType[] allowedActionTypes) {
		this.npc = npc;
		this.allowedActionTypes = allowedActionTypes;
	}
	
	/**
	 * Intercepts a call for invokeAction method in a NPC.
	 * If the called type is not a the specified {@link NPCActionType} in {@link AllowedNPCActionTypes}
	 * this method will throw a {@link NonAllowedActionTypeException} otherwise it calls the method.
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getName().equals("invokeAction")) {
			if (!Arrays.stream(allowedActionTypes).anyMatch((t) -> t.equals(args[0]))) {
				throw new NonAllowedActionTypeException((NPCActionType)args[0]);
			}
		}
		
		return method.invoke(npc, args);
	}
}
