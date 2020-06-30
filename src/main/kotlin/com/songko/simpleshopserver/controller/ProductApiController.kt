package com.songko.simpleshopserver.controller

import com.songko.simpleshopserver.common.ApiResponse
import com.songko.simpleshopserver.common.CustomException
import com.songko.simpleshopserver.domain.product.*
import com.songko.simpleshopserver.domain.product.registration.ProductRegistrationRequest
import com.songko.simpleshopserver.domain.product.registration.ProductRegistrationService
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
    @GetMapping("/products/{id}")
    fun get(@PathVariable id: Long) = productService.get(id)?.let {
        ApiResponse.ok(it.toProductResponse())
    } ?: throw CustomException("상품 정보를 찾을 수 없습니다.")

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
            @RequestParam(required = false) keyword: String?,
            @RequestParam(required = false) limit: Int?
    ) = productService
            .search(categoryId, productId, direction, keyword, limit ?: 10)
            .mapNotNull(Product::toProductListItemResponse) // null 걸러줌
            .let { ApiResponse.ok(it) }
}