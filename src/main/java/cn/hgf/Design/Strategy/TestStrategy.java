package cn.hgf.Design.Strategy;


import cn.hgf.Design.FactoryDesign.CashImpl;
import cn.hgf.Design.FactoryDesign.CashSuper;

/**
 * @Author : Fan Yin
 * @Description :
 * @Modified :
 */
public class TestStrategy {

    public static void main(String[] args){

        CashSuper cashSuper = new CashImpl();

//        cashSuper cashSuper = new CashReturn("300","100");
//        cashSuper cashSuper = new CashReturn("9");

        CashContext cashContext = new CashContext(cashSuper);



    }


}
