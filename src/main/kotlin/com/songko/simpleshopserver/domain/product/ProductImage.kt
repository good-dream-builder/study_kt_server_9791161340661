package com.songko.simpleshopserver.domain.product

import com.songko.simpleshopserver.domain.jpa.BaseEntity
import java.util.*
import javax.persistence.*

@Entity(name = "product_image")
class ProductImage(
        val path: String,
        val productId: Long? = null
) : BaseEntity() {
    constructor() : this("", null)
}