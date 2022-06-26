package ai.openhouse.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @OneToMany(mappedBy = "logId", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JsonProperty("actions")
    private List<Actions> actions;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n[\n");
        sb.append("  id=").append(this.id).append("\n");
        sb.append("  userId=").append(this.userId).append("\n");
        sb.append("  sessionId=").append(this.sessionId).append("\n");

        this.actions.stream().forEach(a -> sb.append(a.toString()));

        sb.append("]");

        return sb.toString();
    }
}
