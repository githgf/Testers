package cn.hgf.Design.Strategy;

import cn.hgf.Design.FactoryDesign.CashSuper;

/**
 * @Author : Fan Yin
 * @Description : 沿用简单工厂中的例子
 * @Modified :
 */
public class CashContext {

    private CashSuper cashSuper;

    public CashContext(CashSuper cashSuper) {
        this.cashSuper = cashSuper;
    }

    public double finallyPrice(double oldPrice){

        return cashSuper.acceptCash(oldPrice);

    }
}
