package ai.openhouse.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "applog")
@Getter
@Setter
public class AppLog extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    @JsonIgnore
    private Long id;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private String userId;

    @Column(name = "session_id")
    @JsonProperty("sessionId")
    private String sessionId;

    @OneToMany(mappedBy = "logId", cascade = { CascadeType.ALL })
    @JsonProperty("actions")
    private List<Actions> actions;
}
