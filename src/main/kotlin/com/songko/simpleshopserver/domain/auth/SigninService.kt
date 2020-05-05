package com.songko.simpleshopserver.domain.auth

import com.songko.simpleshopserver.common.CustomException
import com.songko.simpleshopserver.domain.user.User
import com.songko.simpleshopserver.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class SigninService @Autowired constructor(
        private val userRepository: UserRepository
) {
    fun signin(signinRequest: SigninRequest): SigninResponse {
        // 1. DB에서 해당 사용자 정보가 있는지 확인. Email로 조회.
        // TODO 이메일을 대소문구분 안하는 걸로 했는데, 회원가입시 이 부분 처리가 없음.
        val user = userRepository.findByEmail(signinRequest.email.toLowerCase())
                ?: throw CustomException("로그인 정보를 확인해주세요.")

        // 2. 해시된 비밀번호랑 동일한 지 검증
        if (isNotValidPassword(signinRequest.password, user.password)) {
            throw CustomException("로그인 정보를 확인해주세요.")
        }

        return responseWithTokens(user)
    }

    private fun isNotValidPassword(
            plain: String,
            hashed: String
    ) = BCrypt.checkpw(plain, hashed).not()

    private fun responseWithTokens(user: User) = user.id?.let { userId ->
        SigninResponse(
                JWTUtil.createToken(user.email),
                JWTUtil.createRefreshToken(user.email),
                user.name,
                userId
        )
    } ?: throw IllegalStateException("user.id 없음.")
}