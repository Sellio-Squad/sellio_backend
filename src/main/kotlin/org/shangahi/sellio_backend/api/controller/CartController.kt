package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.AddCartItemRequest
import org.shangahi.sellio_backend.api.dto.request.UpdateCartItemRequest
import org.shangahi.sellio_backend.api.dto.response.CartResponse
import org.shangahi.sellio_backend.api.swagger.doc.CartDoc
import org.shangahi.sellio_backend.service.CartService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/cart")
@Tag(name = "Cart", description = "Endpoints for managing the shopping cart")
class CartController(private val cartService: CartService) {

    @CartDoc.GetCart
    @GetMapping
    fun getCart(@AuthenticationPrincipal userId: UUID): CartResponse {
        return cartService.getOrCreateCart(userId)
    }

    @CartDoc.AddItem
    @PostMapping("/items")
    fun addItem(
        @AuthenticationPrincipal userId: UUID,
        @Valid @RequestBody request: AddCartItemRequest
    ): CartResponse {
        return cartService.addItem(userId, request)
    }

    @CartDoc.UpdateItemQuantity
    @PutMapping("/items/{itemId}")
    fun updateItemQuantity(
        @AuthenticationPrincipal userId: UUID,
        @PathVariable itemId: UUID,
        @Valid @RequestBody request: UpdateCartItemRequest
    ): CartResponse {
        return cartService.updateItemQuantity(userId, itemId, request)
    }

    @CartDoc.RemoveItem
    @DeleteMapping("/items/{itemId}")
    fun removeItem(
        @AuthenticationPrincipal userId: UUID,
        @PathVariable itemId: UUID
    ): CartResponse {
        return cartService.removeItem(userId, itemId)
    }
}
