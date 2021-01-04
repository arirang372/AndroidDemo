package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view.navigation;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceDefaultingMap;

import java.util.HashMap;
import java.util.Map;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_USERS;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_USER_SET_UP;

/**
 * User destinations that will be used in {@link com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view.AceUsersActivity}
 *
 * @author John Sung, Geico
 */
public class AceUserDestinations {
    public static final Map<String, Integer> DESTINATION_MAP = createDestinationMap();

    private static Map<String, Integer> createDestinationMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put(ACTION_USERS, R.id.users_fragment);
        map.put(ACTION_USER_SET_UP, R.id.user_settings_fragment);
        return AceDefaultingMap.withDefault(map, R.id.users_fragment);
    }
}
