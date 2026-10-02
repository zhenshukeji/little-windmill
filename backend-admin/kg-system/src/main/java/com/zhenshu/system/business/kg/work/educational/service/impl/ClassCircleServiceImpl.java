package com.zhenshu.system.business.kg.work.educational.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.LoginIdentity;
import com.zhenshu.common.enums.base.UserIdentity;
import com.zhenshu.common.enums.kg.work.educational.CircleUploadType;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.work.educational.domain.bo.ClassCircleBO;
import com.zhenshu.system.business.kg.work.educational.domain.po.ClassCircle;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleAddVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleDeleteVO;
import com.zhenshu.system.business.kg.work.educational.domain.vo.ClassCircleQueryVO;
import com.zhenshu.system.business.kg.work.educational.mapper.ClassCircleMapper;
import com.zhenshu.system.business.kg.work.educational.service.IClassCircleService;
import com.zhenshu.system.business.ruoyi.domain.bo.PostRoleNameBO;
import com.zhenshu.system.remote.bloc.base.RemoteKindergartenService;
import com.zhenshu.system.remote.kg.base.record.RemoteKindergartenStaffService;
import com.zhenshu.system.remote.ruoyi.RemoteSysPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author zch
 * @version 1.0
 * @date 2022-05-11
 * @desc serviceImpl
 */
@Service
public class ClassCircleServiceImpl extends ServiceImpl<ClassCircleMapper, ClassCircle> implements IClassCircleService {

    @Resource
    private RemoteKindergartenStaffService kindergartenStaffService;
    @Resource
    private RemoteKindergartenService kindergartenService;
    @Resource
    private RemoteSysPostService postService;

    /**
     * 查询班级圈，将图片封装成list，创建时间与当前时间相差多久
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<ClassCircleBO> listQuery(ClassCircleQueryVO queryVO) {
        IPage<ClassCircleBO> page = queryVO.page();
        IPage<ClassCircleBO> pageList = baseMapper.listQuery(page,queryVO);
        List<ClassCircleBO> classCircleList = pageList.getRecords();
        if(CollUtil.isEmpty(classCircleList)){
            return null;
        }
        //获取班级圈uid
        List<Long> uidList = classCircleList.stream().map(ClassCircleBO::getCreateBy).distinct().collect(Collectors.toList());
        //设置班级圈创建者
        getNotKgPeopleCircle(classCircleList);
        //通过用户id和校区id查询用户角色（岗位）
        List<PostRoleNameBO> postName = postService.getPostName(uidList);
        //一个用户岗位可能存在多个 去重
        List<PostRoleNameBO> newList = postName.stream().collect(Collectors
                .collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(PostRoleNameBO::getId))),
                        ArrayList::new));
        //转map uid和岗位
        Map<Long, String> postNameMap = newList.stream().collect(Collectors.toMap(PostRoleNameBO::getId, PostRoleNameBO::getPostName));
        //遍历设置岗位名称和时间
        List<ClassCircleBO> list = classCircleList.stream().sorted(Comparator.comparing(ClassCircleBO::getCreateTime).reversed()).peek(classCircle -> {
            //设置园区员工岗位
            Long id = classCircle.getCreateBy();
            if (postNameMap.containsKey(id)) {
                classCircle.setPostName(postNameMap.get(id));
            }
            //设置图片地址
            if (Objects.equals(classCircle.getUploadType(), CircleUploadType.UPLOAD_PICTURE.getCode()) && Objects.nonNull(classCircle.getAttachUrl())) {
                String[] strArr = classCircle.getAttachUrl().split(",");
                List<String> strings = Arrays.asList(strArr);
                classCircle.setImgUrlList(strings);
                classCircle.setAttachUrl(null);
            }
            //这里的时间需要不到一分钟是展示 xx秒前，不到一小时展示 xx分钟前，不到一天展示 xx小时前。
            String dateString = DateUtils.getDateString(classCircle.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            if (Objects.isNull(dateString)) {
                classCircle.setHowLongCreate(classCircle.getCreateTime() + "");
            } else {
                classCircle.setHowLongCreate(dateString);
            }

        }).collect(Collectors.toList());
        pageList.setRecords(list);
        return pageList;
    }


    /**
     * 获取班级圈校区管理员
     *
     * @param classCircleList 班级圈集合
     * @return 结果
     */
    private void getNotKgPeopleCircle(List<ClassCircleBO> classCircleList){
        //获取班级圈校区id并去重
        List<Long> kgIds = classCircleList.stream().map(ClassCircleBO::getKgId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollUtil.isEmpty(kgIds)) {
            return;
        }
        //获取管理员用户id
        List<Long> uidByKgId = kindergartenService.getUidByKgId(kgIds);
        if (CollUtil.isEmpty(uidByKgId)) {
           return;
        }
        //设置园区管理员岗位
        classCircleList.forEach(circle -> {
            Long id = circle.getCreateBy();
            if (uidByKgId.contains(id)) {
                circle.setPostName("园长");
            }
        });
    }


    /**
     * 保存班级圈
     *
     * @param addVO 入参
     */
    @Override
    public void insertOne(ClassCircleAddVO addVO) {
        SysUser loginUser = SecurityUtils.getUser();
        ClassCircle data = new ClassCircle();
        data.initCreateProp();
        BeanUtils.copyBeanProp(data, addVO);
        data.setKgId(loginUser.getKgId());
        data.setBlocId(loginUser.getBlocId());
        this.save(data);
    }

    /**
     * 删除
     *
     * @param deleteVO 删除入参
     */
    @Override
    public void deleteById(ClassCircleDeleteVO deleteVO) {
        // 1.校验id对应的数据
        this.getAndVerifyClassCircle(deleteVO.getId());
        // 2.逻辑删除
        ClassCircle update = new ClassCircle();
        update.initUpdateProp();
        update.setId(deleteVO.getId());
        BeanUtils.copyBeanProp(deleteVO, update);
        //删除班级圈 -> 是为园长，可以删除所有信息 -> 其他人员删除自己发布的
        KindergartenStaff andVerifyKgStaffByUid = kindergartenStaffService.getAndVerifyKgStaffByUid(SecurityUtils.getUserId());
        if (Objects.isNull(andVerifyKgStaffByUid) || Objects.equals(andVerifyKgStaffByUid.getIdentity().getValue(), UserIdentity.PEOPLE.getValue())) {
            deleteVO.setCreateBy(SecurityUtils.getUserKgId());
        }
        baseMapper.deleteOne(deleteVO);
    }

    /**
     * 获取并校验当前用户是否能查看和操作id对应的数据
     *
     * @param id id
     */
    @Override
    public void getAndVerifyClassCircle(Long id) {
        // 1.根据id查询
        ClassCircle data = super.getById(id);
        // 2.判断是否有数据
        if (data == null) {
            throw new ServiceException(ErrorEnums.UPDATE_FAIL);
        }
        SysUser user = SecurityUtils.getUser();
        //2,当用户登录到系统之后,确定用户身份,用户信息保存,可以在SecurityUtils的user中获取
        Integer code = SecurityUtils.getLoginIdentity().getCode();
        if (Objects.equals(code, LoginIdentity.BLOC_ADMIN.getCode()) || Objects.equals(code, LoginIdentity.KG_ADMIN.getCode())) {
            return;
        }
        // 3.判断当前用户是否能查看或操作id对应的数据
        if (!Objects.equals(data.getKgId(), user.getKgId()) || !Objects.equals(data.getCreateBy(), user.getUserId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
    }

}
