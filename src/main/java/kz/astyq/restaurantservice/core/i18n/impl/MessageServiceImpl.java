package kz.astyq.restaurantservice.core.i18n.impl;

import kz.astyq.restaurantservice.core.i18n.MessageService;
import kz.astyq.restaurantservice.core.model.AppSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageSource messageSource;
    private final AppSettings appSettings;

    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(
                code,
                args,
                code,
                Locale.forLanguageTag(appSettings.getLocale()));
    }

    @Override
    public String getMessage(String code) {
        return this.getMessage(code, (Object) null);
    }
}
