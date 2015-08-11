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
package io.gravitee.management.api.exceptions;

import javax.ws.rs.core.Response;

/**
 * @author David BRASSELY (brasseld at gmail.com)
 */
public class UsernameAlreadyExistsException extends AbstractManagementException {

    private final String username;

    public UsernameAlreadyExistsException(String username) {
        this.username = username;
    }

    @Override
    public Response.Status getHttpStatusCode() {
        return Response.Status.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "A user with username " + username + " already exists.";
    }
}
