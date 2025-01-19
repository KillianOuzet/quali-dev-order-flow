package org.ormi.priv.tfa.orderflow.product.registry.read.service;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRegistryEvent;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.message.ProductRegistryMessage;
import org.ormi.priv.tfa.orderflow.product.registry.read.projection.ProductRegistryProjector;
import org.ormi.priv.tfa.orderflow.product.registry.read.service.producer.ProductRegistryEventSinker;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductRegistryEventConsumer {

  @Inject
  private ProductRegistryProjector projector;

  @Inject
  private ProductRegistryEventSinker sinker;

  @Incoming("product-registry-event")
  @Transactional(Transactional.TxType.REQUIRED)
  public void handleEvent(ProductRegistryMessage message) {
    final ProductRegistryEvent event = msg.get().getValue()
    
    // Project the event
    projector.handleEvent(event);

    // TODO: Sink the event here once or while projection is processed
    // Get the correlation id from the message metadata
    final var metadata = msg.getMetadata(PulsarIncomingMessageMetadata.class).orElseThrow();
    final String correlationId = Optional.ofNullable(metadata.getProperty("correlation-id")).orElseThrow();
    sinker.sink(correlationID, event)

  }
}
