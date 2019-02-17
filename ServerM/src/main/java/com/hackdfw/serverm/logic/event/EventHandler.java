package com.hackdfw.serverm.logic.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hackdfw.serverm.logic.event.GameEventManager.ListenerPriority;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler
{
	ListenerPriority priority() default ListenerPriority.NORMAL;
	boolean ignoreCancelled() default false;
}