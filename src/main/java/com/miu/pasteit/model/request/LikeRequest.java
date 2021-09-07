package com.miu.pasteit.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author Abdi Wako Jilo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequest implements Serializable {

    private int id;
    private int like;
}