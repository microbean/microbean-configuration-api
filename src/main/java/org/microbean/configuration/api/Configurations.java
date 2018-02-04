/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2017-2018 microBean.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.microbean.configuration.api;

import java.lang.reflect.Type;

import java.util.Collections; // for javadoc only
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A single source for configuration values suitable for an
 * application.
 *
 * @author <a href="https://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see #getValue(Map, String, Type, String)
 */
public abstract class Configurations {


  /**
   * A {@link Logger} for this {@link Configurations}.
   *
   * <p>This field is never {@code null}.</p>
   *
   * @see #createLogger()
   */
  protected final Logger logger;
  

  /*
   * Constructors.
   */


  /**
   * Creates a new {@link Configurations}.
   *
   * @exception IllegalStateException if the {@link #createLogger()}
   * method returns {@code null}
   *
   * @see #createLogger()
   */
  protected Configurations() {
    super();
    this.logger = this.createLogger();
    if (this.logger == null) {
      throw new IllegalStateException("createLogger() == null");
    }
  }


  /*
   * Instance methods.
   */
  

  /**
   * Returns a {@link Logger} for use by this {@link Configurations}
   * implementation.
   *
   * <p>This method never returns {@code null}.</p>
   *
   * <p>Overrides of this method must not return {@code null}.</p>
   *
   * @return a non-{@code null} {@link Logger}
   */
  protected Logger createLogger() {
    return Logger.getLogger(this.getClass().getName());
  }
  
  /**
   * Returns a non-{@code null}, {@linkplain
   * Collections#unmodifiableSet(Set) immutable} {@link Set} of {@link
   * Type}s representing all the types to which {@link String}-typed
   * configuration values may be converted by this {@link
   * Configurations} object.
   *
   * <p>This method never returns {@code null}.</p>
   *
   * @return a non-{@code null}, {@linkplain
   * Collections#unmodifiableSet(Set) immutable} {@link Set} of {@link
   * Type}s
   *
   * @see TypeLiteral
   */
  public abstract Set<Type> getConversionTypes();

  /**
   * Returns a {@link Map} of <em>configuration
   * coordinates</em>&mdash;aspects and their values that define a
   * location within which requests for configuration values may take
   * place.
   *
   * <p>Implementations of this method may return {@code null}.</p>
   *
   * @return a {@link Map} of configuration coordinates; may be {@code
   * null}
   */
  public abstract Map<String, String> getConfigurationCoordinates();

  /**
   * Returns a configuration value corresponding to the configuration
   * property with the supplied {@code name}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} is {@code null}
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final String getValue(final String name) {
    return this.getValue(this.getConfigurationCoordinates(), name, String.class, null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property with the supplied {@code name}, or the supplied {@code
   * defaultValue} if otherwise {@code null} would be returned.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param defaultValue the value to return if otherwise {@code null}
   * would be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} is {@code null}
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final String getValue(final String name, final String defaultValue) {
    return this.getValue(this.getConfigurationCoordinates(), name, String.class, defaultValue);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} is {@code null}
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final String getValue(final Map<String, String> configurationCoordinates, final String name) {
    return this.getValue(configurationCoordinates, name, String.class, null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}, or the supplied
   * {@code defaultValue} if otherwise {@code null} would be returned.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param defaultValue the value to return if otherwise {@code null}
   * would be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} is {@code null}
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final String getValue(final Map<String, String> configurationCoordinates, final String name, final String defaultValue) {
    return this.getValue(configurationCoordinates, name, String.class, defaultValue);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code name}, converted, if
   * possible, to the supplied {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Class} representing the type to which the
   * configuration value will be converted; must not be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final String name, final Class<T> type) {
    return this.getValue(this.getConfigurationCoordinates(), name, type, null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code name}, converted, if
   * possible, to the supplied {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Class} representing the type to which the
   * configuration value will be converted; must not be {@code null}
   *
   * @param defaultValue the value that will be converted if {@code
   * null} would otherwise be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final String name, final Class<T> type, final String defaultValue) {
    return this.getValue(this.getConfigurationCoordinates(), name, type, defaultValue);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}, converted, if
   * possible, to the supplied {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Class} representing the type to which the
   * configuration value will be converted; must not be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final Map<String, String> configurationCoordinates, final String name, final Class<T> type) {
    return this.getValue(configurationCoordinates, name, (Type)type, null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}, or the supplied
   * {@code defaultValue} if {@code null} would otherwise be returned,
   * converted, if possible, to the supplied {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Class} representing the type to which the
   * configuration value will be converted; must not be {@code null}
   *
   * @param defaultValue the value that will be converted if {@code
   * null} would otherwise be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final Map<String, String> configurationCoordinates, final String name, final Class<T> type, final String defaultValue) {
    return this.getValue(configurationCoordinates, name, (Type)type, defaultValue);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code name}, converted, if
   * possible, to the type represented by the supplied {@code
   * typeLiteral}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param typeLiteral a {@link TypeLiteral} representing the type to
   * which the configuration value will be converted; must not be
   * {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final String name, final TypeLiteral<T> typeLiteral) {
    return this.getValue(this.getConfigurationCoordinates(), name, typeLiteral, null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code name}, or the supplied
   * {@code defaultValue} if {@code null} would otherwise be returned,
   * converted, if possible, to the type represented by the supplied
   * {@code typeLiteral}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param typeLiteral a {@link TypeLiteral} representing the type to
   * which the configuration value will be converted; must not be
   * {@code null}
   *
   * @param defaultValue the value that will be converted if {@code
   * null} would otherwise be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final String name, final TypeLiteral<T> typeLiteral, final String defaultValue) {
    return this.getValue(this.getConfigurationCoordinates(), name, typeLiteral, defaultValue);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}, converted, if
   * possible, to the type represented by the supplied {@code
   * typeLiteral}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param typeLiteral a {@link TypeLiteral} representing the type to
   * which the configuration value will be converted; must not be
   * {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final Map<String, String> configurationCoordinates, final String name, final TypeLiteral<T> typeLiteral) {
    return this.getValue(configurationCoordinates, name, typeLiteral == null ? (Type)null : typeLiteral.getType(), null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}, converted, if
   * possible, to the type represented by the supplied {@code
   * typeLiteral}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param typeLiteral a {@link TypeLiteral} representing the type to
   * which the configuration value will be converted; must not be
   * {@code null}
   *
   * @param defaultValue the value that will be converted if {@code
   * null} would otherwise be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final Map<String, String> configurationCoordinates, final String name, final TypeLiteral<T> typeLiteral, final String defaultValue) {
    return this.getValue(configurationCoordinates, name, typeLiteral == null ? (Type)null : typeLiteral.getType(), defaultValue);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code name}, converted, if
   * possible, to the type represented by the supplied {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Type} representing the type to
   * which the configuration value will be converted; must not be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}

   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final String name, final Type type) {
    return this.getValue(this.getConfigurationCoordinates(), name, type, null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code name}, or the supplied
   * {@code defaultValue} if otherwise {@code null} would be returned,
   * converted, if possible, to the type represented by the supplied
   * {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Type} representing the type to
   * which the configuration value will be converted; must not be {@code null}
   *
   * @param defaultValue the value that will be converted if {@code
   * null} would otherwise be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}

   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final String name, final Type type, final String defaultValue) {
    return this.getValue(this.getConfigurationCoordinates(), name, type, defaultValue);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}, converted, if
   * possible, to the type represented by the supplied {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Type} representing the type to
   * which the configuration value will be converted; must not be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}

   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   *
   * @see #getValue(Map, String, Type, String)
   */
  public final <T> T getValue(final Map<String, String> configurationCoordinates, final String name, final Type type) {
    return this.getValue(configurationCoordinates, name, type, null);
  }

