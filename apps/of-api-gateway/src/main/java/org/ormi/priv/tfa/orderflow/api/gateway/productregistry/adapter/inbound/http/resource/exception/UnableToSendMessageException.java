package org.ormi.priv.tfa.orderflow.api.gateway.productregistry.adapter.inbound.http.resource.exception;

public class UnableToSendMessageException extends RuntimeException {

  public UnableToSendMessageException(String message) {
    super(message);
  }

  public UnableToSendMessageException(String message, Throwable cause) {
    super(message, cause);
  }
}
