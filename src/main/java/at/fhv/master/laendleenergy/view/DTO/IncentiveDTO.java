package at.fhv.master.laendleenergy.view.DTO;

import at.fhv.master.laendleenergy.domain.Incentive;
import java.time.LocalDate;

public class IncentiveDTO {
    private String id;
    private String description;
    private String endDate;

    public IncentiveDTO() {}

    public IncentiveDTO(String id, String description, String endDate) {
        this.description = description;
        this.endDate = endDate;
        this.id = id;
    }

    public Incentive toIncentive() {
        return new Incentive(this.getId(), this.getDescription(), LocalDate.parse(this.getEndDate()));
    }

    public static IncentiveDTO create(Incentive incentive) {
        return new IncentiveDTO(incentive.getId(),
                incentive.getDescription(),
                incentive.getEndDate() != null ? incentive.getEndDate().toString() : "");
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
