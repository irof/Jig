package jig.infrastructure.mybatis;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SqlType {
    INSERT("insert +into +([\\w.]+).+"),
    SELECT("select.+ from +([\\w]+) .+"),
    UPDATE("update +([\\w.]+) .+"),
    DELETE("delete +from +([\\w.]+) .+");

    private static final Logger LOGGER = Logger.getLogger(SqlType.class.getName());
    private final Pattern pattern;

    SqlType(String regex) {
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public String extractTable(String sql) {
        Matcher matcher = pattern.matcher(sql);
        if (matcher.matches()) {
            return matcher.group(1);
        }

        LOGGER.warning("テーブル名がわかりません。 " + sql);
        return "(不明)";
    }
}