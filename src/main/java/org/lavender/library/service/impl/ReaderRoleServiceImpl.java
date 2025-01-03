package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lavender.library.entity.ReaderRole;
import org.lavender.library.mapper.ReaderRoleMapper;
import org.lavender.library.service.ReaderRoleService;
import org.springframework.stereotype.Service;

@Service
public class ReaderRoleServiceImpl extends ServiceImpl<ReaderRoleMapper, ReaderRole> implements ReaderRoleService {
}
