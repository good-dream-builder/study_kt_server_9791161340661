package com.songko.simpleshopserver.domain.inquiry

import com.songko.simpleshopserver.domain.jpa.BaseEntity
import com.songko.simpleshopserver.domain.product.Product
import com.songko.simpleshopserver.domain.user.User
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Inquiry(
        val productId: Long,
        val requestUserId: Long,
        val productOwnerId: Long,
        val question: String,
        var answer: String? = null
) : BaseEntity() {

    // 질문자 ID
    @ManyToOne
    @JoinColumn(
            name = "requestUserId",
            insertable = false,
            updatable = false
    )
    var requestUser: User? = null

    // 상품등록자 ID
    @ManyToOne
    @JoinColumn(
            name = "productOwnerId",
            insertable = false,
            updatable = false
    )
    var productOwner: User? = null

    // 상품 ID
    @ManyToOne
    @JoinColumn(
            name = "productId",
            insertable = false,
            updatable = false
    )
    var product: Product? = null
}