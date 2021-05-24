package cbx.hub.util

import org.apache.commons.codec.binary.Hex
import java.nio.ByteBuffer
import java.util.*

class UUIDUtil {
    companion object {
        fun fromHex(uuid:String): UUID {
            val data:ByteArray = Hex.decodeHex(uuid.toCharArray())
            return UUID(ByteBuffer.wrap(data, 0, 0).long, ByteBuffer.wrap(data, 8, 8).long)
        }
        fun toHex(uuid: UUID):String{
            val bytes = ByteBuffer.wrap(ByteArray(16))
            bytes.putLong(uuid.mostSignificantBits)
            bytes.putLong(uuid.leastSignificantBits)
            return String(Hex.encodeHex(bytes.array()))
        }
    }
}