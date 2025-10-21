package town.kairos.market.specifications;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.apache.catalina.User;
import org.springframework.data.jpa.domain.Specification;
import town.kairos.market.models.ActionModel;
import town.kairos.market.models.ContextModel;
import town.kairos.market.models.MarketModel;
import town.kairos.market.models.UserModel;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "marketType", spec = Equal.class),
            @Spec(path = "marketStatus", spec = Equal.class),
            @Spec(path = "title", spec = Like.class),
            @Spec(path = "category", spec = Like.class),
            @Spec(path = "location", spec = Like.class)
    })
    public interface MarketSpec extends Specification<MarketModel> {}

    @And({
            @Spec(path = "email", spec = Equal.class),
            @Spec(path = "fullName", spec = Equal.class),
            @Spec(path = "userStatus", spec = Like.class),
            @Spec(path = "userType", spec = Like.class),
    })
    public interface UserSpec extends Specification<UserModel> {}

    @Spec(path = "title", spec = Like.class)
    public interface ContextSpec extends Specification<ContextModel> {}

    @Spec(path = "title", spec = Like.class)
    public interface ActionSpec extends Specification<ActionModel> {}

    public static Specification<ContextModel> contextMarketId(final UUID marketId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<ContextModel> context = root;
            Root<MarketModel> market = query.from(MarketModel.class);
            Expression<Collection<ContextModel>> marketContexts = market.get("contexts");
            return cb.and(cb.equal(market.get("marketId"), marketId), cb.isMember(context, marketContexts));
        };
    }

    public static Specification<ActionModel> actionContextId(final UUID contextId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<ActionModel> action = root;
            Root<ContextModel> context = query.from(ContextModel.class);
            Expression<Collection<ActionModel>> contextActions = context.get("actions");
            return cb.and(cb.equal(context.get("contextId"), contextId), cb.isMember(action, contextActions));
        };
    }

    public static Specification<UserModel> userMarketId(final UUID marketId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<UserModel> user = root;
            Root<MarketModel> market = query.from(MarketModel.class);
            Expression<Collection<UserModel>> marketUsers = market.get("users");
            return cb.and(cb.equal(market.get("marketId"), marketId), cb.isMember(user, marketUsers));
        };
    }

    public static Specification<MarketModel> marketUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<MarketModel> market = root;
            Root<UserModel> user = query.from(UserModel.class);
            Expression<Collection<MarketModel>> usersMarkets = user.get("markets");
            return cb.and(cb.equal(market.get("userId"), userId), cb.isMember(market, usersMarkets));
        };
    }
}
