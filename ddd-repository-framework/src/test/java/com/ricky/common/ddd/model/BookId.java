package com.ricky.common.ddd.model;

import com.ricky.common.ddd.marker.Identifier;
import lombok.Value;

@Value
public class BookId implements Identifier {

    Long value;

}