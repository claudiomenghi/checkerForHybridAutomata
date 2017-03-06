package ta.visitors;

import ta.AP;
import ta.TA;
import ta.expressions.EmptyExpression;
import ta.state.State;

public interface TAVisitor<T> {

	public T visit(TA ta);

	public T visit(AP ap);

	public T visit(State state);

	public T visit(EmptyExpression emptyExpression);

}
