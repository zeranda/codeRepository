package commonUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;

public class BigDecimalUtil {

    public static String format(BigDecimal money) {
        return new DecimalFormat("###0.00").format(Optional.ofNullable(money).orElse(BigDecimal.ZERO));
    }

    public static String formatRatio(BigDecimal ratio) {
        return ratio.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_DOWN).toString();
    }
}
