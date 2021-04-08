package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class ShippersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shippers.class);
        Shippers shippers1 = new Shippers();
        shippers1.setId(1L);
        Shippers shippers2 = new Shippers();
        shippers2.setId(shippers1.getId());
        assertThat(shippers1).isEqualTo(shippers2);
        shippers2.setId(2L);
        assertThat(shippers1).isNotEqualTo(shippers2);
        shippers1.setId(null);
        assertThat(shippers1).isNotEqualTo(shippers2);
    }
}
