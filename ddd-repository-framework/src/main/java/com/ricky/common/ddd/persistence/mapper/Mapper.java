package com.ricky.common.ddd.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ricky.common.ddd.persistence.po.BasePO;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/31
 * @className Mapper
 * @desc
 */
@org.apache.ibatis.annotations.Mapper
public interface Mapper<PO extends BasePO> extends BaseMapper<PO> {
}
