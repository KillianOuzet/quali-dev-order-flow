package org.ormi.priv.tfa.orderflow.api.gateway.productregistry.adapter.inbound.http.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.ormi.priv.tfa.orderflow.api.gateway.productregistry.adapter.inbound.http.dto.RegisterProductCommandDto;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.command.RegisterProduct;

@QuarkusTest
public class ProductRegistryCommandResourceTest {

   @Test
    public void testRegisterProductValid() {
        RegisterProductCommandDto validDto = new RegisterProductCommandDto("Product Name", "Product Description");

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(validDto)
            .when()
            .post("/api/product/registry/registerProduct")
            .then()
            .statusCode(303) // Redirection
            .header("Location", containsString("/api/product/registry/events/productRegistered"));
    }

    @Test
    public void testRegisterProductInvalid() {
        RegisterProductCommandDto invalidDto = new RegisterProductCommandDto(null, "Product Description");

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(invalidDto)
            .when()
            .post("/api/product/registry/registerProduct")
            .then()
            .statusCode(400); // Bad Request
    }

    @Test
    public void testRegisterProductNullBody() {
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .post("/api/product/registry/registerProduct")
            .then()
            .statusCode(400); // Bad Request
    }

    @Test
    public void testUpdateProductValid() {
        UpdateProductCommandDto validDto = new UpdateProductCommandDto("product-id-123", "Updated Name", "Updated Description");

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(validDto)
            .when()
            .post("/api/product/registry/updateProduct")
            .then()
            .statusCode(303) // Redirection
            .header("Location", containsString("/api/product/registry/events/productUpdated"));
    }

    @Test
    public void testUpdateProductInvalid() {
        UpdateProductCommandDto invalidDto = new UpdateProductCommandDto(null, "Updated Name", "Updated Description");

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(invalidDto)
            .when()
            .post("/api/product/registry/updateProduct")
            .then()
            .statusCode(400); // Bad Request
    }

    @Test
    public void testUpdateProductNullBody() {
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .post("/api/product/registry/updateProduct")
            .then()
            .statusCode(400); // Bad Request
    }

    @Test
    public void testRemoveProductValid() {
        RemoveProductCommandDto validDto = new RemoveProductCommandDto("product-id-123");

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(validDto)
            .when()
            .post("/api/product/registry/removeProduct")
            .then()
            .statusCode(303) // Redirection
            .header("Location", containsString("/api/product/registry/events/productRemoved"));
    }

    @Test
    public void testRemoveProductInvalid() {
        RemoveProductCommandDto invalidDto = new RemoveProductCommandDto(null);

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(invalidDto)
            .when()
            .post("/api/product/registry/removeProduct")
            .then()
            .statusCode(400); // Bad Request
    }

    @Test
    public void testRemoveProductNullBody() {
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .post("/api/product/registry/removeProduct")
            .then()
            .statusCode(400); // Bad Request
    }
}
