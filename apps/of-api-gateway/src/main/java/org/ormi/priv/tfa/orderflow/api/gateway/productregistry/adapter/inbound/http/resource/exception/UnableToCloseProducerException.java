package org.ormi.priv.tfa.orderflow.api.gateway.productregistry.adapter.inbound.http.resource.exception;

public class UnableToCloseProducerException extends RuntimeException {

  public UnableToCloseProducerException(String message) {
    super(message);
  }

  public UnableToCreateException(String message, Throwable cause) {
    super(message, cause);
  }
}