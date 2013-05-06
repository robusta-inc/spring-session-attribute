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
package org.springframework.web.bind.support;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.MissingServletRequestSessionAttributeException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SessionAttributeWebArgumentResolverTest {
    public static final String AN_ALTERNATE_NAME = "anAlternateName";
    private WebArgumentResolver resolver;
    private MethodParameter methodParameter;
    private MockHttpServletRequest request;
    @Mock private NativeWebRequest nativeWebRequest;
    private TrailBlazer trailBlazerInSession = new TrailBlazer();
    private static final String TRAIL_BLAZER = "trailBlazer";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        when(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).thenReturn(request);
        resolver = new SessionAttributeWebArgumentResolver();
    }

    @Test
    public void testResolveArgument_whenSessionDoesNotHaveAttribute_MethodParameterAnnotatedWithCreateIfMissing_shouldReturnInitializedInstance() throws Exception {
        methodParameter = testController_testMethod1_sessionAttribute_createIfMissingEqualToTrue();
        Object resolution = resolver.resolveArgument(methodParameter, nativeWebRequest);
        assertThat(resolution, notNullValue());
        assertThat(resolution, instanceOf(TrailBlazer.class));
        assertSessionAttribute(TRAIL_BLAZER, resolution);
    }

    private void assertSessionAttribute(String attrName, Object attrValue) {
        assertThat(request.getSession().getAttribute(attrName), is(equalTo(attrValue)));
    }

    @Test
    public void testResolveArgument_whenSessionDoesNotHaveAttribute_MethodParameterAnnotatedWithCreateIfMissing_andNonDefaultAttributeName_shouldReturnInitializedInstance() throws Exception {
        methodParameter = testController_testMethod5_nonDefaultSessionAttributeName();
        Object resolution = resolver.resolveArgument(methodParameter, nativeWebRequest);
        assertThat(resolution, notNullValue());
        assertThat(resolution, instanceOf(TrailBlazer.class));
        assertSessionAttribute(AN_ALTERNATE_NAME, resolution);
    }

    @Test
    public void testResolveArgument_whenSessionHasAttribute_MethodParameterAnnotatedWithCreateIfMissing_shouldReturnInstanceFromSession() throws Exception {
        simulateTrailBlazerInSessionWithName_trailBlazer();
        methodParameter = testController_testMethod1_sessionAttribute_createIfMissingEqualToTrue();
        Object resolution = resolver.resolveArgument(methodParameter, nativeWebRequest);
        assertThat(resolution, notNullValue());
        assertThat(resolution, instanceOf(TrailBlazer.class));
        assertThat((TrailBlazer) resolution, is(equalTo(trailBlazerInSession)));
        assertSessionAttribute(TRAIL_BLAZER, trailBlazerInSession);
    }

    private void simulateTrailBlazerInSessionWithName_trailBlazer() {
        request.getSession().setAttribute("trailBlazer", trailBlazerInSession);
    }

    private MethodParameter testController_testMethod1_sessionAttribute_createIfMissingEqualToTrue() {
        return findTestMethodOrController("testMethod1");
    }

    @Test
    public void testResolveArgument_whenSessionHasAttribute_MethodParameterAnnotatedWithOptional_shouldReturnInstanceFromSession() throws Exception {
        simulateTrailBlazerInSessionWithName_trailBlazer();
        methodParameter = testController_testMethod2_optionalSessionAttribute_RequiredEqualToFalse();
        Object resolution = resolver.resolveArgument(methodParameter, nativeWebRequest);
        assertThat(resolution, notNullValue());
        assertThat(resolution, instanceOf(TrailBlazer.class));
        assertThat((TrailBlazer) resolution, is(equalTo(trailBlazerInSession)));
        assertSessionAttribute(TRAIL_BLAZER, trailBlazerInSession);
    }

    @Test
    public void testResolveArgument_whenSessionDoesNotHasAttribute_methodParameterAnnotatedWithOptional_shouldReturnUNRESOLVED() throws Exception {
        methodParameter = testController_testMethod2_optionalSessionAttribute_RequiredEqualToFalse();
        Object resolution = resolver.resolveArgument(methodParameter, nativeWebRequest);
        assertThat(resolution, notNullValue());
        assertThat(resolution, equalTo(WebArgumentResolver.UNRESOLVED));
    }

    private MethodParameter testController_testMethod2_optionalSessionAttribute_RequiredEqualToFalse() {
        return findTestMethodOrController("testMethod2");
    }

    @Test(expected = MissingServletRequestSessionAttributeException.class)
    public void testResolveArgument_whenSessionDoesNotHasAttribute_defaultIsRequired_shouldThrowException() throws Exception {
        methodParameter = testController_testMethod3_requiredSessionAttribute();
        resolver.resolveArgument(methodParameter, nativeWebRequest);
    }

    @Test
    public void testResolveArgument_whenSessionHasAttribute_MethodParameterAnnotatedWithRequired_shouldReturnInstanceFromSession() throws Exception {
        simulateTrailBlazerInSessionWithName_trailBlazer();
        methodParameter = testController_testMethod3_requiredSessionAttribute();
        Object resolution = resolver.resolveArgument(methodParameter, nativeWebRequest);
        assertThat(resolution, notNullValue());
        assertThat(resolution, instanceOf(TrailBlazer.class));
        assertThat((TrailBlazer) resolution, is(equalTo(trailBlazerInSession)));
        assertSessionAttribute(TRAIL_BLAZER, trailBlazerInSession);
    }

    @Test
    public void testResolveArgument_whenSessionHasAttribute_MethodParameterAnnotatedWithAlwaysCreateNew_shouldReturnNewInstance() throws Exception {
        simulateTrailBlazerInSessionWithName_trailBlazer();
        methodParameter = testController_testMethod4_alwaysCreateNewSessionAttribute();
        Object resolution = resolver.resolveArgument(methodParameter, nativeWebRequest);
        assertThat(resolution, notNullValue());
        assertThat(resolution, instanceOf(TrailBlazer.class));
        assertThat((TrailBlazer) resolution, is(not(equalTo(trailBlazerInSession))));
        assertSessionAttribute(TRAIL_BLAZER, resolution);
    }

    private MethodParameter testController_testMethod4_alwaysCreateNewSessionAttribute() {
        return findTestMethodOrController("testMethod4");
    }

    private MethodParameter testController_testMethod5_nonDefaultSessionAttributeName() {
        return findTestMethodOrController("testMethod5");
    }

    private MethodParameter testController_testMethod3_requiredSessionAttribute() {
        return findTestMethodOrController("testMethod3");
    }

    private MethodParameter findTestMethodOrController(String name) {
        MethodParameter methodParameter = new MethodParameter(ReflectionUtils.findMethod(TestController.class, name, String.class, TrailBlazer.class), 1);
        methodParameter.initParameterNameDiscovery(new LocalVariableTableParameterNameDiscoverer());
        return methodParameter;
    }

    public static class TrailBlazer {
        private final List<String> trail = new ArrayList<String>();
    }

    public static class TestController {
        public String testMethod1(@RequestParam String id, @SessionAttribute(createIfMissing = true) TrailBlazer trailBlazer) {
            return "next.view.jsp";
        }

        public String testMethod2(@RequestParam String id, @SessionAttribute(required = false) TrailBlazer trailBlazer) {
            return "next.view.jsp";
        }

        public String testMethod3(@RequestParam String id, @SessionAttribute TrailBlazer trailBlazer) {
            return "next.view.jsp";
        }

        public String testMethod4(@RequestParam String id, @SessionAttribute(createNew = true) TrailBlazer trailBlazer) {
            return "next.view.jsp";
        }

        public String testMethod5(@RequestParam String id, @SessionAttribute(value = AN_ALTERNATE_NAME, createIfMissing = true) TrailBlazer trailBlazer) {
            return "next.view.jsp";
        }
    }
}
