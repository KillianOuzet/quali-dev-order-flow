package org.ormi.priv.tfa.orderflow.product.registry.aggregate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.smallrye.mutiny.Uni;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.command.*;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.*;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.valueobject.ProductId;
import org.ormi.priv.tfa.orderflow.product.registry.aggregate.service.ProductRegistryService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRegistryTest {

    @InjectMocks
    private ProductRegistry productRegistry;

    @Mock
    private ProductRegistryService productRegistryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productRegistry = new ProductRegistry(productRegistryService);
    }

    // Méthode handle

    @Test
    void testHandleRegisterProductCommand_Valid() {
        RegisterProduct command = new RegisterProduct(new ProductId("product-id"), "Product Name", "Product Description");
        ProductRegistered expectedEvent = new ProductRegistered(
            new ProductRegistered.Payload(command.productId(), command.name(), command.productDescription()));

        when(productRegistryService.registerProduct(eq(productRegistry), eq(command)))
            .thenReturn(Uni.createFrom().item(expectedEvent));

        var result = productRegistry.handle(command).await().indefinitely();

        verify(productRegistryService).registerProduct(productRegistry, command);
        assertEquals(expectedEvent, result);
    }

    @Test
    void testHandleRemoveProductCommand_Valid() {
        RemoveProduct command = new RemoveProduct(new ProductId("product-id"));
        ProductRemoved expectedEvent = new ProductRemoved(new ProductRemoved.Payload(command.productId()));

        when(productRegistryService.removeProduct(eq(productRegistry), eq(command)))
            .thenReturn(Uni.createFrom().item(expectedEvent));

        var result = productRegistry.handle(command).await().indefinitely();

        verify(productRegistryService).removeProduct(productRegistry, command);
        assertEquals(expectedEvent, result);
    }

    @Test
    void testHandleUpdateProductCommand_Valid() {
        UpdateProduct command = new UpdateProduct(new ProductId("product-id"), "Updated Name", "Updated Description");
        ProductUpdated expectedEvent = new ProductUpdated(
            new ProductUpdated.Payload(command.productId(), command.name(), command.productDescription()));

        when(productRegistryService.updateProduct(eq(productRegistry), eq(command)))
            .thenReturn(Uni.createFrom().item(expectedEvent));

        var result = productRegistry.handle(command).await().indefinitely();

        verify(productRegistryService).updateProduct(productRegistry, command);
        assertEquals(expectedEvent, result);
    }

    @Test
    void testHandleNullCommand_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> productRegistry.handle(null).await().indefinitely());
    }

    // Méthode apply

    @Test
    void testApplyProductRegisteredEvent_Valid() {
        ProductRegistered event = new ProductRegistered(
            new ProductRegistered.Payload(new ProductId("product-id"), "Product Name", "Product Description"));

        productRegistry.apply(event);

        assertTrue(productRegistry.hasProductWithId(new ProductId("product-id")));
    }

    @Test
    void testApplyProductRemovedEvent_Valid() {
        ProductRegistered event = new ProductRegistered(
            new ProductRegistered.Payload(new ProductId("product-id"), "Product Name", "Product Description"));
        productRegistry.apply(event);

        ProductRemoved removeEvent = new ProductRemoved(new ProductRemoved.Payload(new ProductId("product-id")));
        productRegistry.apply(removeEvent);

        assertFalse(productRegistry.hasProductWithId(new ProductId("product-id")));
    }

    @Test
    void testApplyProductUpdatedEvent_Valid() {
        ProductRegistered event = new ProductRegistered(
            new ProductRegistered.Payload(new ProductId("product-id"), "Old Name", "Old Description"));
        productRegistry.apply(event);

        ProductUpdated updateEvent = new ProductUpdated(
            new ProductUpdated.Payload(new ProductId("product-id"), "Updated Name", "Updated Description"));
        productRegistry.apply(updateEvent);

        assertTrue(productRegistry.hasProductWithId(new ProductId("product-id")));
        assertEquals("Updated Name", productRegistry.products.get(new ProductId("product-id")).getName());
    }

    @Test
    void testApplyUnhandledEvent_ShouldLogWarning() {
        ProductRegistryEvent event = mock(ProductRegistryEvent.class);
        productRegistry.apply(event);
        // Expected no changes, log warning message
    }

    // Méthode getVersion

    @Test
    void testGetVersion_ShouldReturnDefinedVersion() {
        assertEquals(0, productRegistry.getVersion());
    }

    @Test
    void testGetVersion_AfterIncrement_ShouldIncrease() {
        productRegistry.incrementVersion();
        assertEquals(1, productRegistry.getVersion());
    }

    // Méthode hasProductWithId

    @Test
    void testHasProductWithId_ExistingProduct() {
        ProductRegistered event = new ProductRegistered(
            new ProductRegistered.Payload(new ProductId("product-id"), "Product Name", "Product Description"));
        productRegistry.apply(event);

        assertTrue(productRegistry.hasProductWithId(new ProductId("product-id")));
    }

    @Test
    void testHasProductWithId_NonExistingProduct() {
        assertFalse(productRegistry.hasProductWithId(new ProductId("non-existing-id")));
    }

    // Méthode hasProduct

    @Test
    void testHasProduct_ExistingProduct() {
        ProductRegistered event = new ProductRegistered(
            new ProductRegistered.Payload(new ProductId("product-id"), "Product Name", "Product Description"));
        productRegistry.apply(event);

        Product product = new Product(new ProductId("product-id"), "Product Name", "Product Description");
        assertTrue(productRegistry.hasProduct(product));
    }

    @Test
    void testHasProduct_NonExistingProduct() {
        Product product = new Product(new ProductId("non-existing-id"), "Product Name", "Product Description");
        assertFalse(productRegistry.hasProduct(product));
    }

    // Méthode isProductNameAvailable

    @Test
    void testIsProductNameAvailable_NameAvailable() {
        assertTrue(productRegistry.isProductNameAvailable("Available Name"));
    }

    @Test
    void testIsProductNameAvailable_NameUnavailable() {
        ProductRegistered event = new ProductRegistered(
            new ProductRegistered.Payload(new ProductId("product-id"), "Unavailable Name", "Product Description"));
        productRegistry.apply(event);

        assertFalse(productRegistry.isProductNameAvailable("Unavailable Name"));
    }
}
