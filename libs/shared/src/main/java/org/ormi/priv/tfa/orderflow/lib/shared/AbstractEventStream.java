package org.ormi.priv.tfa.orderflow.lib.shared;


public class StreamAbstract {

    public static <T> void consumeEventStream(
        Consumer<T> consumer,
        int timeout,
        MultiEmitter<T> em
    ){
        while(!em.isCancelled()) {
          try {
            final var timeout;
            final var msg = Optional.ofNullable(consumer.receive(timeout, TimeUnit.MILLISECONDS));
            if (msg.isEmpty()) {
              // Complete the emitter if no event is received within the timeout. Free up resources.
              Log.debug("No event received within timeout of " + timeout + " seconds.");
              em.complete();
            }
            if (msg.get().getValue() instanceof ProductRegistryError){
              // Fail the stream on unexpected event types
              Throwable error = new ProductRegistryEventStreamException("Unexpected event type: " + evt.getClass().getName());
              em.fail(error);
              return;
            }

            final ProductRegistryEvent evt = msg.get().getValue();
              Log.debug("Received event: " + evt);
              // Map event to DTO
              if (evt instanceof ProductRegistered registered) {
                Log.debug("Emitting DTO for registered event: " + registered);
                // Emit DTO for registered event
                em.emit(ProductRegistryEventDtoMapper.INSTANCE.toDto(registered));
              }

            // Acknowledge the message
            consumer.acknowledge(msg.get());
          } catch (PulsarClientException e) {
            Log.error("Failed to receive event from consumer.", e);
            em.fail(e);
            return;
          }
        }
    }
}