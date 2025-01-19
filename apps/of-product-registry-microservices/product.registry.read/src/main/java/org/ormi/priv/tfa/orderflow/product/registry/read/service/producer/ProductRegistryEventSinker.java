package org.ormi.priv.tfa.orderflow.product.registry.read.service.producer;

import java.util.concurrent.CompletionStage;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRegistered;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRegistryEvent;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRemoved;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductUpdated;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.config.ProductRegistryEventChannelName;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.query.config.ProductRegistryQueryChannelName;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.pulsar.PulsarClientService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * The product registry event sinker.
 * Produces events to the product registry event channel so it can be watched on
 * other services.
 */
@ApplicationScoped
public class ProductRegistryEventSinker {

  /**
   * The Pulsar client service.
   */
  @Inject
  private PulsarClientService pulsarClients;

  /**
   * Produce the given event with the given correlation id.
   * 
   * @param correlationId - the correlation id
   * @param event         - the event
   */
  public void sink(String correlationId, ProductRegistryEvent event) {
    // Get the producer for the correlation id
    getEventSinkByCorrelationId(correlationId)
        .thenAccept((producer) -> {
          // Sink the event
          producer
              .newMessage()
              .value(event)
              .sendAsync()
              .whenComplete((msgId, ex) -> {
                if (ex != null) {
                  throw new NoProduceEventWithIdException("Failed to produce event for correlation id: " + correlationId, ex);
                }
                Log.debug(String.format("Sinked event with correlation id{%s} in msg{%s}", correlationId, msgId));
                try {
                  producer.close();
                } catch (PulsarClientException e) {
                  throw new UnableToCloseProducerException("Failed to close producer", e);
                }
              });
        });
  }

  /**
   * Create a producer for the given correlation id.
   * 
   * @param correlationId - the correlation id
   * @return the producer
   */
  private CompletionStage<Producer<ProductRegistryMessage>> getEventSinkByCorrelationId(String correlationId) {
    // Define the channel name, topic and schema definition
    final String channelName = ProductRegistryQueryChannelName.PRODUCT_REGISTRY_READ_RESULT.toString();
    final String topic = channelName + "-" + correlationId;
    // Create and return the producer
    return pulsarClients.getClient(channelName)
        .newProducer(Schema.JSON(ProductRegistryMessage.class))
        .producerName(topic)
        .topic(topic)
        .createAsync()
        .exceptionally(ex -> {
          throw new UnableToCreateProducerException("Failed to create producer for correlation id: " + correlationId, ex);
        });
  }
}
