package cn.hgf.se.dataStruct;

import java.util.Stack;

public class StudyStack {

    public static void main(String[] args){

        Stack<String> stringStack = new Stack<>();
        //push（）：向栈顶插入值
        stringStack.push("hello");
        stringStack.push("world");
        stringStack.push("!!!");
        //peek（）：获取栈顶的元素（不会将栈顶元素退出）
        String peek = stringStack.peek();
        System.out.println(peek);
        //pop（）：将栈顶的元素推出
        stringStack.pop();
        System.out.println(stringStack);


    }

}
