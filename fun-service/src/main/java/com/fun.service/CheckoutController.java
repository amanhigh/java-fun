package com.fun.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @Operation(summary = "Add Product",
            description =
                    "Adds Product to Basket for given Customer Id. Connects to Product Service to verify Product Exists.<br/>" +
                            "Customer Id must start with C<0-9*> Eg. C123.<br/>" +
                            "Product Id must start with P<0-9*> Eg. P123.<br/>" +
                            "Creates Fresh Basket if basket doesn't exist for customer. <br/>" +
                            "If Product already exists it doesn't add twice <br/>" +
                            "")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Add product",
            content = {@Content(mediaType = "application/text", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid customer id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product/Customer not found", content = @Content)})
    @PostMapping("/customer/{customerId}/product/{productId}")
    public ResponseEntity<?> getProductById(@NotNull @PathVariable String customerId,@NotNull @PathVariable String productId) {
        if (!customerId.startsWith("C")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Customer Id has to start with C"));
        }
        checkoutService.addProduct(customerId,productId);
        return ResponseEntity.ok("SUCCESS");
    }

    @Operation(summary = "Get Basket",
            description =
                    "Adds Product to Basket for given Customer Id. <br/>" +
                            "Customer Id must start with C<0-9*> Eg. C-123.<br/>" +
                            "Fetches Basket for Customer using CustomerId if present. <br/>" +
                            "")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Add product",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Basket.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid customer id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer basket not found", content = @Content)})
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getBasket(@NotNull @PathVariable String customerId) {
        if (!customerId.startsWith("C")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Customer Id has to start with C"));
        }
        return checkoutService.getBasket(customerId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
