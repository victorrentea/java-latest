package victor.training.java.sealed.model;


import victor.training.java.sealed.model.Letter.Vocala;

// solutia 1: sealed + permits
sealed public interface Shape
    permits Square, Rectangle, Circle {
}

// solutia 2: sealed sa CONTINA ca nested toate subtipurile permise
sealed  interface Letter   {
  record G() implements Letter{} // super
  final class B implements Letter{} // OK
  // ce altceva decat 'record' poate fi subtip al unei interfete sealed ?
  sealed abstract class Vocala implements Letter permits A, E {} // hm... da pe C cine ?? si cand o ... ?
}

final class A extends Vocala {}
final class E extends Vocala {}
//class Elipsa implements Shape {}

// sealed abstract class SyntaxNode
  // - Statement
  // - sealed abstract class Expression
  //    - record PlusOperator(leftOp,rightOp)
  //    - MinusOperator

  // - Declaration

