package com.songko.simpleshopserver.controller

import com.songko.simpleshopserver.common.ApiResponse
import com.songko.simpleshopserver.domain.product.Product
import com.songko.simpleshopserver.domain.product.ProductImageService
import com.songko.simpleshopserver.domain.product.ProductService
import com.songko.simpleshopserver.domain.product.registration.ProductRegistrationRequest
import com.songko.simpleshopserver.domain.product.registration.ProductRegistrationService
import com.songko.simpleshopserver.domain.product.toProductListItemResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.persistence.Id

@RestController
@RequestMapping("/api/v1")
class ProductApiController @Autowired constructor(
        private val productImageService: ProductImageService,
        private val productRegistrationService: ProductRegistrationService,
        private val productService: ProductService
) {
    @PostMapping("/product_images")
    fun uploadImage(image: MultipartFile) = ApiResponse.ok(
            productImageService.uploadImage(image)
    )

    @PostMapping("/products")
    fun register(@RequestBody request: ProductRegistrationRequest) = ApiResponse.ok(
            productRegistrationService.register(request)
    )

    @GetMapping("/products")
    fun search(
            @RequestParam productId: Long,
            @RequestParam(required = false) categoryId: Int?,
            @RequestParam direction: String,
            @RequestParam(required = false) limit: Int?
    ) = productService
            .search(categoryId, productId, direction, limit ?: 10)
            .mapNotNull(Product::toProductListItemResponse) // null 걸러줌
            .let { ApiResponse.ok(it) }
}