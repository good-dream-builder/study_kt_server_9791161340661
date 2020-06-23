package com.songko.simpleshopserver.domain.product

import com.songko.simpleshopserver.common.CustomException

data class ProductResponse(
        val id: Long,
        val name: String,
        val description: String,
        val price: Int,
        val status: String,
        val sellerId: Long,
        val imagePaths: List<String>
)

// id가 null 이 아닌 경우에만 정상 응답 반환
fun Product.toProductResponse() = id?.let {
    ProductResponse(
            it,
            name,
            description,
            price,
            status.name,
            userId,
            images.map { it.path }
    )
} ?: throw CustomException("상품 정보를 찾을 수 없습니다.")