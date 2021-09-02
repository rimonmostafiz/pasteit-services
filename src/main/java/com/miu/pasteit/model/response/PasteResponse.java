package com.miu.pasteit.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miu.pasteit.model.dto.PasteModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Rimon Mostafiz
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasteResponse {
    private List<PasteModel> pastes;

    private PasteModel paste;

    public static PasteResponse of(List<PasteModel> pastes) {
        PasteResponse response = new PasteResponse();
        response.setPastes(pastes);
        return response;
    }

    public static PasteResponse of(PasteModel paste) {
        PasteResponse response = new PasteResponse();
        response.setPaste(paste);
        return response;
    }
}
