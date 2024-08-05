package com.ricky.common.ddd.model;

import com.ricky.common.ddd.marker.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image implements Aggregate<ImageId> {

    private ImageId id;

    private String imageUrl;

}