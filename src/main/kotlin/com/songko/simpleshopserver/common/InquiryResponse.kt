package com.songko.simpleshopserver.common

import com.songko.simpleshopserver.domain.inquiry.Inquiry

/**
 * 문의 등록과 검색에서 사용할 데이터 객체
 */
data class InquiryResponse(
        val id: Long,
        val question: String,
        val answer: String?,
        val requestUserName: String,
        val requestUserId: Long,
        val productOwnerName: String,
        val productOwnerId: Long,
        val productName: String,
        val productId: Long
)

fun Inquiry.toInquiryResponse() = id?.let {
    InquiryResponse(
            it,
            question,
            answer,
            requestUser?.name ?: "이름없음",
            requestUser?.id ?: -1,
            productOwner?.name ?: "이름없음",
            productOwner?.id ?: -1,
            product?.name ?: "상품명없음",
            productId
    )
}