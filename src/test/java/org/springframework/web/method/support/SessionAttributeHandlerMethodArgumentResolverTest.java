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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.support.WebArgumentResolver.UNRESOLVED;

public class SessionAttributeHandlerMethodArgumentResolverTest {
    private SessionAttributeHandlerMethodArgumentResolver handlerMethodArgumentResolver;
    @Mock private WebArgumentResolver resolver;
    @Mock private MethodParameter methodParameter;
    @Mock private ModelAndViewContainer mavContainer;
    @Mock private NativeWebRequest webRequest;
    @Mock private WebDataBinderFactory binderFactory;
    private ModelMap modelMap;
    private static final Object RESOLVED = new Object();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        modelMap = new ModelMap();
        handlerMethodArgumentResolver = new SessionAttributeHandlerMethodArgumentResolver(resolver);
    }

    @Test
    public void testSupportsParameter_whenAnnotated_withSessionAttribute_shouldReturnTrue() throws Exception {
        when(methodParameter.hasParameterAnnotation(SessionAttribute.class)).thenReturn(true);
        assertThat(handlerMethodArgumentResolver.supportsParameter(methodParameter), is(equalTo(true)));
    }

    @Test
    public void testSupportsParameter_whenNotAnnotated_withSessionAttribute_shouldReturnFalse() throws Exception {
        when(methodParameter.hasParameterAnnotation(SessionAttribute.class)).thenReturn(false);
        assertThat(handlerMethodArgumentResolver.supportsParameter(methodParameter), is(equalTo(false)));
    }

    @Test
    public void testResolveArgument_whenDelegateResolverReturnsNull() throws Exception {
        when(mavContainer.getModel()).thenReturn(modelMap);
        when(resolver.resolveArgument(methodParameter, webRequest)).thenReturn(null);
        assertNull(handlerMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory));
        assertThat(modelMap.isEmpty(), is(true));
    }

    @Test
    public void testResolveArgument_whenDelegateResolverReturns_UNRESOLVED() throws Exception {
        when(mavContainer.getModel()).thenReturn(modelMap);
        when(resolver.resolveArgument(methodParameter, webRequest)).thenReturn(UNRESOLVED);
        assertNull(handlerMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory));
        assertThat(modelMap.isEmpty(), is(true));
    }

    @Test
    public void testResolveArgument_whenDelegateResolverReturns_RESOLUTION() throws Exception {
        methodParameter = new MethodParameter(ReflectionUtils.findMethod(TestClass.class, "testMethod", String.class), 0);
        methodParameter.initParameterNameDiscovery(new LocalVariableTableParameterNameDiscoverer());
        when(mavContainer.getModel()).thenReturn(modelMap);
        when(resolver.resolveArgument(methodParameter, webRequest)).thenReturn(RESOLVED);
        assertThat(handlerMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory), is(equalTo(RESOLVED)));
        assertThat(modelMap.isEmpty(), is(false));
        assertThat(modelMap.get("argument"), is(equalTo(RESOLVED)));
    }

    public static class TestClass {
        public void testMethod(@SessionAttribute String argument) {}
    }
}
