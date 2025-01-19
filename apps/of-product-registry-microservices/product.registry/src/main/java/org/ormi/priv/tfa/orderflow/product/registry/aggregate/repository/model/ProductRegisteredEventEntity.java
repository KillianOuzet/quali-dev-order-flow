package org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository.model;

/**
 * Event entity for a product which has been registered
 */
public class ProductRegisteredEventEntity extends ProductRegistryEventEntity {
  static final String EVENT_TYPE = "ProductRegistered";

  /**
   * Payload for the event.
   */
  public static record Payload(String productId, String name, String productDescription) {
  }

  /**
   * The payload for the event.
   */
  public Payload payload;

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
}