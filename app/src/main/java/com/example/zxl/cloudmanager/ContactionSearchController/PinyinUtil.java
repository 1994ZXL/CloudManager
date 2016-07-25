package com.example.zxl.cloudmanager.ContactionSearchController;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ZQQ on 2016/7/25.
 */
public class PinyinUtil {
    // 汉字返回拼音，字母原样返回，都转换为小写
    public static String getPinYin(String input) {
        ArrayList<HanziToPinYIN.Token> tokens = HanziToPinYIN.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinYIN.Token token : tokens) {
                if (HanziToPinYIN.Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase(Locale.US);
    }
}
