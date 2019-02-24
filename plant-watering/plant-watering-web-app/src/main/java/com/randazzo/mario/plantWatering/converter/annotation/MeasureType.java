package com.randazzo.mario.plantWatering.converter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import com.randazzo.mario.plantWatering.converter.MeasureConverter;

/**
 * Qualifiers for {@link MeasureConverter}.
 * 
 * @author Mario Randazzo
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface MeasureType {
}
