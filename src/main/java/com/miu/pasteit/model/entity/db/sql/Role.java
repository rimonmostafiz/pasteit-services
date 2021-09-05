package com.miu.pasteit.model.entity.db.sql;

import com.miu.pasteit.model.entity.common.EntityCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.miu.pasteit.utils.ValidationConstants.ALPHANUMERIC_UNDERSCORE_DOT;
import static com.miu.pasteit.utils.ValidationConstants.ROLE_MAX_SIZE;

/**
 * @author Rimon Mostafiz
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "ROLE")
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class Role extends EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true)
    @NotBlank(message = "{error.role.name.blank}")
    @Size(max = ROLE_MAX_SIZE, message = "{error.role.name.max.size}")
    @Pattern(regexp = ALPHANUMERIC_UNDERSCORE_DOT, message = "{error.role.name.invalid}")
    private String name;
}
