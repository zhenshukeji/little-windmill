package com.zhenshu.system.business.ruoyi.facade.message;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 10:25
 * @desc 获取未读消息数量
 */
public interface MessageInterface {
    /**
     * 获取未读消息数量
     *
     * @return 未读消息数
     */
    int getNotReadMessageCount();
}
