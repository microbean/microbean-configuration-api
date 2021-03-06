/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2017 MicroBean.
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

import java.io.Serializable; // for javadoc only

/**
 * A {@link ConfigurationException} indicating that a problem was
 * encountered during type conversion.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 */
public class ConversionException extends ConfigurationException {

  /**
   * The version of this class for {@linkplain Serializable
   * serialization} purposes.
   */
  private static final long serialVersionUID = 1L;


  /*
   * Constructors.
   */


  /**
   * Creates a new {@link ConversionException}.
   */
  public ConversionException() {
    super();
  }

  /**
   * Creates a new {@link ConversionException}.
   *
   * @param message the error message; may be {@code null}
   */
  public ConversionException(final String message) {
    super(message, null);
  }

  /**
   * Creates a new {@link ConversionException}.
   *
   * @param cause the {@link Throwable} causing this {@link
   * ConversionException}; may be {@code null}
   */
  public ConversionException(final Throwable cause) {
    super(null, cause);
  }

  /**
   * Creates a new {@link ConversionException}.
   *
   * @param message the error message; may be {@code null}
   *
   * @param cause the {@link Throwable} causing this {@link
   * ConversionException}; may be {@code null}
   */
  public ConversionException(final String message, final Throwable cause) {
    super(message, cause);
  }
  
}
