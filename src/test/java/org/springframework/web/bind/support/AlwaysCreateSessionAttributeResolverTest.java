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

public class AlwaysCreateSessionAttributeResolverTest {
    public static final String ATTRIBUTE = "AN_ATTRIBUTE";
    public static final Object NEW_INSTANCE = new Object();
    private AlwaysCreateSessionAttributeResolver resolver;
    @Mock private SessionAttributeResolver next;
    @Mock private SessionHandler handler;
    @Mock private SessionAttributeParameter parameter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolver = new AlwaysCreateSessionAttributeResolver(next);
    }

    @Test
    public void testIfCreateNewIsNotSpecified_shouldReturnNull() throws Exception {
        when(parameter.isAlwaysCreate()).thenReturn(false);
        assertNull(resolver.resolveSessionAttributeInternal(handler, parameter));
    }

    @Test
    public void testIfCreateNewIsSpecified_shouldCreateNewAndReturn() throws Exception {
        when(parameter.isAlwaysCreate()).thenReturn(true);
        when(parameter.resolvedAttributeName()).thenReturn(ATTRIBUTE);
        when(parameter.createNewInstance()).thenReturn(NEW_INSTANCE);
        when(handler.addToSession(ATTRIBUTE, NEW_INSTANCE)).thenReturn(NEW_INSTANCE);
        assertThat(resolver.resolveSessionAttributeInternal(handler, parameter), is(equalTo(NEW_INSTANCE)));
    }
}
