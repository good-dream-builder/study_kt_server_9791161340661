package com.songko.simpleshopserver.domain.product

import com.songko.simpleshopserver.domain.jpa.BaseEntity
import java.util.*
import javax.persistence.*

@Entity(name = "product")
class Product(
        @Column(length = 40)
        var name: String,
        @Column(length = 500)
        var description: String,
        var price: Int,
        var categoryId: Int,
        @Enumerated(EnumType.STRING)
        var status: ProductStatus,
        @OneToMany
        @JoinColumn(name = "productId")
        var images: MutableList<ProductImage>,
        val userId: Long
) : BaseEntity() {
        constructor():this("","",0,0, ProductStatus.SOLD_OUT, mutableListOf(), 0)
}