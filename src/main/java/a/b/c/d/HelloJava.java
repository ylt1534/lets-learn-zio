//package a.b.c.d;
//
//public class HelloJava {
//
//    public static void  main(String[] args)
//    {
//        IntegerStack integerStack = new IntegerStack(5);
//        Stack stack = integerStack;
//        stack.push("Hello");
//        Integer data = integerStack.pop();
//    }
//
//}
//
//
//class Stack<E> {
//    private E[] stackContent;
//
//    public Stack(int capacity) {
//        this.stackContent = (E[]) new Object[capacity];
//    }
//
//    public void push(E data) {
//        // ..
//    }
//}
//
//class IntegerStack extends Stack<Integer> {
//
//    public IntegerStack(int capacity) {
//        super(capacity);
//    }
//
//    public void push(Integer value) {
//        super.push(value);
//    }
//}