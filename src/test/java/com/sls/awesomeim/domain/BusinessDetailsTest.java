package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class BusinessDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessDetails.class);
        BusinessDetails businessDetails1 = new BusinessDetails();
        businessDetails1.setId(1L);
        BusinessDetails businessDetails2 = new BusinessDetails();
        businessDetails2.setId(businessDetails1.getId());
        assertThat(businessDetails1).isEqualTo(businessDetails2);
        businessDetails2.setId(2L);
        assertThat(businessDetails1).isNotEqualTo(businessDetails2);
        businessDetails1.setId(null);
        assertThat(businessDetails1).isNotEqualTo(businessDetails2);
    }
}
