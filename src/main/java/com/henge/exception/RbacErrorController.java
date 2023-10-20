package com.henge.exception;

import com.henge.model.RestResp;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/error")
@EnableConfigurationProperties({ServerProperties.class})
public class RbacErrorController implements ErrorController {
    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    public RbacErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //设置状态码
        HttpStatus status;
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                status = HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        //设置异常栈信息
        boolean includeStackTrace;
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            includeStackTrace = true;
        } else if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            String parameter = request.getParameter("trace");
            if (parameter == null) {
                includeStackTrace = false;
            } else {
                includeStackTrace = !"false".equals(parameter.toLowerCase());
            }
        } else {
            includeStackTrace = false;
        }

        //设置请求属性
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> body = this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);

        Map<String, Object> model = Collections.unmodifiableMap(body);
        response.setStatus(status.value());
        return new ModelAndView("error", model);
    }

    /**
     * 当通过身份验证，继续进入，而找不到后台html页面时，程序进入这里
     *
     * @param request
     * @return
     */
    @RequestMapping()
    @ResponseBody
    public ResponseEntity<RestResp> error(HttpServletRequest request) {
        //设置异常栈信息
        boolean includeStackTrace;
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            includeStackTrace = true;
        } else if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            String parameter = request.getParameter("trace");
            if (parameter == null) {
                includeStackTrace = false;
            } else {
                includeStackTrace = !"false".equals(parameter.toLowerCase());
            }
        } else {
            includeStackTrace = false;
        }

        //设置请求属性
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> body = this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);

        //设置响应信息
        String path = MapUtils.getString(body, "path");
        String message = MapUtils.getString(body, "message");
        RestResp restResp = RestResp.error(RestResp.ERROR, message, path);
        if (MapUtils.getInteger(body, "status") == 404) {
            restResp = RestResp.error(RestResp.NOT_FOUND, "访问的路径不存在", path);
        }
        return new ResponseEntity<>(restResp, HttpStatus.OK);
    }

    @Override
    public String getErrorPath() {
        return "";
    }
}
