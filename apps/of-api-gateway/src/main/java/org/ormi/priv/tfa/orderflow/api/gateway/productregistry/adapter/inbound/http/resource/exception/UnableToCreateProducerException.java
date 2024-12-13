package org.ormi.priv.tfa.orderflow.api.gateway.productregistry.adapter.inbound.http.resource.exception;

public class UnableToCreateProducerException extends RuntimeException {

  public UnableToCreateProducerException(String message) {
    super(message);
  }

  public UnableToCreateProducerException(String message, Throwable cause) {
    super(message, cause);
  }
}