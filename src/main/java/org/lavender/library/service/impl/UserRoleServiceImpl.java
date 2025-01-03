package org.lavender.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lavender.library.entity.UserRole;
import org.lavender.library.mapper.UserRoleMapper;
import org.lavender.library.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
