package org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository.model;

/**
 * Event entity for a product which is removed
 */
public class ProductRemovedEventEntity extends ProductRegistryEventEntity {
  static final String EVENT_TYPE = "ProductRemoved";

  /**
   * Payload for the event.
   */
  public static record Payload(String productId) {
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