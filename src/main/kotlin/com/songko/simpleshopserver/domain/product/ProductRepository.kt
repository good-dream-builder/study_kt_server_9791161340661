package com.songko.simpleshopserver.domain.product

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface ProductRepository : JpaRepository<Product, Long> {
    /**
     * 위쪽으로 스크롤 될 때 호출
     */
    fun findByCategoryIdAndIdGreaterThanOrderByIdDesc(
            categoryId: Int?, id: Long, pageable: Pageable
    ): List<Product>

    /**
     * 아래쪽으로 스크롤 될 때 호출
     */
    fun findByCategoryIdAndIdLessThanOrderByIdDesc(
            categoryId: Int?, id:Long, pageable: Pageable
    ):List<Product>
}