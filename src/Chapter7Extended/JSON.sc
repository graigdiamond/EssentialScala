import java.util.Date

sealed trait JsValue {
  def stringify: String
}

final case class JsObject(values: Map[String, JsValue]) extends JsValue {
  def stringify = values
    .map{case (name, value) => "\"" + name + "\":" + value.stringify}
    .mkString("{",",","}")
}

final case class JsString(value: String) extends JsValue{
  override def stringify: String = "\"" + value.replaceAll("\\|\"","\\\\$1") + "\""
}

trait JsWriter[A]{
  def write(a: A): JsValue
}

object JsWriter {
  def apply[A](implicit writer: JsWriter[A]): JsWriter[A] = writer
}

object JsUtil{
  def toJson[A](a: A)(implicit writer: JsWriter[A]) = writer.write(a)
}

sealed trait Visitor {
  def id: String
  def createdAt: Date
  def age: Long = new Date().getTime - createdAt.getTime
}

final case class Anonymous(id: String, createdAt: Date = new Date()) extends Visitor
final case class User(id: String, email: String, createdAt: Date = new Date()) extends Visitor

object VisitorImplicits {
  implicit object AnonymousWriter extends JsWriter[Anonymous]{
    override def write(a: Anonymous): JsValue = JsObject(
      Map(
        "id" -> JsString(a.id),
        "createdAt" -> JsString(a.createdAt.toString)
      )
    )
  }
  implicit object UserWriter extends JsWriter[User]{
    def write(a: User): JsValue = JsObject(
      Map(
        "id" -> JsString(a.id),
        "email" -> JsString(a.email),
        "createdAt" -> JsString(a.createdAt.toString)
      )
    )
  }
  implicit object VisitorWriter extends JsWriter[Visitor]{
    def write(a: Visitor): JsValue = a match {
      case u: User => JsUtil.toJson(u)
      case anon: Anonymous => JsUtil.toJson(anon)
    }
  }

  implicit class VisitorExt(v: Visitor){
    def toJson = JsUtil.toJson(v)
  }

}

object VisitorTester {

  import VisitorImplicits._

  val visitors: Seq[Visitor] = Seq(Anonymous("001"), User("003", "dave@example.com"))
  val visitorString: String = visitors.map(x => x.toJson).mkString(",")

}

VisitorTester.visitorString
