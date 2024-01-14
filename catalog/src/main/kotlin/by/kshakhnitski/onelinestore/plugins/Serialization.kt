package by.kshakhnitski.onelinestore.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.math.BigDecimal

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(contentType = ContentType.Application.Json, json = Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                contextual(BigDecimal::class, BigDecimalSerializer)
            }
            ignoreUnknownKeys = true
        })
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = BigDecimal::class)
object BigDecimalSerializer : KSerializer<BigDecimal> {

    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: BigDecimal) {
        encoder.encodeString(value.toPlainString())
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return BigDecimal(decoder.decodeString())
    }
}
