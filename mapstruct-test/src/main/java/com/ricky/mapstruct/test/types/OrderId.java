package com.ricky.mapstruct.test.types;

import com.ricky.mapstruct.test.marker.Identifier;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OrderId
 * @desc
 */
@Value
@AllArgsConstructor
public class OrderId implements Identifier {

    Long value;

}
