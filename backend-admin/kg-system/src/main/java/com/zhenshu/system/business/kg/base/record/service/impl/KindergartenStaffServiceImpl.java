package com.zhenshu.system.business.kg.base.record.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.constant.ErrorEnums;
import com.zhenshu.common.core.domain.entity.SysUser;
import com.zhenshu.common.enums.base.*;
import com.zhenshu.common.enums.kg.base.record.KgStaffQueryType;
import com.zhenshu.common.enums.kg.work.backlog.VacateType;
import com.zhenshu.common.enums.system.UserSex;
import com.zhenshu.common.exception.ServiceException;
import com.zhenshu.common.utils.DateUtils;
import com.zhenshu.common.utils.SecurityUtils;
import com.zhenshu.common.utils.bean.BeanUtils;
import com.zhenshu.system.business.bloc.base.domain.bo.BlocStaffDetailsBO;
import com.zhenshu.system.business.bloc.base.domain.po.BlocStaffKindergarten;
import com.zhenshu.system.business.bloc.base.domain.po.Kindergarten;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffKindergartenService;
import com.zhenshu.system.business.bloc.base.service.IBlocStaffService;
import com.zhenshu.system.business.bloc.base.service.IKindergartenService;
import com.zhenshu.system.business.kg.base.advanced.domain.bo.TeacherBO;
import com.zhenshu.system.business.kg.base.advanced.domain.vo.TeacherQueryVO;
import com.zhenshu.system.business.kg.base.advanced.service.IClassroomService;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffDetailsBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenStaffSimpleBO;
import com.zhenshu.system.business.kg.base.record.domain.bo.KindergartenTeacherBO;
import com.zhenshu.system.business.kg.base.record.domain.dto.KgStaffExcelDTO;
import com.zhenshu.system.business.kg.base.record.domain.po.KindergartenStaff;
import com.zhenshu.system.business.kg.base.record.domain.po.Student;
import com.zhenshu.system.business.kg.base.record.domain.vo.*;
import com.zhenshu.system.business.kg.base.record.mapper.KindergartenStaffMapper;
import com.zhenshu.system.business.kg.base.record.service.IKindergartenStaffService;
import com.zhenshu.system.business.kg.work.backlog.domain.po.StudentVacateApply;
import com.zhenshu.system.business.ruoyi.domain.SysUserRole;
import com.zhenshu.system.business.ruoyi.domain.bo.RoleBO;
import com.zhenshu.system.business.ruoyi.domain.vo.UserIdVO;
import com.zhenshu.system.cache.lock.LockManages;
import com.zhenshu.system.remote.kg.base.record.RemoteKindergartenStaffService;
import com.zhenshu.system.remote.ruoyi.RemoteCommonService;
import com.zhenshu.system.remote.ruoyi.RemoteSysPostService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserPostService;
import com.zhenshu.system.remote.ruoyi.RemoteSysUserService;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/27 18:58
 * @desc serviceImpl
 */
@Service
public class KindergartenStaffServiceImpl extends ServiceImpl<KindergartenStaffMapper, KindergartenStaff> implements IKindergartenStaffService {
    @Resource
    private RemoteSysUserService remoteSysUserService;
    @Resource
    private RemoteSysUserPostService remoteSysUserPostService;
    @Resource
    private RemoteCommonService commonService;
    @Resource
    private RemoteSysPostService remoteSysPostService;
    @Resource
    private IKindergartenService kindergartenService;
    @Resource
    private IBlocStaffKindergartenService blocStaffKindergartenService;
    @Resource
    private IBlocStaffService blocStaffService;
    @Resource
    private IClassroomService classroomService;

