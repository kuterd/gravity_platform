package com.kuter.bcw.script;

import java.util.LinkedList;
import java.util.Stack;

import com.kuter.bcw.WorldMan;
import com.kuter.bcw.objects.Platform;

public class ScriptBuilder<T extends ScriptBuilder<?>> {
	private enum Mode {
		MULTIPLEXED, ORDERED;
	}
	
	private class OpList {
		LinkedList<BaseOp> ops = new LinkedList<BaseOp>();
		Mode mode;
		
		public OpList(Mode mode) {
			this.mode = mode;
		}
		
		public BaseOp toOp() {
			BaseOp[] ops = this.ops.toArray(new BaseOp[0]);
			
			if (this.mode == Mode.MULTIPLEXED) {
				return new MultiplexerOp(ops);
			} else if (this.mode == Mode.ORDERED){
				return new ScriptOp(ops);
			}
			
			return null;
		}
	}
	
	private Stack<OpList> mOpListStack = new Stack<OpList>();
	
	public ScriptBuilder() {
		pushMode(Mode.ORDERED);
	}
	
	private void pushMode(Mode mode) {
		mOpListStack.push(new OpList(mode));
	}
	
	@SuppressWarnings("unchecked")
	public T pushMultiplexed() {
		pushMode(Mode.MULTIPLEXED);
		return (T)this;
	}

	public T pushOrdered() {
		pushMode(Mode.ORDERED);
		return (T)this;
	}
	
	@SuppressWarnings("unchecked")
	public T pop() {
		if (mOpListStack.size() < 2)
			throw new IllegalStateException("mOpListStack should have at least 2 items");
	
		BaseOp op = mOpListStack.pop().toOp();
		mOpListStack.peek().ops.add(op);
		
		return (T)this;
	}
	
	public BaseOp done() {
		while (mOpListStack.size() > 1)
			this.pop();
	
		return mOpListStack.peek().toOp();
	}
	
	
	@SuppressWarnings("unchecked")
	public T pushOp(BaseOp op) {
		mOpListStack.peek().ops.addLast(op);
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T wait(int tick) {
		pushOp(new WaitOp(tick));
		return (T) this;
	}
}
