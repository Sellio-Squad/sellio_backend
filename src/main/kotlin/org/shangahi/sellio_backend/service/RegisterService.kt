package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.model.RegisterUserModel
import org.shangahi.sellio_backend.security.service.JwtService
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.security.service.otp.SmsSender
import org.shangahi.sellio_backend.service.exception.InvalidOtpException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisterService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val refreshTokenService: RefreshTokenService,
    private val otpService: OtpService,
    private val phoneNumberValidator: PhoneNumberValidatorService,
    private val smsSender: SmsSender
) {

    fun requestOtp(phoneNumber: String, defaultRegion: String): OtpRequestResponse {
        val validated = phoneNumberValidator.validate(phoneNumber, defaultRegion)

        if (userService.findUserByPhoneNumber(validated.phoneNumber) != null)
            throw UserPhoneNumberAlreadyExistsException()

        val otpLog = otpService.createOtp(validated.phoneNumber)
        smsSender.sendSms(validated.countryCode, validated.phoneNumber, otpLog.otp)
        return OtpRequestResponse(otpLog.sessionId.toString())
    }

    fun verifyOtp(sessionId: String, otp: String) {
        otpService.verifyOtp(sessionId = UUID.fromString(sessionId), otp = otp)
    }

    fun createUser(registerUserModel: RegisterUserModel): AuthResponse {
        val sessionId = registerUserModel.sessionId
        otpService.validateSessionVerified(sessionId ?: throw InvalidOtpException())

        if (userService.findUserByPhoneNumber(registerUserModel.phoneNumber) != null) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        val user = User(
            phoneNumber = registerUserModel.phoneNumber,
            password = passwordEncoder.encode(registerUserModel.password),
            firstName = registerUserModel.firstName,
            lastName = registerUserModel.lastName,
            avatarUrl = null,
            city = registerUserModel.city,
            country = registerUserModel.country,
            email = registerUserModel.email
            )
        val savedUser = userService.createUser(user)

        otpService.expireOtpBySessionId(sessionId)

        val accessToken = jwtService.generateUserToken(savedUser)
        val refreshToken = refreshTokenService.createRefreshToken(savedUser).refreshToken

        return AuthResponse(accessToken, refreshToken)
    }

}
