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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DefaultSessionAttributeResolverChainTest {
    private DefaultSessionAttributeResolverChain resolverChain;
    @Mock private SessionAttributeResolver mockedResolverChain;
    @Mock private SessionHandler handler;
    @Mock private SessionAttributeParameter parameter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolverChain = new DefaultSessionAttributeResolverChain(mockedResolverChain);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatWhenInitializingWithAnInvalidResolver_shouldThrowException() throws Exception {
        new DefaultSessionAttributeResolverChain(null);
    }

    @Test
    public void testResolveSessionAttribute_shouldDelegateToTheInternalHandlerChain() throws Exception {
        resolverChain.resolveSessionAttribute(handler, parameter);
        Mockito.verify(mockedResolverChain).resolveSessionAttribute(handler, parameter);
    }
}
