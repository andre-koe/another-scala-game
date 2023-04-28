package model.interpreter

trait IExpressionParser {
  def parse(str: String): IExpression
}
