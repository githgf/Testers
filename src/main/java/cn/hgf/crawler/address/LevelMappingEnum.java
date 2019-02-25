package cn.hgf.crawler.address;

/**
 * @author gaofan
 * @date 2019-02-24 8:41
 */
public enum LevelMappingEnum {
    //省
    PROVINCE(1,"province"),
    //市
    CITY(2,"city"),
    //区
    COUNTRY(3,"county"),
    //镇、街道
    TOWN(4,"town"),
    //村
    VILLAGE(5,"village");


    int code;

    String tagPrefix;

    LevelMappingEnum(int code, String tagPrefix) {
        this.code = code;
        this.tagPrefix = tagPrefix;
    }

    public static String getTagPrefix(int code){
        for (LevelMappingEnum levelMappingEnum : values()) {
            if (levelMappingEnum.code == code){
                return levelMappingEnum.tagPrefix;
            }
        }
        return null;
    }

}
