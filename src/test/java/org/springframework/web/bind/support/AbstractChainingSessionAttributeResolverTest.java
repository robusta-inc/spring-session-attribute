package org.springframework.web.bind.support;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.MissingServletRequestSessionAttributeException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AbstractChainingSessionAttributeResolverTest {
    public static final Object RESOLUTION = new Object();
    private AbstractChainingSessionAttributeResolver resolvingResolver;
    private AbstractChainingSessionAttributeResolver nonResolvingResolver;
    @Mock private SessionAttributeResolver next;
    @Mock private SessionHandler handler;
    @Mock private SessionAttributeParameter parameter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolvingResolver = new AbstractChainingSessionAttributeResolver(next) {
            @Override
            protected Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
                return RESOLUTION;
            }
        };
        nonResolvingResolver = new AbstractChainingSessionAttributeResolver(next) {
            @Override
            protected Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
                return null;
            }
        };
    }

    @Test
    public void testResolveSessionAttribute_whenAbleToResolve_shouldNotInvokeNextInChain_shouldReturnResolution() throws Exception {
        assertThat(resolvingResolver.resolveSessionAttribute(handler, parameter), is(equalTo(RESOLUTION)));
        verifyNoMoreInteractions(next, handler, parameter);
    }

    @Test
    public void testResolveSessionAttribute_whenUnableAbleToResolve_shouldInvokeNextInChain() throws Exception {
        assertThat(nonResolvingResolver.resolveSessionAttribute(handler, parameter), is(nullValue()));
        verify(next).resolveSessionAttribute(handler, parameter);
    }
}