  /**
   * Returns a configuration value corresponding to the configuration
   * property suitable for the supplied {@code
   * configurationCoordinates} and {@code name}, or the supplied
   * {@code defaultValue} if {@code null} would otherwise be returned,
   * converted, if possible, to the type represented by the supplied
   * {@code type}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param <T> the type to which a {@link String}-typed configuration
   * value should be converted
   *
   * @param configurationCoordinates a {@link Map} representing the
   * configuration coordinates in effect for this request; may be
   * {@code null}
   *
   * @param name the name of the configuration property for which a
   * value will be returned; must not be {@code null}
   *
   * @param type a {@link Type} representing the type to which the
   * configuration value will be converted; must not be {@code null}
   *
   * @param defaultValue the value that will be converted if {@code
   * null} would otherwise be returned; may be {@code null}
   *
   * @return the configuration value, or {@code null}
   *
   * @exception NullPointerException if {@code name} or {@code type}
   * is {@code null}
   *
   * @exception ConversionException if type conversion could not occur
   * for any reason
   *
   * @exception AmbiguousConfigurationValuesException if two or more
   * values were found that could be suitable
   */
  public abstract <T> T getValue(final Map<String, String> configurationCoordinates, final String name, final Type type, final String defaultValue);


  /*
   * Static methods.
   */


  /**
   * Returns a {@link Configurations} implementation found using the
   * standard {@link ServiceLoader} mechanism.
   *
   * <p>The first entry in the first classpath resource named {@code
   * META-INF/services/org.microbean.configuration.api.Configurations}
   * will be treated as a class name, loaded and instantiated, and
   * returned.</p>
   *
   * @return a non-{@code null} {@link Configurations} instance
   *
   * @exception ConfigurationException if there was no implementation
   * found
   *
   * @see ServiceLoader
   */
  public static final Configurations newInstance() {
    final String cn = Configurations.class.getName();
    final Logger logger = Logger.getLogger(cn);    
    final String mn = "newInstance";    
    if (logger.isLoggable(Level.FINER)) {
      logger.entering(cn, mn);
    }
    Configurations returnValue = null;
    final ServiceLoader<Configurations> configurationsLoader = ServiceLoader.load(Configurations.class);
    assert configurationsLoader != null;
    final Iterator<Configurations> configurationsIterator = configurationsLoader.iterator();
    assert configurationsIterator != null;
    while (returnValue == null && configurationsIterator.hasNext()) {
      try {
        returnValue = configurationsIterator.next();
      } catch (final ServiceConfigurationError badServiceProviderFile) {
        throw new ConfigurationException(badServiceProviderFile);
      }
    }
    if (returnValue == null) {
      throw new ConfigurationException("No " + Configurations.class.getName() + " implementation found in any META-INF/services/" + Configurations.class.getName() + " service provider resources");
    }
    if (logger.isLoggable(Level.FINER)) {
      logger.exiting(cn, mn, returnValue);
    }
    return returnValue;
  }
  
}
