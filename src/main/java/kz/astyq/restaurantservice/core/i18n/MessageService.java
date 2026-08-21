package kz.astyq.restaurantservice.core.i18n;

public interface MessageService {
    String getMessage(String messageCode, Object... args);

    String getMessage(String messageCode);
}
