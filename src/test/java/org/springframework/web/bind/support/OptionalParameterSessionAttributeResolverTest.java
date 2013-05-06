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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.support.AbstractChainingSessionAttributeResolverTest.passesTheBuckAround;
import static org.springframework.web.bind.support.WebArgumentResolver.UNRESOLVED;

public class OptionalParameterSessionAttributeResolverTest {

    private OptionalParameterSessionAttributeResolver resolver;
    @Mock private SessionAttributeResolver next;
    @Mock private SessionAttributeParameter parameter;
    @Mock private SessionHandler handler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolver = new OptionalParameterSessionAttributeResolver(next);
    }

    @Test
    public void testThatWhenSessionAttributeIsAnnotatedAsMandatory_shouldReturnAsUnResolved_shouldReturnNull() throws Exception {
        when(parameter.isOptional()).thenReturn(false);
        assertThat(resolver.resolveSessionAttributeInternal(handler, parameter), passesTheBuckAround());
    }

    @Test
    public void testThatWhenSessionAttributeIsAnnotatedAsOptional_shouldReturnAsUNRESOLVED_Object() throws Exception {
        when(parameter.isOptional()).thenReturn(true);
        assertThat(resolver.resolveSessionAttributeInternal(handler, parameter), is(equalTo(UNRESOLVED)));
    }
}
