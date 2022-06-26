package ai.openhouse.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ai.openhouse.enums.ActionType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "actions")
@Getter
@Setter
public class Actions extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "time")
    @JsonProperty("time")
    private ZonedDateTime time;

    @Column(name = "type")
    @JsonProperty("type")
    private ActionType type;

    @ManyToOne
    @JoinColumn(name = "applog_id", foreignKey = @ForeignKey(name = "fk_actions_applog"))
    @JsonIgnore
    private ActionProperties properties;
}
