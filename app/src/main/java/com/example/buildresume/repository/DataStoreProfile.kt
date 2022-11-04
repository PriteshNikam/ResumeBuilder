package com.example.buildresume.repository

import androidx.datastore.core.Serializer
import com.example.buildresume.ProfileEditDetails
import com.example.buildresume.UserProfileDetails
import com.example.buildresume.data.Profile
import java.io.InputStream
import java.io.OutputStream

object DataStoreProfile: Serializer<UserProfileDetails> {
    override val defaultValue: UserProfileDetails
        get() = UserProfileDetails.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProfileDetails {
        return UserProfileDetails.parseFrom(input)
    }

    override suspend fun writeTo(t: UserProfileDetails, output: OutputStream) {
        return t.writeTo(output)
    }
}