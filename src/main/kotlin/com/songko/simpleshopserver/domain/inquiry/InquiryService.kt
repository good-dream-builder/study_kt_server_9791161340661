package com.songko.simpleshopserver.domain.inquiry

import com.songko.simpleshopserver.common.CustomException
import com.songko.simpleshopserver.domain.fcm.NotificationService
import com.songko.simpleshopserver.domain.product.Product
import com.songko.simpleshopserver.domain.product.ProductService
import com.songko.simpleshopserver.domain.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * 상품 문의 등록 처리 서비스
 */
@Service
class InquiryService @Autowired constructor(
        private val inquiryRepository: InquiryRepository,
        private val productService: ProductService,
        private val userService: UserService,
        private val notificationService: NotificationService
) {
    // 상품 문의 등록
    fun register(request: InquiryRequest, userId: Long) {
        val product = productService.get(request.productId)
                ?: throw CustomException("상품 정보를 찾을 수 없습니다.")

        // 문의 내용 저장
        val inquiry = saveInquiry(request, userId, product)
        
        // 푸시 알림 보냄
        sendNotification(request, inquiry)
    }

    // 상품 문의 저장
    private fun saveInquiry(
            request: InquiryRequest,
            userId: Long,
            product: Product
    ) = if (request.type == InquiryType.QUESTION) {
        // QUESTION : 문의 생성 > 저장

        if (userId == product.userId) {
            throw CustomException("자신의 상품에는 질문할 수 없습니다.")
        }

        val inquiry = Inquiry(
                request.productId,
                userId,
                product.userId,
                request.content
        )
        inquiryRepository.save(inquiry)
    } else {
        // ANSWER : 기존 문의 조회 > answer 필드에 답변 추가 > 업데이트

        request.inquiryId
                ?.let(inquiryRepository::findByIdOrNull)
                ?.apply {
                    // 3
                    require(productId == request.productId) { "답변 데이터 오류." }
                    if (productOwnerId != userId) {
                        throw CustomException("자신의 상품에 만 답변할 수 있습니다.")
                    }
                    answer = request.content
                    inquiryRepository.save(this)
                } ?: throw CustomException("질문 데이터를 찾을 수 없습니다.")
    }

    // 알림 대상자에게 푸시
    private fun sendNotification(
            request: InquiryRequest,
            inquiry: Inquiry
    ) {
        val targetUser = if (request.type == InquiryType.QUESTION) {
            // 문의 : 상품 등록자에게
            userService.find(inquiry.productOwnerId)
        } else {
            // 답변 : 문의자에게
            userService.find(inquiry.requestUserId)
        }

        targetUser?.run {
            notificationService.sendToUser(this, "상품문의", request.content)
        }
    }
}