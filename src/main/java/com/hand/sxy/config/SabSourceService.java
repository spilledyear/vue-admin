//package com.hand.sxy.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.PostConstruct;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class SabSourceService implements FilterInvocationSecurityMetadataSource {
//
//    @Autowired
//    private ResourceMapper resourceMapper;
//
//    private static Map<String, Collection<ConfigAttribute>> resourceMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();
//
//    /*
//     * 一定要加上@PostConstruct注解 在Web服务器启动时，提取系统中的所有权限。
//     */
//    @PostConstruct
//    private void loadResourceDefine() {
//        this.loadResource();
//    }
//
//    public void loadResource() {
//        /*
//         * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
//         */
//        List<Resource> resList = resourceMapper.loadAll();
//        if (!CollectionUtils.isEmpty(resList)) {
//            for (Resource resource : resList) {
//                List<ConfigAttribute> attList = new ArrayList<ConfigAttribute>();
//                if (!CollectionUtils.isEmpty(resource.getRoleList())) {
//                    for (Role role : resource.getRoleList()) {
//                        ConfigAttribute ca = new SecurityConfig(role.getName());
//                        attList.add(ca);
//                    }
//                }
//                resourceMap.put(resource.getUrl(), attList);
//            }
//        }
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return new ArrayList<ConfigAttribute>();
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        FilterInvocation filterInvocation = (FilterInvocation) object;
//        if (resourceMap == null) {
//            loadResourceDefine();
//        }
//        Iterator<String> ite = resourceMap.keySet().iterator();
//        while (ite.hasNext()) {
//            String resURL = ite.next();
//            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
//            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
//                return resourceMap.get(resURL);
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//
//}
