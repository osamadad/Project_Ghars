package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class RecommendedEventDTOOut {

    private Integer eventId;
    private String title;
    private String description;
    private String location;
    private String date;
    private String startTime;
    private String endTime;

    private String reason;
    private String whatToPrepare;
}
