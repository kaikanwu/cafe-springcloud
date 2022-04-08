package com.kaikanwu.cafe.infrastructure.domain.warehouse;

public enum StockStatus {

    /**
     * 增加货物库存
     */
    INCREASE,

    /**
     * 货物冻结：移动指定数量到冻结状态
     */
    FROZEN,

    /**
     * 货物解冻：从冻结货物中移动指定数量到正常状态
     */
    THAWED,

    /**
     * 货物售出：从冻结状态的货物中扣减
     */
    DECREASE
}
