package com.songko.simpleshopserver.domain.inquiry

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface InquiryRepository : JpaRepository<Inquiry, Long> {

    // 단일 상품에 대한 문의 리스트 조회
    fun findByProductIdAndIdLessThanOrderByIdDesc(
            productId: Long?,
            inquiryId: Long,
            pageable: Pageable
    ): List<Inquiry>


    fun findByProductIdAndIdGreaterThanOrderByIdDesc(
            productId: Long?,
            inquiryId: Long,
            pageable: Pageable
    ): List<Inquiry>

    // 사용자가 올린 문의 리스트 조회
    fun findByRequestUserIdAndIdLessThanOrderByIdDesc(
            requestUserId: Long?,
            inquiryId: Long,
            pageable: Pageable
    ): List<Inquiry>

    fun findByRequestUserIdAndIdGreaterThanOrderByIdDesc(
            requestUserId: Long?,
            inquiryId: Long,
            pageable: Pageable
    ): List<Inquiry>

    // 사용자가 등록한 상품들에 대한 문의 리스트 조회
    fun findByProductOwnerIdAndIdLessThanOrderByIdDesc(
            productOwnerId: Long?,
            inquiryId: Long,
            pageable: Pageable
    ): List<Inquiry>

    fun findByProductOwnerIdAndIdGreaterThanOrderByIdDesc(
            productOwnerId: Long?,
            inquiryId: Long,
            pageable: Pageable
    ): List<Inquiry>

}