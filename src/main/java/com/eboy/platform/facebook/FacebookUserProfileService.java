package com.eboy.platform.facebook;

import com.eboy.platform.UserProfileService;
import com.eboy.platform.facebook.update.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FacebookUserProfileService implements UserProfileService {

    private final static Logger logger = Logger.getLogger(FacebookUserProfileService.class.getName());

    private final String PAGE_ACCESS_TOKEN = "EAAPfk4CcikoBAOj4pffNbiBQBjq9WAQq9y1kFEnp1RsmsqPrpJWS8AdQhqvXFk57RhiWOgp9BRRyJnmW85CZCozcvh8P2sM8914OhtGqc8nsB1WiF2J2jrqBcd887ApiHhrcLhyFN24rLrKVjJR3phcpfmDFcWnUC1pGpVgZDZD";
    private final String FB_USER_PROFILE_URL = "https://graph.facebook.com/v2.6/<USER_ID>?fields=first_name,last_name,profile_pic&access_token=" + PAGE_ACCESS_TOKEN;
    private final String USER_ID_PATTERN = "<USER_ID>";

    RestTemplate restTemplate;

    @Autowired
    public FacebookUserProfileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getUserName(String userId) {
        Assert.notNull(userId);

        Optional<UserProfile> userProfile = Optional.ofNullable(this.getUserProfileForId(userId));

        return userProfile.isPresent() ? userProfile.get().getFirstName() : null;
    }

    private UserProfile getUserProfileForId(String userId) {

        String url = FB_USER_PROFILE_URL.replace(USER_ID_PATTERN, userId);

        ResponseEntity<UserProfile> entity = restTemplate.getForEntity(url, UserProfile.class);

        if (entity != null && entity.getBody() != null) {
            return entity.getBody();
        } else {
            logger.warning("Couldn't receive user profile for id: " + userId);
            return null;
        }

    }
}
