package kz.astyq.restaurantservice.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppSettings {

    private String defaultLocale;
    private static final List<String> locales = List.of("ru", "en", "kk");

    public String getLocale() {
        String locale = LocaleContextHolder.getLocale().getLanguage();
        if (locales.contains(locale)) {
            return locale;
        }
        return defaultLocale;
    }

}