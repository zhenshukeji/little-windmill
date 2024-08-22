package com.zhenshu.common.utils;

import org.apache.poi.util.StringUtil;

/**
 * 将数字金额转为大写汉字金额
 *
 * @author Jing
 */
public class ConvertUpMoney {

    /**
     * 大写数字
     */
    private static final String[] NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    /**
     * 整数部分的单位
     */
    private static final String[] IUNIT = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};
    /**
     * 小数部分的单位
     */
    private static final String[] DUNIT = {"角", "分", "厘"};

    private static final String ALL = "整";

    /**
     * 转成中文的大写金额
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String toChinese(String str) {
        if(str == null){
            return "零元整";
        }
        // 判断输入的金额字符串是否符合要求
        String regTxt = "(-)?[\\d]*(.)?[\\d]*";
        if (StringUtils.isBlank(str) || !str.matches(regTxt)) {
            System.out.println("抱歉，请输入数字！");
            return str;
        }

        String zero = "0";
        String twoZero = "0.00";
        String oneZero = "0.0";
        if (zero.equals(str) || twoZero.equals(str) || oneZero.equals(str)) {
            return "零元整";
        }

        //判断是否存在负号"-"
        boolean flag = false;
        String negative = "-";
        String emptyStr = "";
        if (str.startsWith(negative)) {
            flag = true;
            str = str.replaceAll(negative, emptyStr);
        }

        str = str.replaceAll(",", emptyStr);
        //去掉","
        String integerStr;//整数部分数字
        String decimalStr;//小数部分数字
        //初始化：分离整数部分和小数部分
        String point = ".";
        if (str.indexOf(point) > 0) {
            integerStr = str.substring(0, str.indexOf(point));
            decimalStr = str.substring(str.indexOf(point) + 1);
        } else if (str.indexOf(point) == 0) {
            integerStr = emptyStr;
            decimalStr = str.substring(1);
        } else {
            integerStr = str;
            decimalStr = emptyStr;
        }

        //beyond超出计算能力，直接返回
        if (integerStr.length() > IUNIT.length) {
            System.out.println(str + "：超出计算能力");
            return str;
        }
        //整数部分数字
        int[] integers = toIntArray(integerStr);
        //判断整数部分是否存在输入012的情况
        if (integers.length > 1 && integers[0] == 0) {
            System.out.println("抱歉，请输入数字！");
            if (flag) {
                str = negative + str;
            }
            return str;
        }
        //设置万单位
        boolean isWan = isWan5(integerStr);
        //小数部分数字
        int[] decimals = toIntArray(decimalStr);
        //返回最终的大写金额
        String result = getChineseInteger(integers, isWan) + getChineseDecimal(decimals);
        if (flag) {
            //如果是负数，加上"负"
            return "负" + result;
        } else {
            boolean isInteger = true;
            for (int decimal : decimals) {
                if (decimal != 0) {
                    isInteger = false;
                    break;
                }
            }
            return isInteger ? result + ALL : result;
        }
    }

    /**
     * 将字符串转为int数组
     *
     * @param number 数字
     * @return 数组
     */
    private static int[] toIntArray(String number) {
        int[] array = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            array[i] = Integer.parseInt(number.substring(i, i + 1));
        }
        return array;
    }

    /**
     * 将整数部分转为大写的金额
     *
     * @param integers    数组
     * @param tenThousand 万
     * @return 金额
     */
    public static String getChineseInteger(int[] integers, boolean tenThousand) {
        String emptyStr = "";
        StringBuilder chineseInteger = new StringBuilder(emptyStr);
        int length = integers.length;
        if (length == 1 && integers[0] == 0) {
            return emptyStr;
        }
        for (int i = 0; i < length; i++) {
            String key = emptyStr;
            if (integers[i] == 0) {
                //万（亿）
                if ((length - i) == 13) {
                    key = IUNIT[4];
                } else if ((length - i) == 9) {
                    //亿
                    key = IUNIT[8];
                } else if ((length - i) == 5 && tenThousand) {
                    //万
                    key = IUNIT[4];
                } else if ((length - i) == 1) {
                    //元
                    key = IUNIT[0];
                }
                if ((length - i) > 1 && integers[i + 1] != 0) {
                    key += NUMBERS[0];
                }
            }
            chineseInteger.append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
        }
        return chineseInteger.toString();
    }

    /**
     * 将小数部分转为大写的金额
     *
     * @param decimals 数值
     * @return 字符串
     */
    private static String getChineseDecimal(int[] decimals) {
        String emptyStr = "";
        StringBuilder chineseDecimal = new StringBuilder(emptyStr);
        for (int i = 0; i < decimals.length; i++) {
            if (i == 3) {
                break;
            }
            chineseDecimal.append(decimals[i] == 0 ? emptyStr : (NUMBERS[decimals[i]] + DUNIT[i]));
        }
        return chineseDecimal.toString();
    }

    /**
     * 判断当前整数部分是否已经是达到【万】
     *
     * @param integerStr 数字字符串
     * @return 是否
     */
    private static boolean isWan5(String integerStr) {
        int length = integerStr.length();
        if (length > 4) {
            String subInteger = "";
            if (length > 8) {
                subInteger = integerStr.substring(length - 8, length - 4);
            } else {
                subInteger = integerStr.substring(0, length - 4);
            }
            return Integer.parseInt(subInteger) > 0;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        String number = "12.56";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "1234567890563886.123";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "1600";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "156,0";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "-156,0";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "0.12";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "0";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "01.12";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "0125";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "-0125";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        number = "sdw5655";
        System.out.println(number + ": " + ConvertUpMoney.toChinese(number));

        System.out.println(null + ": " + ConvertUpMoney.toChinese(null));
    }

}
