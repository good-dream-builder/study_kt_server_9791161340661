package com.songko.simpleshopserver.domain.product

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface ProductRepository : JpaRepository<Product, Long> {

    /**
     * 검색을 위한 DB 검색 함수
     */
    fun findByIdGreaterThanAndNameLikeOrderByIdDesc(
            id: Long, keyword: String, pageable: Pageable
    ): List<Product>

    fun findByIdLessThanAndNameLikeOrderByIdDesc(
            id: Long, keyword: String, pageable: Pageable
    ): List<Product>

    /**
     * 스크롤 될 때 호출
     */
    // 위쪽으로 스크롤
    fun findByCategoryIdAndIdGreaterThanOrderByIdDesc(
            categoryId: Int?, id: Long, pageable: Pageable
    ): List<Product>

    // 아래쪽으로 스크롤
    fun findByCategoryIdAndIdLessThanOrderByIdDesc(
            categoryId: Int?, id: Long, pageable: Pageable
    ): List<Product>
}