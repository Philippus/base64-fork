package base64

import java.nio.ByteBuffer
import java.nio.charset.Charset

import scala.annotation.implicitNotFound

@implicitNotFound(msg = "base64 Input[T] type class instance for type ${T} not found")
trait Input[T] {
  def apply(t: T): Array[Byte]
}

object Input {
  private[this] val utf8 = Charset.forName("UTF-8")

  implicit val ByteBuffers: Input[ByteBuffer] =
    new Input[ByteBuffer] {
      def apply(in: ByteBuffer): Array[Byte] = in.array
    }

  implicit val Bytes: Input[Array[Byte]] =
    new Input[Array[Byte]] {
      def apply(in: Array[Byte]): Array[Byte] = in
    }

  implicit val Utf8Str: Input[String] =
    new Input[String] {
      def apply(in: String): Array[Byte] = Str(in, utf8)
    }

  implicit val Str: Input[(String, Charset)] =
    new Input[(String, Charset)] {
      def apply(in: (String, Charset)): Array[Byte] = Bytes(in._1.getBytes(in._2.name()))
    }

  def apply[T: Input](in: T): Array[Byte] = implicitly[Input[T]].apply(in)
}
