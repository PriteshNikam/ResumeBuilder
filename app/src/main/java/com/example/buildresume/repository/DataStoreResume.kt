package com.example.buildresume.repository

import androidx.datastore.core.Serializer
import com.example.buildresume.ResumeDetails
import java.io.InputStream
import java.io.OutputStream

object DataStoreResume: Serializer<ResumeDetails> {
    override val defaultValue: ResumeDetails
        get() = ResumeDetails.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ResumeDetails {
        return ResumeDetails.parseFrom(input)
    }

    override suspend fun writeTo(t: ResumeDetails, output: OutputStream) {
        return t.writeTo(output)
    }
}