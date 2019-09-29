package net.onfirenetwork.onsetjava.api.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Getter
@NoArgsConstructor
public class EventBus {

    private HashMap<Class<Event>, List<Consumer<Event>>> listeners = new HashMap<>();
    private Consumer<Class<Event>> registerHandler = null;

    public EventBus(Consumer<Class<Event>> registerHandler) {
        this.registerHandler = registerHandler;
    }

    public <T extends Event> void listen(Class<T> type, Consumer<T> listener) {
        if (!listeners.containsKey(type)) {
            listeners.put((Class<Event>) type, new ArrayList<>());
            if (registerHandler != null) {
                registerHandler.accept((Class<Event>) type);
            }
        }
        listeners.get(type).add((Consumer<Event>) listener);
    }

    public void listen(Object instance) {
        for (Method m : instance.getClass().getDeclaredMethods()) {
            if (m.getParameterCount() == 1) {
                Class<?> paramType = m.getParameterTypes()[0];
                if (paramType.getSuperclass().equals(Event.class)) {
                    Class<Event> eventType = (Class<Event>) paramType;
                    listen(eventType, toConsumer(m, instance));
                }
            }
        }
    }

    private Consumer<Event> toConsumer(Method m, Object instance) {
        m.setAccessible(true);
        return e -> {
            try {
                m.invoke(instance, e);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        };
    }

    public void fire(Event event) {
        listeners.getOrDefault(event.getClass(), new ArrayList<>()).forEach(eventConsumer -> eventConsumer.accept(event));
    }
}
