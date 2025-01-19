package org.ormi.priv.tfa.orderflow.lib.publishedlanguage.error;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.message.ProductRegistryMessage;

public class ProductRegistryError implements ProductRegistryMessage {

    private String message;

    public ProductRegistryError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

