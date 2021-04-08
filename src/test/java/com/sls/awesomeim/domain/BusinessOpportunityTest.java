package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class BusinessOpportunityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessOpportunity.class);
        BusinessOpportunity businessOpportunity1 = new BusinessOpportunity();
        businessOpportunity1.setId(1L);
        BusinessOpportunity businessOpportunity2 = new BusinessOpportunity();
        businessOpportunity2.setId(businessOpportunity1.getId());
        assertThat(businessOpportunity1).isEqualTo(businessOpportunity2);
        businessOpportunity2.setId(2L);
        assertThat(businessOpportunity1).isNotEqualTo(businessOpportunity2);
        businessOpportunity1.setId(null);
        assertThat(businessOpportunity1).isNotEqualTo(businessOpportunity2);
    }
}
