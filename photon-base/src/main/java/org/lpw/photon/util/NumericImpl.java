package org.lpw.photon.util;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lpw
 */
@Component("photon.util.numeric")
public class NumericImpl implements Numeric {
    private final String integerPattern = "^[+-]?\\d+$";
    private final String floatPattern = "^[+-]?\\d+(\\.\\d+)?$";

    @Inject
    private Validator validator;
    @Inject
    private Logger logger;
    private Map<String, DecimalFormat> formats = new ConcurrentHashMap<>();

    @Override
    public int toInt(Object object) {
        return toInt(object, 0);
    }

    @Override
    public int toInt(Object object, int defaultValue) {
        if (validator.isEmpty(object))
            return defaultValue;

        if (object instanceof Integer)
            return (int) object;

        if (object instanceof Long)
            return (int) (long) object;

        if (object instanceof Float)
            return Math.round((Float) object);

        if (object instanceof Double)
            return (int) Math.round((Double) object);

        String string = toString(object);
        if (string.indexOf('.') > -1)
            return (int) Math.round(stringToDouble(string, defaultValue));

        try {
            return validator.isMatchRegex(integerPattern, string) ? Integer.parseInt(string) : defaultValue;
        } catch (Exception e) {
            logger.warn(e, "将对象[{}]转化为int数值时发生异常！", object);

            return defaultValue;
        }
    }

    @Override
    public int hexToInt(String hex) {
        return hexToInt(hex, 0);
    }

    @Override
    public int hexToInt(String hex, int defaultValue) {
        if (validator.isEmpty(hex))
            return defaultValue;

        try {
            return Integer.parseInt(hex, 16);
        } catch (Throwable throwable) {
            logger.warn(throwable, "转化十六进制数据[{}]为整数时发生异常！", hex);

            return defaultValue;
        }
    }

    @Override
    public long toLong(Object object) {
        return toLong(object, 0L);
    }

    @Override
    public long toLong(Object object, long defaultValue) {
        if (validator.isEmpty(object))
            return defaultValue;

        if (object instanceof Long)
            return (long) object;

        if (object instanceof Integer)
            return (int) object;

        if (object instanceof Float)
            return Math.round((Float) object);

        if (object instanceof Double)
            return Math.round((Double) object);

        String string = toString(object);
        if (string.indexOf('.') > -1)
            return Math.round(stringToDouble(string, defaultValue));

        try {
            return validator.isMatchRegex(integerPattern, string) ? Long.parseLong(string) : defaultValue;
        } catch (Exception e) {
            logger.warn(e, "将对象[{}]转化为long数值时发生异常！", object);

            return defaultValue;
        }
    }

    @Override
    public float toFloat(Object object) {
        return toFloat(object, 0.0F);
    }

    @Override
    public float toFloat(Object object, float defaultValue) {
        if (validator.isEmpty(object))
            return defaultValue;

        if (object instanceof Float)
            return (float) object;

        if (object instanceof Double)
            return (float) (double) object;

        if (object instanceof Integer)
            return (int) object;

        if (object instanceof Long)
            return (float) (long) object;

        String string = toString(object);
        try {
            return validator.isMatchRegex(floatPattern, string) ? Float.parseFloat(string) : defaultValue;
        } catch (Exception e) {
            logger.warn(e, "将对象[{}]转化为float数值时发生异常！", object);

            return defaultValue;
        }
    }

    @Override
    public double toDouble(Object object) {
        return toDouble(object, 0.0D);
    }

    @Override
    public double toDouble(Object object, double defaultValue) {
        if (validator.isEmpty(object))
            return defaultValue;

        if (object instanceof Double)
            return (double) object;

        if (object instanceof Float)
            return (float) object;

        if (object instanceof Integer)
            return (int) object;

        if (object instanceof Long)
            return (long) object;

        return stringToDouble(toString(object), defaultValue);
    }

    private double stringToDouble(String string, double defaultValue) {
        try {
            return validator.isMatchRegex(floatPattern, string) ? Double.parseDouble(string) : defaultValue;
        } catch (Exception e) {
            logger.warn(e, "将对象[{}]转化为double数值时发生异常！", string);

            return defaultValue;
        }
    }

    private String toString(Object object) {
        return (object instanceof String ? (String) object : object.toString()).replaceAll(",", "");
    }

    @Override
    public int[] toInts(String string) {
        return toInts(string, 0);
    }

    @Override
    public int[] toInts(String string, int defaultValue) {
        if (validator.isEmpty(string))
            return new int[0];

        return toInts(string.split(","), defaultValue);
    }

    @Override
    public int[] toInts(String[] array) {
        return toInts(array, 0);
    }

    @Override
    public int[] toInts(String[] array, int defaultValue) {
        if (validator.isEmpty(array))
            return new int[0];

        int[] ns = new int[array.length];
        for (int i = 0; i < ns.length; i++)
            ns[i] = toInt(array[i], defaultValue);

        return ns;
    }

    @Override
    public String toString(Number number, String format) {
        return formats.computeIfAbsent(format, DecimalFormat::new).format(number);
    }
}
