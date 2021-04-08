package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class QuotesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quotes.class);
        Quotes quotes1 = new Quotes();
        quotes1.setId(1L);
        Quotes quotes2 = new Quotes();
        quotes2.setId(quotes1.getId());
        assertThat(quotes1).isEqualTo(quotes2);
        quotes2.setId(2L);
        assertThat(quotes1).isNotEqualTo(quotes2);
        quotes1.setId(null);
        assertThat(quotes1).isNotEqualTo(quotes2);
    }
}
