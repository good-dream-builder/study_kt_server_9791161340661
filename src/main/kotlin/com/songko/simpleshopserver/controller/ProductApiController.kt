package com.songko.simpleshopserver.controller

import com.songko.simpleshopserver.common.ApiResponse
import com.songko.simpleshopserver.domain.product.ProductImageService
import com.songko.simpleshopserver.domain.product.registration.ProductRegistrationRequest
import com.songko.simpleshopserver.domain.product.registration.ProductRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ProductApiController @Autowired constructor(
        private val productImageService: ProductImageService,
        private val productRegistrationService: ProductRegistrationService
) {
    @PostMapping("/product_images")
    fun uploadImage(image: MultipartFile) = ApiResponse.ok(
            productImageService.uploadImage(image)
    )

    @PostMapping("/products")
    fun register(@RequestBody request: ProductRegistrationRequest) = ApiResponse.ok(
            productRegistrationService.register(request)
    )
}