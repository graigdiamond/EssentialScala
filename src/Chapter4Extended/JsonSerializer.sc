//
// Going off of http://www.json.org/ for this exercise
//
// Seems like JSON needs to represent the following values:
//   String
//   Number
//   Boolean
//   Null
//   Array
//   Object
//
// An array is a list of values surrounded by [ and ]
//
// An object is a key/value pair defined as {key:value}
//
//

sealed trait JsValue{

  def asString: String = this match {
    case JsNumber(num) => num.toString
    case JsString(str) => s""""${str}""""
    case JsBoolean(bool) => bool.toString
    case JsNull => "null"
    case JsArray(arr) => "[" + arr.map(_.asString).mkString(",") + "]"
    case JsObject(obj) => "{" + obj.map(_.asString).mkString(",") + "}"
    case JsObjectPair(key, value) => s""""${key}":${value.asString}"""
  }

}
sealed trait JsBasicValue extends JsValue
final case class JsNumber(value: Double) extends JsBasicValue
final case class JsString(value: String) extends JsBasicValue
final case class JsBoolean(value: Boolean) extends JsBasicValue
case object JsNull extends JsBasicValue
final case class JsArray(seq: Seq[JsValue]) extends JsValue
final case class JsObjectPair(key: String, value: JsValue) extends JsValue
final case class JsObject(seq: Seq[JsObjectPair]) extends JsValue

object test{
  val test1 = JsArray(Seq(JsString("hello"),JsString("my"),JsString("baby"),JsNumber(4.0),JsBoolean(false))).asString
  val test2 = JsObject(Seq(JsObjectPair("a",JsArray(Seq(JsNumber(1),JsNumber(2),JsArray(Seq(JsBoolean(false),JsBoolean(true))),
    JsObject(Seq(JsObjectPair("b",JsArray(Seq(JsObject(Seq(JsObjectPair("d",JsString("you fool")))))))))))))).asString
}

test.test1
test.test2


