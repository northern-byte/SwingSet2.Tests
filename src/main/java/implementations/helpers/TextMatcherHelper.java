package implementations.helpers;

import org.fest.swing.util.TextMatcher;

import java.util.Objects;

public class TextMatcherHelper {
    public TextMatcher exactText(String expected) {
        return new TextMatcher() {
            @Override
            public boolean isMatching(String text) {
                return Objects.equals(text, expected);
            }

            @Override
            public String description() {
                return "Text must be the same";
            }

            @Override
            public String formattedValues() {
                return null;
            }
        };
    }

    public TextMatcher containsText(String substring) {
        return new TextMatcher() {
            @Override
            public boolean isMatching(String text) {
                return text.contains(substring);
            }

            @Override
            public String description() {
                return "Text must contain a substring";
            }

            @Override
            public String formattedValues() {
                return null;
            }
        };
    }
}
