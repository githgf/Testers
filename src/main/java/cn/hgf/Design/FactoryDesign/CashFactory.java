package cn.hgf.Design.FactoryDesign;

/**
 * @Author : Fan Yin
 * @Description :
 * @Modified :
 */
public class CashFactory {

    private CashSuper cashSuper;

    public CashSuper CashFactory(String cashMethod){

        if (cashMethod.equals("none")){

           System.out.println("不打折");

           cashSuper = new CashImpl();

        }else if (cashMethod.equals("rebate")){

            System.out.println("打折");

            cashSuper = new CashRebate("9");

        }else if (cashMethod.equals("returnNum")){

            System.out.println("满减");

            cashSuper = new CashReturn("300","100");

        }

        return cashSuper;

    }


}
