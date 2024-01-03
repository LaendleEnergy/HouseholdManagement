package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="incentive")
public class Incentive {
    @Id
    @Column(name = "incentive_id")
    private String id;
    @Column(name = "description")
    private String description;
    @Column(name = "end_date")
    private LocalDate endDate;

    public Incentive() {
        this.id = UUID.randomUUID().toString();
    }

    public Incentive(String description, LocalDate endDate) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.endDate = endDate;
    }

    public Incentive(String id, String description, LocalDate endDate) {
        this.id = id;
        this.description = description;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String textDescription) {
        this.description = textDescription;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incentive incentive = (Incentive) o;
        return Objects.equals(id, incentive.id) && Objects.equals(description, incentive.description) && Objects.equals(endDate, incentive.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, endDate);
    }

    @Override
    public String toString() {
        return "Incentive{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", endDate=" + endDate +
                '}';
    }
}
