/*
 * Copyright 2015-2017 the original author or authors.
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

package org.hawaiiframework.web.resource;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Abstract {@link ResourceAssembler} implementation.
 *
 * @param <S> the type of the object to convert
 * @param <T> the type of the resource
 * @author Marcel Overdijk
 * @author Rutger Lubbers
 * @author Paul Klos
 * @since 2.0.0
 */
public abstract class AbstractResourceAssembler<S, T> implements ResourceAssembler<S, T> {

    /**
     * The resource type.
     */
    private final Class<T> resourceType;

    /**
     * Constructs a {@link AbstractResourceAssembler}.
     *
     * @param resourceType the resource type
     */
    public AbstractResourceAssembler(final Class<T> resourceType) {
        this.resourceType = requireNonNull(resourceType, "'resourceType' must not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T toResource(final S object) {
        if (object == null) {
            return null;
        }
        final T target = instantiateResource(object);
        toResource(object, target);
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> toResources(final Iterable<? extends S> objects) {
        requireNonNull(objects, "'objects' must not be null");
        final List<T> result = new ArrayList<T>();
        for (final S object : objects) {
            result.add(toResource(object));
        }
        return result;
    }

    /**
     * Instantiates the resource.
     *
     * @param object the object
     * @return the resource
     */
    protected T instantiateResource(final S object) {
        return BeanUtils.instantiateClass(resourceType);
    }
}