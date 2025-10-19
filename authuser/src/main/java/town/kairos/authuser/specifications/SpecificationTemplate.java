package town.kairos.authuser.specifications;

import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import town.kairos.authuser.models.UserMarketModel;
import town.kairos.authuser.models.UserModel;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
        @Spec(path="userType", spec=Equal.class),
        @Spec(path="userStatus", spec=Equal.class),
        @Spec(path="email", spec= Like.class),
        @Spec(path="fullName", spec= Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {

    }

    public static Specification<UserModel> userMarketId(final UUID marketId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<UserModel, UserMarketModel> userProd = root.join("usersMarkets");
            return cb.equal(userProd.get("marketId"), marketId);
        };
    }
}
