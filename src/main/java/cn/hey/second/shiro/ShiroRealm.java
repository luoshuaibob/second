package cn.hey.second.shiro;

import cn.hey.second.entity.User;
import cn.hey.second.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * 自定义 Shiro 的 Realm 对象
 *
 * @author Long
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private UserService userService;

    /**
     * 授权
     * 由于项目的功能较少，对权限的操作需求不大，因此暂时忽略权限操作
     *
     * @param principalCollection PrincipalCollection
     * @return null
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行了 ===>  Shiro用户授权  doGetAuthorizationInfo");
        /* 可获取下面返回的参数中的principal */
        Object principal = principalCollection.getPrimaryPrincipal();
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken 认证令牌
     * @return null
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行了 ===>  Shiro用户认证  doGetAuthenticationInfo");
        // 获取token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 检索用户
        User user = userService.getUserById(token.getUsername());
        // 返回的user为空即没有该用户
        if (user == null) {
            return null;
        }
        // 验证密码是否正确
        // 密码认证由 shiro 做
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), "");
    }
}
