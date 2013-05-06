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
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.SessionAttribute;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SessionAttributeParameterTest {
    public static final String DIFFERENT_NAME = "differentName";
    private SessionAttributeParameter parameter;
    @Mock private MethodParameter methodParameter;
    @Mock private SessionAttribute sessionAttribute;
    private static final String PARAMETER_NAME = "testParameterObject";
    private static final Class PARAMETER_TYPE = TestParameterObject.class;

    private static class TestParameterObject {

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setExpectationsOnMethodParameterToReturn(sessionAttribute, PARAMETER_NAME, PARAMETER_TYPE);
        parameter = new SessionAttributeParameter(methodParameter);
    }

    private void setExpectationsOnMethodParameterToReturn(SessionAttribute sessionAttribute, String parameterName, Class parameterType) {
        when(methodParameter.getParameterAnnotation(SessionAttribute.class)).thenReturn(sessionAttribute);
        when(methodParameter.getParameterName()).thenReturn(parameterName);
        when(methodParameter.getParameterType()).thenReturn(parameterType);
    }

    @Test
    public void testResolvedAttributeName_whenAnnotatedWithSessionAtributeName_shouldUseAnnotatedParameter() throws Exception {
        when(sessionAttribute.value()).thenReturn(DIFFERENT_NAME);
        assertThat(parameter.resolvedAttributeName(), is(equalTo(DIFFERENT_NAME)));
    }

    @Test
    public void testResolvedAttributeName_whenDefaultValueInAnnotation_shouldUseParameterNameFromMethodParameter() throws Exception {
        when(sessionAttribute.value()).thenReturn("");
        assertThat(parameter.resolvedAttributeName(), is(equalTo(PARAMETER_NAME)));
    }

    @Test
    public void testCreateableWhenNull_whenAnnotatedWithTrue_shouldReturnTrue() throws Exception {
        when(sessionAttribute.createIfMissing()).thenReturn(true);
        assertThat(parameter.createableWhenNull(), is(equalTo(true)));
    }

    @Test
    public void testCreateableWhenNull_whenAnnotatedWithFalse_shouldReturnFalse() throws Exception {
        when(sessionAttribute.createIfMissing()).thenReturn(false);
        assertThat(parameter.createableWhenNull(), is(equalTo(false)));
    }

    @Test
    public void testCreateNewInstance() throws Exception {
        Object newInstance = parameter.createNewInstance();
        assertThat(newInstance, notNullValue());
        assertThat(newInstance, instanceOf(TestParameterObject.class));
    }

    @Test
    public void testIsOptional() throws Exception {

    }

    @Test
    public void testIsAlwaysCreate() throws Exception {

    }
}
