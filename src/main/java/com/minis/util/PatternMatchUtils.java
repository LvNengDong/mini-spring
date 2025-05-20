package com.minis.util;

public abstract class PatternMatchUtils {

    /**
     * 该方法用于将一个字符串与给定的模式进行匹配，支持 "xxx*"、"*xxx"、"*xxx*" 和 "xxx*yyy" 等模式，也支持直接相等匹配。
     *
     * @param pattern 用于匹配的模式字符串。
     * @param str     需要进行匹配的目标字符串。
     * @return 若目标字符串与模式匹配则返回 true，否则返回 false。
     */
    public static boolean simpleMatch(String pattern, String str) {
        if (pattern == null || str == null) {
            return false;
        }
        // 查找模式串中的第一个 *
        int firstIndex = pattern.indexOf('*');
        if (firstIndex == -1) { // 若模式串中不存在 *，则直接进行字符串比较
            return pattern.equals(str);
        }

        if (firstIndex == 0) { // 若模式串以 * 开头
            if (pattern.length() == 1) {
                return true;
            }
            int nextIndex = pattern.indexOf('*', 1); // 查找下一个 * 的位置
            if (nextIndex == -1) {
                return str.endsWith(pattern.substring(1));
            }
            String part = pattern.substring(1, nextIndex);
            if (part.isEmpty()) {
                return simpleMatch(pattern.substring(nextIndex), str);
            }
            int partIndex = str.indexOf(part);
            while (partIndex != -1) {
                if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
                    return true;
                }
                partIndex = str.indexOf(part, partIndex + 1);
            }
            return false;
        }

        return (str.length() >= firstIndex &&
                pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex)) &&
                simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
    }

    /**
     * Match a String against the given patterns, supporting the following simple
     * pattern styles: "xxx*", "*xxx", "*xxx*" and "xxx*yyy" matches (with an
     * arbitrary number of pattern parts), as well as direct equality.
     *
     * @param patterns the patterns to match against
     * @param str      the String to match
     * @return whether the String matches any of the given patterns
     */
    public static boolean simpleMatch(String[] patterns, String str) {
        if (patterns != null) {
            for (String pattern : patterns) {
                if (simpleMatch(pattern, str)) {
                    return true;
                }
            }
        }
        return false;
    }

}

