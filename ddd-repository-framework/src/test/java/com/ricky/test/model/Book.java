package com.ricky.test.model;

import lombok.Data;
import org.ricky.repositoryframework.marker.Aggregate;

import java.util.List;

@Data
public class Book implements Aggregate<BookId> {

    private BookId id;

    private String bookName;

    private String bookDesc;

    private Long words;

    private List<Image> images;

    public void addImage(ImageId imageId, String imageUrl) {
        this.images.add(new Image(imageId, imageUrl));
    }

}





