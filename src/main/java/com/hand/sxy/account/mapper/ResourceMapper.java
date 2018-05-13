package com.hand.sxy.account.mapper;

import com.hand.sxy.account.dto.Resource;
import com.hand.sxy.account.dto.Role;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:33
 */
public interface ResourceMapper {
    /**
     * 查询資源
     *
     * @param dto
     * @return
     */
    List<Resource> query(Resource dto);
}
