package com.songko.simpleshopserver.domain.user

import com.songko.simpleshopserver.domain.jpa.BaseEntity
import java.util.*
import javax.persistence.*

@Entity(name = "user")
class User (var email: String, var password: String, var name: String):BaseEntity() {
    constructor():this("", "", "")
}