package com.miu.pasteit.model.entity.common;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Rimon Mostafiz
 */
@Data
@MappedSuperclass
public abstract class NoSqlEntityCommon implements Serializable {
    protected String createdBy;

    protected String editedBy;

    protected LocalDateTime createTime;

    protected LocalDateTime editTime;

    @Version
    protected Long version;

    public abstract String getId();

    public abstract void setId(String id);

    @PrePersist
    public void beforeSave() {
        if (this.createTime == null) {
            this.createTime = LocalDateTime.now(ZoneId.of("BST", ZoneId.SHORT_IDS));
        }
    }

    @PreUpdate
    public void beforeEdit() {
        this.editTime = LocalDateTime.now(ZoneId.of("BST", ZoneId.SHORT_IDS));
    }

    @PreRemove
    public void beforeDelete() {
        this.editTime = LocalDateTime.now(ZoneId.of("BST", ZoneId.SHORT_IDS));
    }
}
