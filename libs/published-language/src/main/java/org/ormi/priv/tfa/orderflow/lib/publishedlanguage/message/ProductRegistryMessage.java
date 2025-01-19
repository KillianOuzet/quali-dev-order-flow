package org.ormi.priv.tfa.orderflow.lib.publishedlanguage.message;

import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.error.ProductRegistryError;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRegistryEvent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = ProductRegistryEvent.class, name = "ProductRegistryEvent"),
  @JsonSubTypes.Type(value = ProductRegistryError.class, name = "ProductRegistryError")
})

public interface ProductRegistryMessage {
}
