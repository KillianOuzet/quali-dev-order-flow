package org.ormi.priv.tfa.orderflow.api.gateway.productregistry.adapter.inbound.http.resource.exception;

public class NoProduceEventWithIdException extends RuntimeException {

  public NoProduceEventWithIdException(String message) {
    super(message);
  }

  public NoProduceEventWithIdException(String message, Throwable cause) {
    super(message, cause);
  }
}
