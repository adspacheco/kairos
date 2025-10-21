package town.kairos.market.consumers;

import com.netflix.discovery.converters.Auto;
import org.apache.catalina.User;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import town.kairos.market.dtos.UserEventDto;
import town.kairos.market.enums.ActionType;
import town.kairos.market.services.UserService;

@Component
public class UserConsumer {

    @Autowired
    UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${kairos.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${kairos.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT,
                                    ignoreDeclarationExceptions = "true")
    ))
    public void listenUserEvent(@Payload UserEventDto userEventDto) {
        var userModel = userEventDto.convertToUserModel();

        switch (ActionType.valueOf(userEventDto.getActionType())) {
            case CREATE:
            case UPDATE:
                userService.save(userModel);
                break;
            case DELETE:
                userService.delete(userEventDto.getUserId());
                break;
        }
    }

}
