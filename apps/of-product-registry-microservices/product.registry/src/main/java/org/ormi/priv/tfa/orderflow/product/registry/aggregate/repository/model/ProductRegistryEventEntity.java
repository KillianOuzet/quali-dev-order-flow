package org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository.model;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;

/**
 * Basic class of a product registration event
 */
@MongoEntity(collection = "product_registry_events")
public abstract class ProductRegistryEventEntity {
  /**
     * The id of the product.
     */
	public ObjectId id;
  /**
     * The id of the event.
     */
  public String eventId;
  /**
     * The type of the event.
     */
  public String eventType = getEventType();
  /**
     * The Root ID of the product.
     */
  public String aggregateRootId;
  /**
     * The version of the product.
     */
  public long version;
  /**
     * The timestamp of the product.
     */
  public long timestamp;

  /**
     * A method which return the type of the event.
     */
  abstract String getEventType();
}
