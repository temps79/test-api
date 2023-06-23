package com.example.testapi.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Основная точка входа
 */
@Slf4j
@Api(tags = {"Основная точка входа системы"})
@ApiIgnore
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class MainController {
    private final String FIELD_NAME_REDIRECT = "redirectUrl";
    @Value("${main.script-path:empty}")
    private String scriptPath;
    private final String mainFtlPath = "main.ftl";
    private final Configuration ftlConfiguration;

    public MainController(Configuration ftlConfiguration) {
        this.ftlConfiguration = ftlConfiguration;
    }

    @RequestMapping(value = "/")
    public void redirectToMain(HttpServletRequest request,
                               HttpServletResponse response,
                               @ApiIgnore @AuthenticationPrincipal User user) throws IOException, TemplateException {
        if (user == null) {
            var redirectUrl = request.getRequestURL().toString();
            if (redirectUrl.endsWith("/")) {
                redirectUrl = redirectUrl.substring(0, redirectUrl.length() - 1);
            }
            response.sendRedirect("/login?" + FIELD_NAME_REDIRECT + "=" + redirectUrl);
        } else {
            response.sendRedirect("/cp");
        }
    }

    @RequestMapping(value = {"login/**", "cp/**"}, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @ApiIgnore @AuthenticationPrincipal User user) throws IOException, TemplateException {
        try (StringWriter writer = new StringWriter()) {
            Template temp = ftlConfiguration.getTemplate(mainFtlPath);
            Path pathToFile = Paths.get(scriptPath);
            File file = new File(pathToFile.toAbsolutePath().toUri());
            String data = FileUtils.readFileToString(file, "UTF-8");
            temp.process(Map.of("script", data), writer);
            return writer.toString();
        }
    }


    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/");
    }


}
