package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class BusinessOfferTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessOffer.class);
        BusinessOffer businessOffer1 = new BusinessOffer();
        businessOffer1.setId(1L);
        BusinessOffer businessOffer2 = new BusinessOffer();
        businessOffer2.setId(businessOffer1.getId());
        assertThat(businessOffer1).isEqualTo(businessOffer2);
        businessOffer2.setId(2L);
        assertThat(businessOffer1).isNotEqualTo(businessOffer2);
        businessOffer1.setId(null);
        assertThat(businessOffer1).isNotEqualTo(businessOffer2);
    }
}
