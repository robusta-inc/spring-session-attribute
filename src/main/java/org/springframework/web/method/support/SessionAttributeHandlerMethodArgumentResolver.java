/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.web.method.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionAttributeParameter;
import org.springframework.web.bind.support.SessionAttributeWebArgumentResolver;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * As of Spring MVC 3.1+ WebArgumentResolver is deprecated and replaced
 * with HandlerMethodArgumentResolver
 *
 * <p>A HandlerMethodArgumentResolver implementation that delegates to a
 * WebArgumentResolver</p>
 */
public class SessionAttributeHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAttributeHandlerMethodArgumentResolver.class);
    private final WebArgumentResolver argumentResolver;

    public SessionAttributeHandlerMethodArgumentResolver(WebArgumentResolver argumentResolver) {
        this.argumentResolver = argumentResolver;
    }

    public SessionAttributeHandlerMethodArgumentResolver() {
        this(new SessionAttributeWebArgumentResolver());
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionAttribute.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object resolution = argumentResolver.resolveArgument(parameter, webRequest);
        // per documentation of HandlerMethodArgumentResolver.resolveArgument a null is required in an unresolved scenario.
        if(resolution == WebArgumentResolver.UNRESOLVED) {
            LOGGER.warn("Unable to resolve method parameter, WebArgumentResolver returned UNRESOLVED, returning null");
            return null;
        } else if(resolution != null) {
            SessionAttributeParameter attributeParameter = new SessionAttributeParameter(parameter);
            LOGGER.trace("Adding Session attribute into ModelMap with model name: '{}'", parameter.getParameterName());
            mavContainer.getModel().put(attributeParameter.resolvedAttributeName(), resolution);
        }
        LOGGER.trace("Returning resolution: '{}' for method parameter: '{}", resolution, parameter.getParameterName());
        return resolution;
    }
}
