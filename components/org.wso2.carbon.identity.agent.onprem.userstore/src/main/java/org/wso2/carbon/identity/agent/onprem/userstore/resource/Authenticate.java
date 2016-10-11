/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.agent.onprem.userstore.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.agent.onprem.userstore.constant.CommonConstants;
import org.wso2.carbon.identity.agent.onprem.userstore.exception.UserStoreException;
import org.wso2.carbon.identity.agent.onprem.userstore.manager.common.UserStoreManager;
import org.wso2.carbon.identity.agent.onprem.userstore.manager.common.UserStoreManagerBuilder;
import org.wso2.carbon.identity.agent.onprem.userstore.model.User;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * REST endpoint for authentication.
 * This will be available at https://localhost:8888/authenticate
 */
@Api(value = CommonConstants.AGENT_BASE_URL + "authenticate")
@SwaggerDefinition(
        info = @Info(
                title = "Authentication Endpoint Swagger Definition", version = "1.0",
                description = "The endpoint which is used to authenticate users in on premise userstores.",
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0")
                )
)
@Path(CommonConstants.AGENT_BASE_URL + "/authenticate")
public class Authenticate {
    private static Logger log = LoggerFactory.getLogger(Authenticate.class);

    /**
     * @param user - user object with username and password being set.
     * @return - true if the user is authenticated.
     * - false otherwise.
     */
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        @ApiOperation(
                value = "Return whether the user is authenticated or not",
                notes = "Returns HTTP 500 if an internal error occurs at the server")

        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "{authenticated:true}"),
                @ApiResponse(code = 500, message = "Particular exception message")})
        public Response authenticate(User user) {
            try {
                Boolean isAuthenticated;
                Map<String , Boolean> returnMap = new HashMap<>();
                UserStoreManager userStoreManager = UserStoreManagerBuilder.getUserStoreManager();
                isAuthenticated = userStoreManager.doAuthenticate(user.getUsername(), user.getPassword());
                returnMap.put("authenticated", isAuthenticated);
                return Response.status(Response.Status.OK).entity(new JSONObject(returnMap).toString()).build();
            } catch (UserStoreException e) {
                log.error(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }

        }
}
