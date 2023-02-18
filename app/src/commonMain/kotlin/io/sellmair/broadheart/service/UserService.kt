package io.sellmair.broadheart.service

import io.sellmair.broadheart.User
import io.sellmair.broadheart.hrSensor.HeartRate
import io.sellmair.broadheart.hrSensor.HrSensorInfo

interface UserService {
    suspend fun currentUser(): User
    suspend fun save(user: User)
    suspend fun saveSensorId(user: User, sensorId: HrSensorInfo.HrSensorId)
    suspend fun saveUpperHeartRateLimit(user: User, limit: HeartRate)

    suspend fun findUser(sensorInfo: HrSensorInfo): User? = findUser(sensorInfo.id)
    suspend fun findUser(sensorId: HrSensorInfo.HrSensorId): User?
    suspend fun findUpperHeartRateLimit(user: User): HeartRate?
}

