/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.http.api.request.validator;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import static org.mule.runtime.api.metadata.MediaType.ANY;
import org.mule.extension.http.api.HttpResponseAttributes;
import org.mule.runtime.api.exception.ErrorMessageAwareException;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.extension.api.runtime.operation.Result;

/**
 * Signals that an error occurred while validating a {@link Message}
 *
 * @since 1.0
 */
public class ResponseValidatorException extends MuleRuntimeException implements ErrorMessageAwareException {

  Message errorMessage;

  public ResponseValidatorException(String message) {
    super(createStaticMessage(message));
  }

  public ResponseValidatorException(String message, Result<Object, HttpResponseAttributes> result) {
    this(message);
    this.errorMessage = Message.builder()
        .payload(result.getOutput())
        .attributes(result.getAttributes().get())
        .mediaType(result.getMediaType().orElse(ANY))
        .build();
  }

  @Override
  public Message getErrorMessage() {
    return errorMessage;
  }

  @Override
  public Throwable getRootCause() {
    return this;
  }

}