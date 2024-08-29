package com.ricky.test.model;

import lombok.Value;
import org.ricky.repositoryframework.marker.Identifier;


@Value
public class BookId implements Identifier {

    Long value;

}