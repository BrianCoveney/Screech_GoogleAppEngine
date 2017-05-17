package helpers;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by brian on 15/03/17.
 */
public final class Const {

    public static final double ONE_HUNDRED_PERCENT = 1.0;
    public static final double SEVENTY_PERCENT = 0.7;
    public static final double SIXTY_PERCENT = 0.6;
    public static final double THIRTY_PERCENT = 0.3;


    // Formatting decimals to percentages with the NumberFormat class, e.g 1.0 is displayed as 100%
    // Ref: https://docs.oracle.com/javase/tutorial/i18n/format/numberFormat.html
    public static String displayPercent(Locale currentLocale, Double percent) {

        percent = new Double(percent);
        NumberFormat percentFormatter;
        String percentOut;

        percentFormatter = NumberFormat.getPercentInstance(currentLocale);
        percentOut = percentFormatter.format(percent);

        return percentOut;
    }



}
