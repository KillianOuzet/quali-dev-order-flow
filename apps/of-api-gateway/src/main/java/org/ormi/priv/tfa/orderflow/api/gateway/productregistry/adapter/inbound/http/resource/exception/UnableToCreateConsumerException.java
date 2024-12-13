package org.ormi.priv.tfa.orderflow.api.gateway.productregistry.adapter.inbound.http.resource.exception;

public class UnableToCreateConsumerException extends RuntimeException {

  public UnableToCreateConsumerException(String message) {
    super(message);
  }

  public UnableToCreateConsumerException(String message, Throwable cause) {
    super(message, cause);
  }
}