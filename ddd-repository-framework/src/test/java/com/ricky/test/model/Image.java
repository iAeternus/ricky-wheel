package com.ricky.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ricky.repositoryframework.marker.Aggregate;

@Data
@AllArgsConstructor
public class Image implements Aggregate<ImageId> {

    private ImageId id;

    private String imageUrl;

}