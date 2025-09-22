package com.itechart.main_service.service.impl;

import com.itechart.main_service.service.ParseLinkService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class ParseLinkServiceImpl implements ParseLinkService {
    @Override
    public Map<String, String> parseLink(String prLink) {
        // https://github.com/{owner}/{repo}/pull/{pr}
        Pattern pattern = Pattern.compile(
                "^https?://github\\.com/([^/]+)/([^/]+)/pull/(\\d+)(?:/.*)?$"
        );

        Matcher matcher = pattern.matcher(prLink);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid PR link: " + prLink);
        }

        Map<String, String> result = new HashMap<>();
        result.put("owner", matcher.group(1));
        result.put("repo",  matcher.group(2));
        result.put("pr",    matcher.group(3));
        return result;
    }

}
