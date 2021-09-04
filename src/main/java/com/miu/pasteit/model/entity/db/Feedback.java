package com.miu.pasteit.model.entity.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

/**
 * @author Abdi Wako Jilo
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document("PASTE")
public class Feedback {

    @Id
    private int id;
    private List<String> comment;
    private int like;

}
