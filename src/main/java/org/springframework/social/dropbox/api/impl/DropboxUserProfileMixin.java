package org.springframework.social.dropbox.api.impl;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.dropbox.api.DropboxUserProfile;

import java.io.IOException;
import java.math.BigInteger;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:07 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DropboxUserProfileMixin.DropboxUserProfileDeserializer.class)
public class DropboxUserProfileMixin {
    static class DropboxUserProfileDeserializer extends JsonDeserializer<DropboxUserProfile>{
        @Override
        public DropboxUserProfile deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode tree = jp.readValueAsTree();

            String country = tree.get("country").getValueAsText();
            String displayName = tree.get("display_name").getValueAsText();
            String uid = tree.get("uid").getTextValue();

            JsonNode quotaNode = tree.get("quota_info");
            BigInteger sharedQuota = quotaNode.get("shared").getBigIntegerValue();
            BigInteger quota = quotaNode.get("quota").getBigIntegerValue();
            BigInteger normalQuota = quotaNode.get("normal").getBigIntegerValue();

            return new DropboxUserProfile(country, displayName,  uid, sharedQuota,  quota,  normalQuota);
        }
    }
}
