package base64

import java.util.{Base64 => Java89Base64}
import javax.xml.bind.DatatypeConverter
import sun.misc

import io.netty.handler.codec.base64.{Base64 => NettyBase64}
import io.netty.buffer.Unpooled
import org.apache.commons.codec.binary.Base64

object Bench {
  val bytes =  "Man is distinguished, not only by his reason, but by this singular passion from other animals, which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable generation of knowledge, exceeds the short vehemence of any carnal pleasure.".getBytes
  
  val encoded = "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlzIHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c3Qgb2YgdGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGludWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRoZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=".getBytes

  def main(args: Array[String]) {
    def repeat(times: Int)(f: => Unit) = {
      val before = System.currentTimeMillis
      for (_ <- 0 to times) f
      System.currentTimeMillis - before
    }

    def run(times: Int = 1000, log: Boolean = false) = {
      val repeatNTimes: (=> Unit) => Long = repeat(times)
      val sunEncoder = new misc.BASE64Encoder()
      val sunDecoder = new misc.BASE64Decoder()

      val apache = repeatNTimes(Base64.encodeBase64(bytes))
      val netty = repeatNTimes(NettyBase64.encode(Unpooled.copiedBuffer(bytes)))
      val ours = repeatNTimes(Encode(bytes))
      val java67 = repeatNTimes(DatatypeConverter.printBase64Binary(bytes))
      val java89 = repeatNTimes(Java89Base64.getEncoder.encode(bytes))
      val sun = repeatNTimes(sunEncoder.encode(bytes))

      val apacheDec = repeatNTimes(Base64.decodeBase64(encoded))
      val nettyDec = repeatNTimes(NettyBase64.decode(Unpooled.copiedBuffer(encoded)))
      val oursDec = repeatNTimes(Decode(encoded))
      val java67Dec = repeatNTimes(DatatypeConverter.parseBase64Binary(encoded.toString))
      val java89Dec = repeatNTimes(Java89Base64.getDecoder.decode(encoded))
      val sunDec = repeatNTimes(sunDecoder.decodeBuffer(encoded.toString))

      if (log) {
        println("enc apache commons (byte arrays) took %s ms" format apache) // 430ms / 150000
        println("enc netty (byte buf)             took %s ms" format netty) // 239ms / 150000
        println("enc ours (byte arrays)           took %s ms" format ours) // 273ms / 150000
        println("enc java67 (byte arrays)         took %s ms" format java67) // 91ms / 150000
        println("enc java89 (byte arrays)         took %s ms" format java89) // 42ms / 150000
        println("enc sun (byte arrays)            took %s ms" format sun) // 1796ms / 150000

        println("dec apache commons (byte arrays) took %s ms" format apacheDec) // 312ms / 150000
        println("dec netty (byte buf)             took %s ms" format nettyDec) // 270ms / 150000
        println("dec ours (byte arrays)           took %s ms" format oursDec)   // 199ms / 150000
        println("dec java67 (byte arrays)         took %s ms" format java67Dec)   // 23ms / 150000
        println("dec java89 (byte arrays)         took %s ms" format java89Dec)   // 106ms / 150000
        println("dec sun (byte arrays)            took %s ms" format sunDec)   // 181ms / 150000
      }
    }

    // warmup
    run()

    // bench
    run(times = 150000, log = true)
  } 
}
