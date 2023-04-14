package ru.nikkitavr.geotagger.geoservice.utils.dispatching;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception.InvalidArgumentException;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception.MethodNotFoundException;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception.MultipleArgumentsException;
import ru.nikkitavr.geotagger.geoservice.utils.dispatching.exception.MultipleMethodTypeUsageException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Component
@Scope("prototype")
public class MessageDispatcher {

    private Object serviceClass;
    private boolean isRegistered = false;

    public void registerServiceClass(Object serviceClass) {
        this.serviceClass = serviceClass;
        isRegistered = true;
    }

    public <T extends MessageBaseEntity> void routeMessage(T messageDto, WebSocketSession session) throws InvocationTargetException, IllegalAccessException {
        if(!isRegistered){
            System.out.println("NOT REGISTERED SERVICE. NOT REGISTERED SESSION");
            return;
        }
        Method[] methods = serviceClass.getClass().getMethods();
        for (Method method : methods) {
            MethodType[] annotations = method.getAnnotationsByType(MethodType.class);
            if (annotations.length == 0){
                continue;
            }
            //annotationPreprocess(method, annotations);

            if(messageDto.getMethod().equalsIgnoreCase(annotations[0].value())){
                method.invoke(serviceClass, messageDto, session);
                return;
            }
        }
        throw new MethodNotFoundException(messageDto.getMethod());
    }

    private /*<T extends MessageBaseEntity>*/ void annotationPreprocess(Method method, MethodType[] annotations/*, T messageDto*/) {
        if(annotations.length > 1){
            throw new MultipleMethodTypeUsageException();
        }

        if(method.getParameterTypes().length != 2) {
            throw new MultipleArgumentsException();
        }

        if(MessageBaseEntity.class.isAssignableFrom(method.getParameterTypes()[0])) {
            throw new InvalidArgumentException(method.getParameterTypes()[0]);
        }

    }
}
