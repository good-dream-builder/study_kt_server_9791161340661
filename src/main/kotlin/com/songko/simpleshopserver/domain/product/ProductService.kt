package com.songko.simpleshopserver.domain.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ProductService @Autowired constructor(
        private val productRepository: ProductRepository
) {
    // 상품 하나 조회. id에 해당하지 않는 상품이 있는 경우 null 반환.
    fun get(id: Long) = productRepository.findByIdOrNull(id)

    fun search(
            categoryId: Int?,
            productId: Long,
            direction: String,
            limit: Int
    ): List<Product> {
        val pageable = PageRequest.of(0, limit) // 0 페이지를 limit 수만큼
        val condition = ProductSearchCondition(
                categoryId != null,
                direction
        )

        return when (condition) {
            NEXT_IN_CATEGORY -> productRepository
                    .findByCategoryIdAndIdLessThanOrderByIdDesc(categoryId, productId, pageable)
            PREV_IN_CATEGORY -> productRepository
                    .findByCategoryIdAndIdGreaterThanOrderByIdDesc(categoryId, productId, pageable)
            else -> throw IllegalArgumentException("상품 검색 조건 오류")
        }
    }

    data class ProductSearchCondition(
            val categoryIdIsNotNull: Boolean,
            val direction: String
    )

    companion object {
        val NEXT_IN_CATEGORY = ProductSearchCondition(true, "next")
        val PREV_IN_CATEGORY = ProductSearchCondition(true, "prev")
    }
}