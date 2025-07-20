package ru.netology.tco2rus.repository

import ru.netology.tco2rus.api.ApiService
import ru.netology.tco2rus.api.dto.DriverProfileDto
import ru.netology.tco2rus.data.DriverProfile
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun getProfile(): DriverProfile {
        return api.getDriverProfile(tokenManager.getToken()).toDomain()
    }

    suspend fun updateProfile(profile: DriverProfile) {
        api.updateProfile(
            DriverProfileDto(
                name = profile.fullName,
                email = profile.email,
                phone = profile.phone,
                columnNumber = profile.columnNumber,
                columnPhone = profile.columnPhone,
                logistPhone = profile.logistPhone,
                avatarUrl = profile.avatarUrl
            ),
            tokenManager.getToken()
        )
    }

    private fun DriverProfileDto.toDomain(): DriverProfile {
        return DriverProfile(
            id = id,
            fullName = fullName,
            phone = phone,
            email = email ?: "",
            columnNumber = columnNumber,
            columnPhone = columnPhone,
            logistPhone = logistPhone,
            avatarUrl = avatarUrl ?: ""
        )
    }
}
