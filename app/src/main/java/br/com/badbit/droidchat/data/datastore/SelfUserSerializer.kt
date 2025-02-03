package br.com.badbit.droidchat.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import br.com.badbit.droidchat.SelfUser
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SelfUserSerializer : Serializer<SelfUser> {
    override val defaultValue: SelfUser
        get() = SelfUser.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SelfUser {
        try {
            return SelfUser.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SelfUser,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}