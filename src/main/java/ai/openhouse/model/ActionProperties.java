package ai.openhouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "actionproperties")
@Getter
@Setter
public class ActionProperties extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "location_x")
    @JsonProperty("locationX")
    private double locationX;

    @Column(name = "location_y")
    @JsonProperty("locationY")
    private double locationY;

    @Column(name = "viewer_id")
    @JsonProperty("viewedId")
    private String viewedId;

    @Column(name = "page_from")
    @JsonProperty("pageFrom")
    private String pageFrom;

    @Column(name = "page_to")
    @JsonProperty("pageTo")
    private String pageTo;
}
