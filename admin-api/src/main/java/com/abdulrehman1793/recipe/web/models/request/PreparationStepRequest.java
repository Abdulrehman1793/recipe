package com.abdulrehman1793.recipe.web.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreparationStepRequest {
//    private int priority;
//    @NotBlank(message = "Preparation Step title is required")
    private String title;

    private String description;

    private MultipartFile multipartFile;
}
