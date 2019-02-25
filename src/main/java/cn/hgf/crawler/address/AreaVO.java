package cn.hgf.crawler.address;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * @author hans
 * @date 2019-02-23
 * @description 区域信息
 *
 */
@Setter
@Getter
public class AreaVO {

    String code;

    String name;

    int level;

    List<AreaVO> children;

}
