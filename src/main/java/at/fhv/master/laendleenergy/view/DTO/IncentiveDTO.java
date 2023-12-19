package at.fhv.master.laendleenergy.view.DTO;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;

import java.time.LocalDate;

public class IncentiveDTO {
    private String id;
    private String description;
    private String endDate;

    public IncentiveDTO() {}

    public IncentiveDTO(String description, String endDate, String id) {
        this.description = description;
        this.endDate = endDate;
        this.id = id;
    }

    public static Incentive create(IncentiveDTO incentiveDTO) {
        return new Incentive(incentiveDTO.getId(), incentiveDTO.getDescription(), LocalDate.parse(incentiveDTO.getEndDate()));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
