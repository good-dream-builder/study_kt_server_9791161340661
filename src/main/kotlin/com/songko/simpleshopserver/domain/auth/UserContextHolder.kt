package com.songko.simpleshopserver.domain.auth

import com.songko.simpleshopserver.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserContextHolder @Autowired constructor(
        private val userRepository: UserRepository
) {
    // ThreadLocal은 스레드 영역에서의 로컬 변수 할당을 가능하게 하는 헬퍼 클래스
    private val userHolder = ThreadLocal.withInitial {
        UserHolder()
    }

    val id: Long? get() = userHolder.get().id
    val name: String? get() = userHolder.get().name
    val email: String? get() = userHolder.get().email

    fun set(email: String) = userRepository
            .findByEmail(email)?.let { user ->
                this.userHolder.get().apply {
                    this.id = user.id
                    this.name = user.name
                    this.email = user.email
                }
            }

    fun clear() {
        userHolder.remove()
    }

    class UserHolder {
        var id: Long? = null
        var email: String? = null
        var name: String? = null
    }
}