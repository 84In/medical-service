package com.vasd.medical_service.common.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class CloudinaryUtils {
    private static final Pattern PUBLIC_ID_PATTERN = Pattern.compile("/upload/.+?/(.+?)\\.[a-z]{3,4}$");

    public static String extractPublicIdFromUrl(String url) {
        if (url == null || url.isBlank()) return null;

        Matcher matcher = PUBLIC_ID_PATTERN.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }
    public static List<String> extractImageUrlsFromHtml(String html) {
        List<String> urls = new ArrayList<>();
        Matcher matcher = Pattern.compile("<img[^>]+src=\"([^\"]+)\"").matcher(html);
        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }

}
