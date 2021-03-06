package org.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.domain.model.Category;
import org.domain.model.Image;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class ProductWithBadgeDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String model;

    @NonNull
    private Category category;

    @NonNull
    private Image primaryImage;

    @NonNull
    private List<Image> secondaryImages;

    @NonNull
    private List<ManualWithNoteDto> manuals;

    @NonNull
    private Boolean hasBadge;

    @NonNull
    private Integer views;

    @NonNull
    private List<CommentDto> comments;
}
