package object base64 {  
  val Pad: Byte = '='
  val WhiteSpaceEnc: Int = -5
  val EqEnc: Int = -1
  val EncMask: Int = 0x3f
  val MaxLine: Int = 76
  val NewLine: Byte = '\n'
}
