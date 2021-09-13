package com.miu.pasteit.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Rimon Mostafiz
 */
class UrlHelperTest {

    @Test
    public void testAll() {
        String all = UrlHelper.all("");
        String allUrl = UrlHelper.all("url");
        Assertions.assertThat(all).isNotNull();
        Assertions.assertThat(all).contains("/");
        Assertions.assertThat(allUrl).contains("url/**");
    }

}