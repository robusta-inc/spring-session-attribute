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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SessionLookupSessionAttributeResolverTest {
    public static final String ATTRIBUTE = "AN_ATTRIBUTE";
    public static final Object RESOLUTION = new Object();
    private SessionLookupSessionAttributeResolver resolver;
    @Mock private SessionAttributeResolver next;
    @Mock private SessionAttributeParameter parameter;
    @Mock private SessionHandler handler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolver = new SessionLookupSessionAttributeResolver(next);
    }

    @Test
    public void testResolveSessionAttributeInternal_shouldLookIntoSessionUsingSessionHandler_shouldReturnNull_whenTheHandlerReturnsNullAttributeValue() throws Exception {
        when(parameter.resolvedAttributeName()).thenReturn(ATTRIBUTE);
        when(handler.getAttributeIfDefined(ATTRIBUTE)).thenReturn(null);
        assertNull(resolver.resolveSessionAttributeInternal(handler, parameter));
    }

    @Test
    public void testResolveSessionAttributeInternal_shouldLookIntoSessionUsingSessionHandler_shouldReturnResolution_whenTheHandlerReturnsAnAttributeValue() throws Exception {
        when(parameter.resolvedAttributeName()).thenReturn(ATTRIBUTE);
        when(handler.getAttributeIfDefined(ATTRIBUTE)).thenReturn(RESOLUTION);
        assertThat(resolver.resolveSessionAttributeInternal(handler, parameter), is(equalTo(RESOLUTION)));
    }

}
