package com.zhenshu.system.remote.kg.base.advanced;

import com.zhenshu.system.business.kg.base.advanced.domain.bo.GradeBO;
import com.zhenshu.system.business.kg.base.advanced.service.IGradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 远程班级调用
 * @date 2022/2/16 0016 19:17
 **/
@Service
public class RemoteGradeService {

    @Resource
    private IGradeService gradeService;

    public List<GradeBO> getByKgId() {
        return gradeService.getList();
    }

}
