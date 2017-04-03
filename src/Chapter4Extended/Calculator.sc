//Solution to first extended exercise

sealed trait Expression{
  def eval: CalcResult = this match {
    case Number(value) => Success(value)
    case Addition(left, right) => left.eval.flatMap(a => right.eval.map(b => a + b))
    case Subtraction(left, right) => left.eval.flatMap(a => right.eval.map(b => a - b))
    case Division(left, right) => right.eval match{
      case Success(v) => if(v != 0.0) left.eval.flatMap(a => right.eval.map(b => a / b)) else Failure("Cannot divide by zero!")
      case Failure(m) => Failure(m)
    }
    case SquareRoot(expr) => expr.eval match{
      case Success(v) => if(v >= 0.0) Success(Math.pow(v,0.5)) else Failure(s"Cannot take a square root of a negative number: $v")
      case Failure(m) => Failure(m)
    }
  }
}
final case class Number(value: Double) extends Expression
final case class Addition(left: Expression, right: Expression) extends Expression
final case class Subtraction(left: Expression, right: Expression) extends Expression
final case class Division(left: Expression, right: Expression) extends Expression
final case class SquareRoot(expr: Expression) extends Expression

sealed trait CalcResult{
  def flatMap(f: Double => CalcResult): CalcResult = this match{
    case Failure(m) => Failure(m)
    case Success(v) => f(v)
  }
  def map(f: Double => Double): CalcResult = this match{
    case Failure(m) => Failure(m)
    case Success(v) => Success(f(v))
  }

}
final case class Success(value: Double) extends CalcResult
final case class Failure(message: String) extends CalcResult

object UnitTests{

  val test1 = Addition(SquareRoot(Subtraction(Number(2.0),Number(4.0))),Number(2.0)).eval
  val test2 = Addition(SquareRoot(Number(4.0)),Number(2.0)).eval
  val test3 = Division(Number(4.0),Number(0.0)).eval
  val test4 = Division(Number(12.0),Number(3.0)).eval

}

UnitTests.test1
UnitTests.test2
UnitTests.test3
UnitTests.test4
