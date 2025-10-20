package town.kairos.market.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import town.kairos.market.enums.MarketStatus;
import town.kairos.market.enums.MarketType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_MARKETS")
public class MarketModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID marketId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MarketType marketType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MarketStatus marketStatus;

    @Column(length = 100)
    private String category;

    @Column(length = 100)
    private String location;

    @Column
    private String imageUrl;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdatedDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "market", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ContextModel> contexts;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "market", fetch = FetchType.LAZY)
    private Set<MarketUserModel> marketsUsers;

    public MarketUserModel convertToMarketUserModel(UUID userID) {
        return new MarketUserModel(null, this, userID);
    }
}
