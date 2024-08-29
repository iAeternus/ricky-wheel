package org.ricky.repositoryframework.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ricky.repositoryframework.persistence.po.BasePO;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/2
 * @className BasePOMapper
 * @desc
 */
@Mapper
public interface BasePOMapper extends IMapper<BasePO> {
}
