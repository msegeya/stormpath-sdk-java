/*
 * Copyright 2014 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.sdk.impl.provider;

import com.stormpath.sdk.lang.Assert;
import com.stormpath.sdk.lang.Strings;
import com.stormpath.sdk.provider.GoogleAccountRequestBuilder;
import com.stormpath.sdk.provider.ProviderAccountRequest;

import java.util.Map;

/**
 * @since 1.0.beta
 */
public class DefaultGoogleAccountRequestBuilder extends AbstractProviderAccountRequestBuilder<GoogleAccountRequestBuilder> implements GoogleAccountRequestBuilder {

    private String code;

    @Override
    public GoogleAccountRequestBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    protected String getConcreteProviderId() {
        return IdentityProviderType.GOOGLE.getNameKey();
    }

    @Override
    protected ProviderAccountRequest doBuild(Map<String, Object> map) {
        Assert.state(Strings.hasText(this.code) ^ Strings.hasText(super.accessToken), "Either 'code' or 'accessToken' properties must exist in a Google account request.");

        DefaultGoogleProviderData providerData = new DefaultGoogleProviderData(null, map);

        if(this.accessToken != null) {
            providerData.setAccessToken(super.accessToken);
        } else {
            providerData.setCode(this.code);
        }

        return new DefaultProviderAccountRequest(providerData);
    }

}
