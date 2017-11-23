package implementations.fixtures;

import org.pmw.tinylog.Logger;

import java.util.Locale;

public class LocaleFixture implements interfaces.fixtures.LocaleFixture {
    private Locale defaultLocale;

    @Override
    public void SetLocale(Locale locale) {
        defaultLocale = Locale.getDefault();
        Locale.setDefault(locale);
        Logger.info(String.format("Locale changed to: %s", locale));
    }

    @Override
    public void resetDefault() {
        Locale.setDefault(defaultLocale);
    }
}
