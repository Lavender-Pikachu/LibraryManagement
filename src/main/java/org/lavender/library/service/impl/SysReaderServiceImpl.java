package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.exception_advice.BusinessException;
import org.lavender.library.dto.ReaderParm;
import org.lavender.library.entity.SysReader;
import org.lavender.library.mapper.SysReaderMapper;
import org.lavender.library.service.SysReaderService;
import org.lavender.library.entity.ReaderRole;
import org.lavender.library.service.ReaderRoleService;
import org.lavender.library.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SysReaderServiceImpl extends ServiceImpl<SysReaderMapper, SysReader> implements SysReaderService {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private ReaderRoleService readerRoleService;

    @Override
    public IPage<SysReader> getList(ReaderParm parm) {
        //构造查询条件
        LambdaQueryWrapper<SysReader> queryWrapper = new LambdaQueryWrapper<>();
        addLikeCondition(queryWrapper, SysReader::getIdCard, parm.getIdCard());
        addLikeCondition(queryWrapper, SysReader::getLearnNum, parm.getLearnNum());
        addLikeCondition(queryWrapper, SysReader::getPhone, parm.getPhone());
        addLikeCondition(queryWrapper, SysReader::getUsername, parm.getUsername());
        //构造分页对象
        IPage<SysReader> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void saveReader(SysReader sysReader) {
        LambdaQueryWrapper<SysReader.SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysReader.SysRole::getRoleType, "2");
        SysReader.SysRole one = sysRoleService.getOne(queryWrapper);
        if (one == null) {
            throw new BusinessException(500, "请先新建读者角色，再建读者!");
        }
        //新增读者
        this.baseMapper.insert(sysReader);
        //设置读者角色
        ReaderRole readerRole = new ReaderRole();
        readerRole.setReaderId(sysReader.getReaderId());
        readerRole.setRoleId(one.getRoleId());
        readerRoleService.save(readerRole);
    }

    @Override
    @Transactional
    public void editReader(SysReader sysReader) {
         //编辑读者
        this.baseMapper.updateById(sysReader);

        LambdaQueryWrapper<SysReader.SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysReader.SysRole::getRoleType, "2");
        SysReader.SysRole one = sysRoleService.getOne(queryWrapper);

        //设置读者，先删除，再设置
        LambdaQueryWrapper<ReaderRole> readerRole = new LambdaQueryWrapper<>();
        readerRole.eq(ReaderRole::getReaderId, sysReader.getReaderId());
        //先删除
        readerRoleService.remove(readerRole);
        //设置读者角色
        ReaderRole saveReaderRole = new ReaderRole();
        saveReaderRole.setReaderId(sysReader.getReaderId());
        saveReaderRole.setRoleId(one.getRoleId());
        readerRoleService.save(saveReaderRole);
    }

    @Override
    public SysReader loadByUsername(String username) {
        LambdaQueryWrapper<SysReader> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysReader::getUsername, username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    private void addLikeCondition(LambdaQueryWrapper<SysReader> queryWrapper,
                                  SFunction<SysReader, ?> column,
                                  String value){
        if(StringUtils.isNotBlank(value)){
            queryWrapper.like(column, value);
        }
    }
}
