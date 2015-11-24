/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.management.rest.resource;

import io.gravitee.management.model.*;
import io.gravitee.management.rest.enhancer.ApplicationEnhancer;
import io.gravitee.management.service.ApplicationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Set;

/**
 * @author David BRASSELY (brasseld at gmail.com)
 */
@Path("/applications")
public class ApplicationsResource extends AbstractResource {

    @Context
    private ResourceContext resourceContext;

    @Inject
    private ApplicationService applicationService;

    @Inject
    private ApplicationEnhancer applicationEnhancer;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ApplicationEntity> list() {
        Set<ApplicationEntity> applications = applicationService.findByUser(getAuthenticatedUser());

        applications.forEach(api -> api = applicationEnhancer.enhance(getAuthenticatedUser()).apply(api));
        return applications;
    }

    /**
     * Create a new application for the authenticated user.
     *
     * @param application
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid final NewApplicationEntity application) {
        ApplicationEntity newApplication = applicationService.create(application, getAuthenticatedUser());
        if (newApplication != null) {
            newApplication = applicationEnhancer.enhance(getAuthenticatedUser()).apply(newApplication);
            return Response
                    .created(URI.create("/applications/" + newApplication.getId()))
                    .entity(newApplication)
                    .build();
        }

        return Response.serverError().build();
    }

    @Path("{application}")
    public ApplicationResource getApplicationResource() {
        return resourceContext.getResource(ApplicationResource.class);
    }
}