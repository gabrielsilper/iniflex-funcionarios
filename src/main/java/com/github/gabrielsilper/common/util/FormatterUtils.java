package com.github.gabrielsilper.common.util;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatterUtils {
    public static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final Locale BR_LOCALE = Locale.of("pt", "BR");
    public static final NumberFormat MOEDA_FORMATTER = NumberFormat.getCurrencyInstance(BR_LOCALE);
}
