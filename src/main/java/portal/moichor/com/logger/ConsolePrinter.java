package portal.moichor.com.logger;



import org.springframework.stereotype.Component;
import portal.moichor.com.logger.options.Color;
import portal.moichor.com.logger.options.Format;

@Component
public class ConsolePrinter {
    //todo: add stringbuilder
    private final static String optionFlag = "\033[";
    private String color = Color.GREEN;
    private String thickness = "";
    private String underline = "";
    private String emoji = "";
    private boolean lowercase;
    private boolean uppercase;

    public ConsolePrinter bold() {
        this.thickness = Format.BOLD;
        return this;
    }

    public ConsolePrinter regular() {
        this.thickness = Format.REGULAR;
        return this;
    }

    public ConsolePrinter underline() {
        this.underline = Format.UNDERLINE;
        return this;
    }

    public ConsolePrinter color(String color) {
        this.color = color;
        return this;
    }

    public ConsolePrinter uppercase() {
        uppercase = true;
        return this;
    }

    public ConsolePrinter lowercase() {
        lowercase = true;
        return this;
    }

    public ConsolePrinter print(String text) {
        System.out.println(emoji + getPrintParameters() + getText(text) + Color.NEUTRAL);
        return this;
    }

    public ConsolePrinter emoji(String emoji) {
        this.emoji = emoji;
        return this;
    }

    public ConsolePrinter reset() {
        this.color = Color.GREEN;
        this.thickness = "";
        this.underline = "";
        this.emoji = "";
        this.lowercase = false;
        this.uppercase = false;
        return this;
    }

    private String getPrintParameters() {
        return optionFlag + thickness + underline + color;
    }

    private String getText(String text) {
        String convertedText;
        if (this.lowercase) {
            convertedText = text.toLowerCase();
        } else if (this.uppercase) {
            convertedText = text.toUpperCase();
        } else {
            convertedText = text;
        }
        return convertedText;
    }
}
