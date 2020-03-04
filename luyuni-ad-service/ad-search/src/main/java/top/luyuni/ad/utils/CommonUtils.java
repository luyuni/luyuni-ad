package top.luyuni.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public class CommonUtils {
    /**
     *
     * @param key
     * @param map
     * @param factory
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V getorCreate(K key, Map<K, V> map,
                                       Supplier<V> factory) {
        /**
         * java8之前。从map中根据key获取value操作可能会有下面的操作
         * Object key = map.get("key");
         * if (key == null) {
         *     key = new Object();
         *     map.put("key", key);
         * }
         * java8之后。上面的操作可以简化为一行，若key对应的value为空，会将第二个参数的返回值存入并返回
         * Object key2 = map.computeIfAbsent("key", k -> new Object());
         */
        return map.computeIfAbsent(key, k -> factory.get());
    }

    /**
     * 拼接String
     * @param args
     * @return
     */
    public static String stringConcat(String... args) {

        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    // Tue Jan 01 08:00:00 CST 2019
    /**
     * 将String转换为Date
     * @param dateString
     * @return
     */
    public static Date parseStringDate(String dateString) {

        try {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.US
            );
            return DateUtils.addHours(
                    dateFormat.parse(dateString),
                    -8
            );
        } catch (ParseException ex) {
            log.error("parseStringDate error: {}", dateString);
            return null;
        }
    }
}
