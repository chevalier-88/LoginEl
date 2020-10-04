package com.gmail.vladimir.chevalier.web.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.vladimir.chevalier.web.entities.View;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonView(View.Basic.class)
public class WsEventDto {

    private ObjectType objectType;
    private EventType eventType;
    @JsonRawValue
    private String body;

}
