package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class CouponsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coupons.class);
        Coupons coupons1 = new Coupons();
        coupons1.setId(1L);
        Coupons coupons2 = new Coupons();
        coupons2.setId(coupons1.getId());
        assertThat(coupons1).isEqualTo(coupons2);
        coupons2.setId(2L);
        assertThat(coupons1).isNotEqualTo(coupons2);
        coupons1.setId(null);
        assertThat(coupons1).isNotEqualTo(coupons2);
    }
}
