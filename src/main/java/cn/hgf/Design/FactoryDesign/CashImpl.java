package cn.hgf.Design.FactoryDesign;

/**
 * @Author : Fan Yin
 * @Description : 正常收取
 * @Modified :
 */
public class CashImpl extends CashSuper{

    @Override
    public double acceptCash(double oldPrice) {
        return oldPrice;
    }

}

/**
 *  @Author : Fan Yin
 *
 *  @Description : 打折收取现金类
 *
 *
 */
class CashRebate extends CashSuper{
    private double rebateNum;
    /**
     *  @Description : 初始化折扣
     *  @Param : rebateNum
     *
     *  @Return :
     */
    public CashRebate(String rebateNum) {

        this.rebateNum =  Double.valueOf(rebateNum);

    }

    @Override
    public double acceptCash(double oldPrice) {

        return oldPrice*rebateNum;
    }
}
/**
 *  @Author : Fan Yin
 *
 *  @Description : 满减活动
 *
 */
class CashReturn extends CashSuper{
    private double max;
    private double returnNum;

    public CashReturn(String max, String returnNum) {
        this.max = Double.parseDouble(max);
        this.returnNum = Double.parseDouble(returnNum);
    }

    @Override
    public double acceptCash(double oldPrice) {
        double returnNum = oldPrice;

        if (oldPrice > max){

            returnNum = oldPrice - Math.ceil(oldPrice/max)*returnNum;

        }

        return returnNum;

    }
}