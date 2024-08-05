package com.ricky.common.ddd.model;

import com.ricky.common.ddd.marker.Identifier;
import lombok.Value;

@Value
public class ImageId implements Identifier {

    Long value;

}