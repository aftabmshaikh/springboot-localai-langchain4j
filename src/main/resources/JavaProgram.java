package test;

public class JavaProgram {

public static int calculator(int a, int b, String operation) {
  if(operation.equals("+")) {
     return add(a, b); 
  }else if(operation.equals("-")) {
     return subtract(a, b);  
  }else if(operation.equals("/")) {
     return divide(a, b);  
  }else if(operation.equals("*")) {
     return multiply(a, b);  
  }
  throw new RuntimeException("Invalid Operation!"); 
}
public static int add(int a, int b) {
  return a + b;
}
public static int multiply(int a, int b) {
  return a * b;
}
public static int divide(int a, int b) {
  return a / b;
}
public static int subtract(int a, int b) {
  return a - b;
}
public static void main(String args[]) {
  System.out.println(calculator(2, 2, "+"));	
}
}