package com.songko.simpleshopserver.domain.user

import java.util.*
import javax.persistence.*

@Entity(name = "user")
class User {
    // TODO JPA 때문에 기본 생성자를 만들면서 여러 생성자를 만들게 되었는데
    //  해당부분 수정이 필요
    
    var email: String
    var password: String
    var name: String

    constructor() {
        this.email = ""
        this.password = ""
        this.name = ""
    }

    constructor(email: String, password: String, name: String){
        this.email = email
        this.password = password
        this.name = name
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var createdAt: Date? = null
    var updatedAt: Date? = null

    @PrePersist
    fun prePersist() {
        createdAt = Date()
        updatedAt = Date()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Date()
    }
}