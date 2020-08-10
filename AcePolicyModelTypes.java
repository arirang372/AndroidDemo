package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.StringDef;

/**
 * Represents annotated policy model types for policy detail page implementation.
 *
 * @author John Sung, Geico
 */
@StringDef(value = {AcePolicyModelTypes.COVERAGE, AcePolicyModelTypes.DISCOUNT, AcePolicyModelTypes.DRIVER,
		AcePolicyModelTypes.VEHICLE})
@Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.SOURCE)
public @interface AcePolicyModelTypes {

	String COVERAGE = "COVERAGE";
	String DISCOUNT = "DISCOUNT";
	String DRIVER = "DRIVER";
	String VEHICLE = "VEHICLE";
}
