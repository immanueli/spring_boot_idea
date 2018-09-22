package com.imooc.miaosha.config;

import com.imooc.miaosha.domain.MiaoShaUser;
import com.imooc.miaosha.service.MiaoShaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class UserArgumentResolvers implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    /**
     * 判断参数类型
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        if (clazz == MiaoShaUser.class) return true;
        return false;
    }

    /**
     * 注入user对象
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(MiaoShaUserService.COOKIE_NAME_TKOEN);
        String cookieToken = getCookieValue(request,MiaoShaUserService.COOKIE_NAME_TKOEN);

        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return miaoShaUserService.getByToken(token,response);
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameTkoen) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieNameTkoen)) return cookie.getValue();
        }
        return null;
    }
}