    /**
     * 列表查询
     *
     * @param queryVO 列表查询入参
     * @return 结果
     */
    @Override
    public IPage<KindergartenStaffBO> listPage(KindergartenStaffQueryVO queryVO) {
        IPage<KindergartenStaffBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        List<KindergartenStaffBO> list;
        if (queryVO.getQueryType() != KgStaffQueryType.BLOC) {
            list = baseMapper.detailsListPage(page, queryVO);
        } else {
            list = baseMapper.detailsBlocStaffListPage(page, queryVO);
        }
        page.setRecords(list);
        return page;
    }

    /**
     * 根据Id修改
     *
     * @param editVO 修改入参
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateById(KindergartenStaffEditVO editVO) {
        // 1.检验岗位Id是否合法, 是否越权
        commonService.verifyPost(RoleType.KG, editVO.getPostIds());
        // 2.获取并校验校区员工信息
        KindergartenStaff byId = this.verifyLoginUserHandleByKgStaffId(editVO.getId());
        if (!Objects.equals(byId.getStaffNumber(), editVO.getStaffNumber())) {
            // 2.校验员工编号是否重复
            if (this.getStaffNumberExist(editVO.getStaffNumber())) {
                throw new ServiceException(ErrorEnums.STAFF_NUMBER_EXIST);
            }
        }
        // 3.判断当前手机号是否已存在用户
        if (!byId.getPhone().equals(editVO.getPhone())) {
            SysUser user = remoteSysUserService.selectByPhone(editVO.getPhone());
            if (user != null) {
                throw new ServiceException(ErrorEnums.PHONE_EXISTS);
            }
        }
        // 4.修改校区员工的信息
        KindergartenStaff staff = new KindergartenStaff();
        staff.initUpdateProp();
        staff.setSex(UserSex.values()[editVO.getSex()]);
        BeanUtils.copyBeanProp(staff, editVO);
        super.update(staff,
                new UpdateWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getId, editVO.getId())
        );
        // 5.修改登录用户信息;
        SysUser update = new SysUser();
        update.setUserId(byId.getUid());
        update.setUserName(editVO.getPhone());
        update.setNickName(editVO.getName());
        update.setAvatar(editVO.getAvatar());
        update.setPhonenumber(editVO.getPhone());
        if (editVO.getSex() != null) {
            update.setSex(UserSex.values()[editVO.getSex()]);
        }
        remoteSysUserService.updateById(update);
        // 6.删除校区员工与校区岗位关联关系
        remoteSysUserPostService.deleteUserPostByUserId(byId.getUid(), UserRoleType.KG_PEOPLE);
        // 7.重新添加校区员工与校区岗位的关联关系
        commonService.addUserPost(byId.getUid(), editVO.getPostIds(), UserRoleType.KG_PEOPLE);
    }

    /**
     * 添加
     *
     * @param addVO 添加入参
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void insert(KindergartenStaffAddVO addVO) {
        // 1.校验岗位和手机号
        commonService.verifyPostAndPhone(RoleType.KG, addVO.getPostIds(), addVO.getPhone());
        // 2.校验员工编号是否重复
        if (this.getStaffNumberExist(addVO.getStaffNumber())) {
            throw new ServiceException(ErrorEnums.STAFF_NUMBER_EXIST);
        }
        // 2.创建登录用户信息
        SysUser sysUser = SysUser.initSysUser();
        sysUser.setUserName(addVO.getPhone());
        sysUser.setNickName(addVO.getName());
        sysUser.setPhonenumber(addVO.getPhone());
        sysUser.setAvatar(addVO.getAvatar());
        if (addVO.getSex() != null) {
            sysUser.setSex(UserSex.values()[addVO.getSex()]);
        }
        remoteSysUserService.insertUser(sysUser);
        // 3.创建校区员工信息
        SysUser login = SecurityUtils.getUser();
        KindergartenStaff kgStaff = new KindergartenStaff();
        kgStaff.initCreateProp();
        BeanUtils.copyBeanProp(kgStaff, addVO);
        kgStaff.setUid(sysUser.getUserId());
        kgStaff.setIdentity(UserIdentity.PEOPLE);
        kgStaff.setBlocId(login.getBlocId());
        kgStaff.setIsQuit(Constants.FALSE);
        kgStaff.setKgId(login.getKgId());
        kgStaff.setSex(sysUser.getSex());
        this.save(kgStaff);
        // 4.将刚才创建的登录用户与校区员工关联起来
        SysUser update = new SysUser();
        update.setUserId(sysUser.getUserId());
        update.setAssociationType(AssociationType.KG);
        update.setAssociationId(kgStaff.getId());
        remoteSysUserService.updateById(update);
        // 6.关联校区员工与岗位的关系
        commonService.addUserPost(sysUser.getUserId(), addVO.getPostIds(), UserRoleType.KG_PEOPLE);
        // 7.自增学校员工数量
        kindergartenService.incrStaffCount(login.getKgId());
    }

    /**
     * 学校员工离职
     *
     * @param quitVO 离职入参
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void quit(KindergartenStaffQuitVO quitVO) {
        // 1.获取员工并校验当前登录用户能否操作这个员工
        KindergartenStaff staff = this.verifyLoginUserHandleByKgStaffId(quitVO.getId());
        // 2.校验员工是否绑定了班级
        int count = classroomService.getStaffBindClassroomCount(staff.getId());
        if (count > Constants.ZERO) {
            throw new ServiceException(ErrorEnums.KG_STAFF_BING_CLASS_ROOM);
        }
        // 3.将员工状态修改为离职
        KindergartenStaff update = new KindergartenStaff();
        update.setIsQuit(Constants.TRUE);
        update.setQuitDate(quitVO.getQuitDate());
        update.setQuitReason(quitVO.getQuitReason());
        update.initUpdateProp();
        update.setId(staff.getId());
        super.updateById(update);
        // 4.自减学校员工数量
        kindergartenService.decrStaffCount(staff.getKgId());
        // 5.删除校区员工的校区岗位信息
        remoteSysUserPostService.deleteUserPostByUserId(staff.getUid(), UserRoleType.KG_PEOPLE);
        // 6.禁用校区员工对应的登录用户
        remoteSysUserService.disableById(staff.getUid());
    }

    /**
     * 根据Id查询
     *
     * @param id id
     * @return 结果
     */
    @Override
    public KindergartenStaffDetailsBO getDetailsById(Long id) {
        // 1.获取校区员工的详情
        KindergartenStaffDetailsBO detailsBO = baseMapper.getDetailsById(id);
        if (detailsBO == null) {
            return null;
        }
        // 2.判断数据是否越权
        if (!detailsBO.getKgId().equals(SecurityUtils.getUserKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.获取校区员工关联的校区岗位Id
        List<SysUserRole> sysUserPosts = remoteSysUserPostService.selectUserJoinPost(detailsBO.getUid(), UserRoleType.KG_PEOPLE);
        detailsBO.setPostIds(sysUserPosts.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        return detailsBO;
    }

    /**
     * 根据校区ID获取集团管理员
     *
     * @param kgId 校区ID
     * @return 集团管理员信息
     */
    @Override
    public KindergartenStaff selectAdminByBlocId(Long kgId) {
        return super.getOne(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getKgId, kgId)
                        .eq(KindergartenStaff::getIdentity, UserIdentity.ADMIN)
        );
    }

    /**
     * 查询指定校区下的在职员工数量, 除了管理员
     *
     * @param kgId 校区Id
     * @return 指定校区下的在职员工数量
     */
    @Override
    public int selectLiveStaffCountByKgId(Long kgId) {
        return super.count(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getKgId, kgId)
                        .eq(KindergartenStaff::getIsQuit, Constants.FALSE)
                        .eq(KindergartenStaff::getIdentity, UserIdentity.PEOPLE)
        );
    }

    /**
     * 删除指定校区下的管理员信息
     *
     * @param kgId 校区Id
     * @return 结果
     */
    @Override
    public boolean deleteAdminByKgId(Long kgId) {
        // 1.查询校区管理员
        KindergartenStaff admin = super.getOne(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getKgId, kgId)
                        .eq(KindergartenStaff::getIdentity, UserIdentity.ADMIN)
        );
        if (admin == null) {
            throw new ServiceException(ErrorEnums.KG_ADMIN_NOT_EXIST);
        }
        // 2.逻辑删除校区管理员
        KindergartenStaff update = new KindergartenStaff();
        update.initUpdateProp();
        return super.update(update,
                new UpdateWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getId, admin.getId())
                        .set(KindergartenStaff::getDelFlag, Constants.TRUE)
        );
    }

    /**
     * 删除学校员工
     *
     * @param idVO 校区员工id入参
     */
    @Override
    public void deleteById(KindergartenStaffIdVO idVO) {
        // 1.获取员工并校验当前登录用户能否操作这个员工
        KindergartenStaff staff = this.verifyLoginUserHandleByKgStaffId(idVO.getId());
        // 2.员工未离职不可删除
        if (!staff.getIsQuit()) {
            throw new ServiceException(ErrorEnums.KG_STAFF_NOT_QUIT_UNDELETABLE);
        }
        // 3.逻辑删除员工
        KindergartenStaff update = new KindergartenStaff();
        update.initUpdateProp();
        update.setId(staff.getId());
        super.update(update,
                new UpdateWrapper<KindergartenStaff>().lambda()
                        .set(KindergartenStaff::getDelFlag, Constants.TRUE)
                        .eq(KindergartenStaff::getId, idVO.getId())
        );
        // 4.逻辑删除登录用户信息
        remoteSysUserService.deleteById(staff.getUid());
    }

    /**
     * 校区员工重新入职
     *
     * @param idVO 校区员工id入参
     */
    @Override
    public void entry(KindergartenStaffIdVO idVO) {
        // 1.获取员工并校验当前登录用户能否操作这个员工
        KindergartenStaff staff = this.verifyLoginUserHandleByKgStaffId(idVO.getId());
        // 2.将员工状态修改为在职
        KindergartenStaff update = new KindergartenStaff();
        update.setIsQuit(Constants.FALSE);
        update.initUpdateProp();
        update.setId(staff.getId());
        super.updateById(update);
        // 3.自增学校员工数量
        kindergartenService.incrStaffCount(staff.getKgId());
        // 4.启用集团员工对应的登录用户
        remoteSysUserService.enableById(staff.getUid());
    }

    /**
     * 进入校区的集团人员分配岗位
     *
     * @param postVO 入参
     */
    @Override
    public void post(BlocStaffPostVO postVO) {
        // 1.校验登录用户能否操作进入校区的集团人员
        SysUser login = SecurityUtils.getUser();
        BlocStaffKindergarten byId = blocStaffKindergartenService.getById(postVO.getId());
        if (byId == null) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        } else if (!Objects.equals(byId.getKindergartenId(), login.getKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 2.校验岗位是否合法
        boolean result = remoteSysPostService.verifyPost(RoleType.KG, postVO.getPostIds());
        if (!result) {
            throw new ServiceException(ErrorEnums.POST_ILLEGALITY);
        }
        // 3.删除旧岗位
        remoteSysUserPostService.deleteUserPostByUserId(postVO.getId(), UserRoleType.BLOC_PEOPLE_INTO_KG);
        // 4.分配新岗位
        ArrayList<SysUserRole> list = new ArrayList<>();
        for (Long postId : postVO.getPostIds()) {
            SysUserRole data = new SysUserRole();
            data.setUserId(postVO.getId());
            data.setRoleId(postId);
            data.setUserType(UserRoleType.BLOC_PEOPLE_INTO_KG);
            list.add(data);
        }
        remoteSysUserPostService.batchUserPost(list);
    }

    /**
     * 获取导出数据
     *
     * @param exportVO 导出入参
     * @return
     */
    @Override
    public List<LinkedHashMap<String, Object>> export(KindergartenStaffExportVO exportVO) {
        return baseMapper.selectExportList(exportVO);
    }

    /**
     * 根据Id查询可进入校区的集团员工
     *
     * @param id id
     * @return 结果
     */
    @Override
    public KindergartenStaffDetailsBO getBlocStaffById(Long id) {
        // 1.根据id查询
        BlocStaffKindergarten byId = blocStaffKindergartenService.getById(id);
        // 2.判断是否为空
        if (byId == null) {
            throw new ServiceException(ErrorEnums.ID_NOT_FOUND);
        }
        // 3.判断集团id是否一致
        SysUser login = SecurityUtils.getUser();
        if (!Objects.equals(byId.getBlocId(), login.getBlocId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 4.获取集团员工的详情 TODO 使用集团远程方法
        BlocStaffDetailsBO detailsBO = blocStaffService.getDetailsById(byId.getBlocStaffId(), Constants.FALSE);
        if (detailsBO == null) {
            return null;
        }
        // 5.判断数据是否越权
        if (!detailsBO.getBlocId().equals(SecurityUtils.getUserBlocId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 6.获取集团员工在校区的权限
        List<SysUserRole> sysUserRoles = remoteSysUserPostService.selectUserJoinPost(id, UserRoleType.BLOC_PEOPLE_INTO_KG);
        detailsBO.setPostIds(sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        // 7.转成KindergartenStaffDetailsBO
        KindergartenStaffDetailsBO bo = new KindergartenStaffDetailsBO();
        BeanUtils.copyBeanProp(bo, detailsBO);
        return bo;
    }

    /**
     * 批量添加校区员工
     *
     * @param list 数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<KgStaffExcelDTO> list) {
        // 1.遍历数据, 拿出所有的岗位名称; 并判断手机号码是否有重复
        Set<String> postSet = new HashSet<>();
        Set<String> phoneSet = new HashSet<>();
        for (KgStaffExcelDTO dto : list) {
            postSet.addAll(Arrays.stream(dto.getPost().split(",")).collect(Collectors.toList()));
            if (phoneSet.contains(dto.getPhone())) {
                throw new ServiceException(ErrorEnums.IMPORT_PHONE_REPEAT);
            }
            phoneSet.add(dto.getPhone());
        }
        // 2.查询岗位名称对应的数据
        List<RoleBO> posts = remoteSysPostService.selectByPostNames(postSet);
        // 3.判断是否有岗位
        if (CollectionUtils.isEmpty(posts)) {
            throw new ServiceException(ErrorEnums.POST_NOT_EXIST);
        }
        // 4.判断excel中填写的所有岗位, 是否都能在数据库中查到
        Map<String, RoleBO> map = new HashMap<>(posts.size());
        for (RoleBO post : posts) {
            if (!map.containsKey(post.getRoleName())) {
                map.put(post.getRoleName(), post);
            }
        }
        if (map.size() != postSet.size()) {
            throw new ServiceException(ErrorEnums.POST_NOT_EXIST);
        }
        // 5.将dto对象转成addVO对象
        List<KindergartenStaffAddVO> addVOList = new ArrayList<>();
        for (KgStaffExcelDTO dto : list) {
            KindergartenStaffAddVO addVO = new KindergartenStaffAddVO();
            BeanUtils.copyBeanProp(addVO, dto);
            String[] postNames = dto.getPost().split(",");
            addVO.setPostIds(new ArrayList<>());
            for (String postName : postNames) {
                addVO.getPostIds().add(map.get(postName).getRoleId());
            }
            addVOList.add(addVO);
        }
        // 6.保存数据
        for (KindergartenStaffAddVO addVO : addVOList) {
            this.insert(addVO);
        }
    }

    /**
     * 重置用户密码
     *
     * @param userIdVO 入参
     * @return 新密码
     */
    @Override
    public String resetPassword(UserIdVO userIdVO) {
        SysUser loginUser = SecurityUtils.getUser();
        // 1.查询登录用户对应的校区员工信息
        KindergartenStaff staff = super.getOne(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getUid, userIdVO.getUserId())
        );
        // 2.判断校区是否一致
        SysUser login = SecurityUtils.getUser();
        if (!Objects.equals(login.getKgId(), staff.getKgId())) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        // 3.修改用户密码
        SysUser update = new SysUser();
        update.setUserId(userIdVO.getUserId());
        String pwd = RandomUtil.randomString(Constants.PASSWORD_MIN_LENGTH);
        update.setPassword(SecurityUtils.encryptPassword(pwd));
        update.setUpdateBy(loginUser.getUserId().toString());
        update.setUpdateTime(DateUtils.getNowDate());
        remoteSysUserService.updateById(update);
        return pwd;
    }

    /**
     * 查询并校验当前用户是否能查看校区员工
     *
     * @param staffId 员工id
     * @return 结果
     */
    @Override
    public KindergartenStaff getAndVerifyKgStaff(Long staffId) {
        // 1.查询校区员工
        KindergartenStaff staff = super.getById(staffId);
        // 2.判断校区员工是否存在
        if (staff == null) {
            throw new ServiceException(ErrorEnums.KG_STAFF_NOT_EXIST);
        }
        // 3.判断校区Id是否一致
        Long kgId = SecurityUtils.getUserKgId();
        if (!Objects.equals(staff.getKgId(), kgId)) {
            throw new ServiceException(ErrorEnums.IDENTITY_ILLEGAL);
        }
        return staff;
    }

    /**
     * 校验校区员工是否都属于kgId; 并且都未离职
     *
     * @param staffIds 校区员工Id集合
     * @param kgId     校区Id
     * @return 结果
     */
    @Override
    public Boolean verifyKgStaffByIds(Collection<Long> staffIds, Long kgId) {
        int count = super.count(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .in(KindergartenStaff::getId, staffIds)
                        .eq(KindergartenStaff::getKgId, kgId)
                        .eq(KindergartenStaff::getIsQuit, Constants.FALSE)
        );
        return count == staffIds.size();
    }

    /**
     * 根据编号和校区id获取未离职的非管理员校区员工
     *
     * @param staffNumbers 校区员工编号集合
     * @param kgId         校区Id
     * @return 结果
     */
    @Override
    public List<KindergartenStaff> getKgStaffByNumbers(Collection<String> staffNumbers, Long kgId) {
        return super.list(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .in(KindergartenStaff::getStaffNumber, staffNumbers)
                        .eq(KindergartenStaff::getKgId, kgId)
                        .eq(KindergartenStaff::getIsQuit, Constants.FALSE)
        );
    }

    /**
     * 查询可绑定班级的老师
     *
     * @param queryVO 入参
     * @return 结果
     */
    @Override
    public IPage<TeacherBO> selectedTeacherListPage(TeacherQueryVO queryVO) {
        IPage<TeacherBO> page = new Page<>(queryVO.getPageNum(), queryVO.getPageSize());
        return baseMapper.selectedTeacherListPage(page, queryVO);
    }

    /**
     * 查询所有在职非管理员的员工
     *
     * @return 结果
     */
    @Override
    public List<KindergartenStaffSimpleBO> listAll() {
        List<KindergartenStaff> list = super.list(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .select(KindergartenStaff::getId, KindergartenStaff::getName)
                        .eq(KindergartenStaff::getKgId, SecurityUtils.getUserKgId())
                        .eq(KindergartenStaff::getIsQuit, Constants.FALSE)
                        .ne(KindergartenStaff::getIdentity, UserIdentity.PEOPLE)
                        .orderByDesc(KindergartenStaff::getCreateTime)
        );
        return list.stream().map(item -> {
            KindergartenStaffSimpleBO bo = new KindergartenStaffSimpleBO();
            bo.setId(item.getId());
            bo.setName(item.getName());
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 校验登录用户id集合是否都指定校区下的员工
     *
     * @param uids 登录用户id集合
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public Boolean verifyKgStaffByUids(List<Long> uids, Long kgId) {
        int count = super.count(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .in(KindergartenStaff::getUid, uids)
                        .eq(KindergartenStaff::getKgId, kgId)
        );
        return count == uids.size();
    }

    /**
     * 根据用户登录id获取并校验
     *
     * @param uid 用户登录id
     * @return 结果
     */
    @Override
    public KindergartenStaff getAndVerifyKgStaffByUid(Long uid) {
        // 1.查询校区员工
        KindergartenStaff staff = super.getOne(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getUid, uid)
        );
        // 2.判断校区员工是否存在
        if (staff == null) {
            throw new ServiceException(ErrorEnums.KG_STAFF_NOT_EXIST);
        }
        // 3.判断校区Id是否一致
        Long kgId = SecurityUtils.getUserKgId();
        if (!Objects.equals(staff.getKgId(), kgId)) {
            throw new ServiceException(ErrorEnums.KG_STAFF_NOT_EXIST);
        }
        return staff;
    }

    /**
     * 判断员工是否为老师
     *
     * @param user 登录用户对象
     * @return 结果
     */
    @Override
    public boolean isTeacher(SysUser user) {
        LoginIdentity identity = user.getIdentity();
        if (LoginIdentity.KG_ADMIN == identity) {
            // 校区管理员总是返回false
            return false;
        } else if (LoginIdentity.KG_PEOPLE == identity) {
            return classroomService.getStaffBindClassroomCount(user.getAssociationId()) > Constants.ZERO;
        } else if (LoginIdentity.BLOC_TO_KG == identity) {
            // TODO 班级绑定的是校区员工id, 目前集团人员是无法绑定班级的, 所以这里的代码可以注释
            // 集团人员进入校区需要查询BlocStaffKindergarten表
//            BlocStaffKindergarten data = blocStaffKindergartenService.getByBlocStaffIdAndKgId(user.getAssociationId(), user.getKgId());
//            return classroomService.getStaffBindClassroomCount(data.getId()) > Constants.ZERO;
        }
        return false;
    }

    /**
     * 通过当前用户校区id查询教师角色的校区员工id
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public List<KindergartenTeacherBO> getTeacherIds(Long kgId) {
        return baseMapper.getTeacherIds(kgId);
    }

    /**
     * 获取校区下所有在职员工
     *
     * @param kgId 校区id
     * @return 结果
     */
    @Override
    public List<KindergartenStaff> getLiveStaffAllByKgId(Long kgId) {
        return super.list(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getKgId, kgId)
                        .eq(KindergartenStaff::getIsQuit, Constants.FALSE)
        );
    }

    /**
     * 校验登录用户能否操作指定的校区员工
     *
     * @param kgStaffId 被操作的校区员工Id
     */
    public KindergartenStaff verifyLoginUserHandleByKgStaffId(Long kgStaffId) {
        // 1.查询并校验校区员工
        KindergartenStaff staff = this.getAndVerifyKgStaff(kgStaffId);
        // 2.操作的是校区管理员抛出异常
        if (staff.getIdentity().equals(UserIdentity.ADMIN)) {
            throw new ServiceException(ErrorEnums.DELETE_FAIL);
        }
        return staff;
    }

    /**
     * 获取员工编号是否存在
     *
     * @param staffNumber 员工编号
     * @return 结果
     */
    public boolean getStaffNumberExist(String staffNumber) {
        int count = super.count(
                new QueryWrapper<KindergartenStaff>().lambda()
                        .eq(KindergartenStaff::getStaffNumber, staffNumber)
                        .eq(KindergartenStaff::getKgId, SecurityUtils.getUserKgId())
        );
        return count > Constants.ZERO;
    }
}
