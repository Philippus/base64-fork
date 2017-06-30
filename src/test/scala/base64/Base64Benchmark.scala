package base64

import java.util.{ Base64 => Java89Base64 }
import javax.xml.bind.DatatypeConverter
import sun.misc

import com.google.caliper.SimpleBenchmark
import io.netty.buffer.Unpooled
import io.netty.handler.codec.base64.{ Base64 => NettyBase64 }
import org.apache.commons.codec.binary.Base64

class Base64Benchmark extends SimpleBenchmark {
  def timeApacheEnc(n: Int) = for (i <- 0 to n) {
    Base64.encodeBase64(Bench.bytes)
  }

  def timeApacheDec(n: Int) = for (i <- 0 to n) {
    Base64.decodeBase64(Bench.encoded)
  }

  def timeNettyEnc(n: Int) = for (i <- 0 to n) {
    NettyBase64.encode(Unpooled.copiedBuffer(Bench.bytes))
  }

  def timeNettyDec(n: Int) = for (i <- 0 to n) {
    NettyBase64.decode(Unpooled.copiedBuffer(Bench.encoded))
  }

  def timeJava67Enc(n: Int) = for (i <- 0 to n) {
    DatatypeConverter.printBase64Binary(Bench.bytes)
  }

  def timeJava67Dec(n: Int) = for (i <- 0 to n) {
    DatatypeConverter.parseBase64Binary(Bench.encoded.toString)
  }

  def timeJava89Enc(n: Int) = for (i <- 0 to n) {
    Java89Base64.getEncoder.encode(Bench.bytes)
  }

  def timeJava89Dec(n: Int) = for (i <- 0 to n) {
    Java89Base64.getDecoder.decode(Bench.encoded)
  }

  val sunEncoder = new misc.BASE64Encoder()
  def timeSunEnc(n: Int) = for (i <- 0 to n) {
    sunEncoder.encode(Bench.bytes)
  }

  val sunDecoder = new misc.BASE64Decoder()
  def timeSunDec(n: Int) = for (i <- 0 to n) {
    sunDecoder.decodeBuffer(Bench.encoded.toString)
  }

  def timeOurEnc(n: Int) = for (i <- 0 to n) {
    Encode(Bench.bytes)
  }

  def timeOurDecode(n: Int) = for (i <- 0 to n) {
    Decode(Bench.encoded)
  }

  /*
    [info] benchmark     ns linear runtime
    [info] ApacheEnc 1843,6 ============
    [info] ApacheDec 1859,8 ============
    [info]  NettyEnc 1611,1 ===========
    [info]  NettyDec 2962,9 ====================
    [info] Java67Enc  456,1 ===
    [info] Java67Dec   77,5 =
    [info] Java89Enc  275,0 =
    [info] Java89Dec  710,9 ====
    [info]    SunEnc 4333,2 ==============================
    [info]    SunDec 1337,7 =========
    [info]    OurEnc 1396,4 =========
    [info] OurDecode 1295,8 ========
  */
}
