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

package org.wso2.carbon.identity.agent.onprem.userstore.constant;

/**
 * Constants common to all userstores.
 */
public class CommonConstants {
    public static final String AGENT_BASE_URL = "/wso2agent";
    public static final String PROPERTY_REPLACE_ESCAPE_CHARACTERS_AT_USER_LOGIN = "ReplaceEscapeCharactersAtUserLogin";
    public static final String PROPERTY_MAX_USER_LIST = "MaxUserNameListLength";
    public static final String PROPERTY_MAX_SEARCH_TIME = "MaxSearchQueryTime";
    public static final String PROPERTY_MAX_ROLE_LIST = "MaxRoleNameListLength";
    public static final String PROPERTY_USER_DN_CACHE_ENABLED = "UserDNCacheEnabled";
    public static final String XML_PATTERN_SEPERATOR = "#";
    public static final String ATTRIBUTE_LIST_SEPERATOR = ",";
    public static final String WILD_CARD_FILTER = "*";
    public static final String CARBON_HOME = "carbon.home";
    public static final int MAX_USER_LIST = 100;
    public static final int MAX_SEARCH_TIME = 10000;   // ms
    public static final long MAX_USER_DN_CACHE_TIMEOUT = 30; //minutes
    public static final long MAX_USER_DN_CACHE_SIZE = 10000;
}
