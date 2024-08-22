package com.zhenshu.api.ruoyi.system;

import com.zhenshu.api.kg.work.backlog.AccidentRecordDisposeController;
import com.zhenshu.api.kg.work.backlog.PaymentApprovalController;
import com.zhenshu.api.kg.work.backlog.StaffVacateApproveController;
import com.zhenshu.api.kg.work.backlog.StudentVacateApproveController;
import com.zhenshu.api.kg.work.health.HeyMedicineApproveController;
import com.zhenshu.api.kg.work.health.ResumeClassesApproveController;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.utils.spring.SpringUtils;
import com.zhenshu.system.business.ruoyi.domain.vo.RouterVo;
import com.zhenshu.system.business.ruoyi.facade.message.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/9 10:33
 * @desc
 */
@Component
public class MessageHandler {
    private final Map<String, MessageInterface> map= new HashMap<>();

    {
        map.put(this.getPermission(StudentVacateApproveController.class), SpringUtils.getBean(StudentVacateApplyMessage.class));
        map.put(this.getPermission(AccidentRecordDisposeController.class), SpringUtils.getBean(AccidentRecordMessage.class));
        map.put(this.getPermission(StaffVacateApproveController.class), SpringUtils.getBean(StaffVacateApplyMessage.class));
        map.put(this.getPermission(PaymentApprovalController.class), SpringUtils.getBean(PaymentProjectMessage.class));
        map.put(this.getPermission(HeyMedicineApproveController.class), SpringUtils.getBean(HeyMedicineApplyMessage.class));;
        map.put(this.getPermission(ResumeClassesApproveController.class), SpringUtils.getBean(ResumeClassesApplyMessage.class));
    }

    private String getPermission(Class<?> clazz) {
        PreAuthorize preAuthorize = clazz.getAnnotation(PreAuthorize.class);
        String value = preAuthorize.value();
        value = value.substring(value.indexOf("'") + Constants.ONE, value.lastIndexOf("'"));
        return value.split(",")[Constants.ZERO];
    }

    /**
     * 获取所有菜单的消息数量, 子菜单的消息数量总和会汇总到父级菜单中;
     * 只会获取成员变量map中包含的菜单id
     *
     * @param routers 菜单集合
     * @return 结果
     */
    public List<MessageNotReadBO> getNotReadMessageCount(List<RouterVo> routers) {
        List<MessageNotReadBO> list = new ArrayList<>();
        for (RouterVo router : routers) {
            MessageNotReadBO bo = new MessageNotReadBO();
            bo.setId(router.getId());
            if (!CollectionUtils.isEmpty(router.getChildren())) {
                List<MessageNotReadBO> children = this.getNotReadMessageCount(router.getChildren());
                bo.setCount(children.stream().mapToInt(MessageNotReadBO::getCount).sum());
                bo.setChildren(children);
            } else {
                bo.setCount(this.getNotReadMessageCount(router.getPermission()));
            }
            if (bo.getCount() != null && bo.getCount() > Constants.ZERO) {
                list.add(bo);
            }
        }
        return list;
    }

    /**
     * 获取单个菜单的消息数量
     *
     * @param permission 权限标识
     * @return 结果
     */
    public Integer getNotReadMessageCount(String permission) {
        MessageInterface messageInterface = map.get(permission);
        if (messageInterface == null) {
            return null;
        }
        return messageInterface.getNotReadMessageCount();
    }

    @Data
    public static class MessageNotReadBO {
        @ApiModelProperty(value = "菜单id")
        private Long id;

        @ApiModelProperty(value = "消息数量")
        private Integer count;

        @ApiModelProperty(value = "子菜单")
        private List<MessageNotReadBO> children;
    }
}
