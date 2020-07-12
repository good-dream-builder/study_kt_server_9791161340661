package com.songko.simpleshopserver.controller

import com.songko.simpleshopserver.common.ApiResponse
import com.songko.simpleshopserver.common.CustomException
import com.songko.simpleshopserver.common.toInquiryResponse
import com.songko.simpleshopserver.domain.auth.UserContextHolder
import com.songko.simpleshopserver.domain.inquiry.Inquiry
import com.songko.simpleshopserver.domain.inquiry.InquiryRequest
import com.songko.simpleshopserver.domain.inquiry.InquirySearchService
import com.songko.simpleshopserver.domain.inquiry.InquiryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 상품 문의 관련 APIs
 */
@RestController
@RequestMapping("/api/v1")
class InquiryApiController @Autowired constructor(
        private val inquiryService: InquiryService,
        private val inquirySearchService: InquirySearchService,
        private val userContextHolder: UserContextHolder
) {
    @PostMapping("/inquiries")
    fun register(@RequestBody request: InquiryRequest) =
            userContextHolder.id?.let { userId ->
                ApiResponse.ok(inquiryService.register(request, userId))
            } ?: throw CustomException("상품 문의에 필요한 사용자 정보가 없습니다.")

    @GetMapping("/inquiries")
    fun getProductInquiries(
            @RequestParam inquiryId: Long,
            @RequestParam(required = false) productId: Long?,
            @RequestParam(required = false) requestUserId: Long?,
            @RequestParam(required = false) productOwnerId: Long?,
            @RequestParam direction: String
    ) = inquirySearchService.getProductInquiries(
            inquiryId,
            productId,
            if (requestUserId == null) null else userContextHolder.id,
            if (productOwnerId == null) null else userContextHolder.id,
            direction
    ).mapNotNull(Inquiry::toInquiryResponse)
            .let { ApiResponse.ok(it) }
}