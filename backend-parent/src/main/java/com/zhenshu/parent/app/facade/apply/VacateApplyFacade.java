package com.zhenshu.parent.app.facade.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.parent.app.domain.bo.StudentBO;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyIdBO;
import com.zhenshu.parent.app.domain.bo.apply.VacateApplyDetailsBO;
import com.zhenshu.parent.app.domain.dto.LoginUser;
import com.zhenshu.parent.app.domain.po.StudentVacateApply;
import com.zhenshu.parent.app.domain.vo.apply.VacateApplyAddVO;
import com.zhenshu.parent.app.facade.MessageReadFacade;
import com.zhenshu.parent.app.service.StudentVacateApplyService;
import com.zhenshu.parent.common.constant.enums.AlreadyReadType;
import com.zhenshu.parent.common.constant.enums.ApplyStatus;
import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import com.zhenshu.parent.common.utils.bean.BeanUtils;
import com.zhenshu.parent.common.utils.page.PageEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/6/24 15:38
 * @desc 请假申请
 */
@Service
public class VacateApplyFacade {
    @Resource
    private StudentVacateApplyService studentVacateApplyService;
    @Resource
    private MessageReadFacade messageReadFacade;

    /**
     * 分页查询
     *
     * @param studentId 学生id
     * @param queryVO   入参
     * @return 结果
     */
    public IPage<VacateApplyIdBO> listPage(Long studentId, PageEntity queryVO) {
        IPage<VacateApplyIdBO> page = studentVacateApplyService.listPage(studentId, queryVO);
        messageReadFacade.setReadById(AlreadyReadType.VACATE, page);
        return page;
    }

    /**
     * 获取详情
     *
     * @param loginUser     登录用户
     * @param vacateApplyId 请假申请id
     * @return 结果
     */
    public VacateApplyDetailsBO getDetailsById(LoginUser loginUser, Long vacateApplyId) {
        VacateApplyDetailsBO bo = studentVacateApplyService.getDetailsById(vacateApplyId);
        if (bo == null) {
            throw new ServiceException(ErrorEnums.DATA_NOT_EXIST);
        }
        if (!Objects.equals(bo.getStudentId(), loginUser.getStudentId())) {
            throw new ServiceException(ErrorEnums.DATA_NOT_EXIST);
        }
        VacateApplyIdBO applyBO = new VacateApplyIdBO();
        BeanUtils.copyBeanProp(applyBO, bo);
        messageReadFacade.alreadyReadById(applyBO, AlreadyReadType.VACATE, loginUser);
        return bo;
    }

    /**
     * 申请请假
     *
     * @param loginUser 登录用户
     * @param addVO     入参
     */
    public void insert(LoginUser loginUser, VacateApplyAddVO addVO) {
        StudentBO student = loginUser.getSelectStudent();
        StudentVacateApply apply = new StudentVacateApply();
        apply.initCreateProp();
        BeanUtils.copyBeanProp(apply, addVO);
        apply.setApplyTime(LocalDateTime.now());
        apply.setStudentId(loginUser.getStudentId());
        apply.setKgId(student.getKgId());
        apply.setBlocId(student.getBlocId());
        apply.setClassId(student.getClassId());
        apply.setStatus(ApplyStatus.PENDING.getCode());
        apply.setCreateBy(loginUser.getUserId());
        apply.setApplyBy(loginUser.getUserId());
        apply.setType(addVO.getVacateType());
        apply.setReason(addVO.getVacateReason());
        studentVacateApplyService.save(apply);
    }
}
