package org.lpw.photon.util;

import org.springframework.stereotype.Component;

@Component("photon.util.mask")
public class MaskImpl implements Mask {
    @Override
    public String mobile(String mobile) {
        if (mobile == null || mobile.length() < 4)
            return null;

        return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
    }
}
