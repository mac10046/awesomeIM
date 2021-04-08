package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class TaxesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taxes.class);
        Taxes taxes1 = new Taxes();
        taxes1.setId(1L);
        Taxes taxes2 = new Taxes();
        taxes2.setId(taxes1.getId());
        assertThat(taxes1).isEqualTo(taxes2);
        taxes2.setId(2L);
        assertThat(taxes1).isNotEqualTo(taxes2);
        taxes1.setId(null);
        assertThat(taxes1).isNotEqualTo(taxes2);
    }
}
