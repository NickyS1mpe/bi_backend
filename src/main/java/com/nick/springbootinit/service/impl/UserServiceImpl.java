package com.nick.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nick.springbootinit.constant.CommonConstant;
import com.nick.springbootinit.constant.UserConstant;
import com.nick.springbootinit.exception.BusinessException;
import com.nick.springbootinit.mapper.UserMapper;
import com.nick.springbootinit.model.dto.user.UserQueryRequest;
import com.nick.springbootinit.model.vo.LoginUserVO;
import com.nick.springbootinit.model.vo.UserVO;
import com.nick.springbootinit.service.UserService;
import com.nick.springbootinit.common.ErrorCode;
import com.nick.springbootinit.model.entity.User;
import com.nick.springbootinit.model.enums.UserRoleEnum;
import com.nick.springbootinit.utils.SqlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * User login service
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * Salt value
     */
    private static final String SALT = "nick";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Param is empty");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Account too short");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Password too short");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Passwords not match");
        }
        synchronized (userAccount.intern()) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "Account exists");
            }
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Database error");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. verify
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Param is empty");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Account error");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Password error");
        }
        // 2. encrypt
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // search user in database
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // not exist
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User not exists or password error");
        }
        // 3. record login status
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    /**
     * get current user
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // is login
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * get current user permit
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // if login
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }

        long userId = currentUser.getId();
        return this.getById(userId);
    }

    /**
     * is administrator
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // admin only
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * logout user
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "Not login");
        }
        // remove login status
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Param is empty");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}
