package com.ricky.test.model;

import lombok.Value;
import org.ricky.repositoryframework.marker.Identifier;

@Value
public class ImageId implements Identifier {

    Long value;

}