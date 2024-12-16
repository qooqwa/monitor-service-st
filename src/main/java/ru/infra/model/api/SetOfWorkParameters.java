package ru.infra.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Параметры работ для агента")
@AllArgsConstructor
public class SetOfWorkParameters {
    @Schema(description = "Наименование параметра работы", example = "SNAPSHOT")
    @JsonProperty
    private String paramName;
    @Schema(description = "Номер работы указанного вида", example = "180")
    @JsonProperty
    private String paramVal;
}
