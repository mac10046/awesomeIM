package com.sls.awesomeim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sls.awesomeim.web.rest.TestUtil;

public class DocDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocDetails.class);
        DocDetails docDetails1 = new DocDetails();
        docDetails1.setId(1L);
        DocDetails docDetails2 = new DocDetails();
        docDetails2.setId(docDetails1.getId());
        assertThat(docDetails1).isEqualTo(docDetails2);
        docDetails2.setId(2L);
        assertThat(docDetails1).isNotEqualTo(docDetails2);
        docDetails1.setId(null);
        assertThat(docDetails1).isNotEqualTo(docDetails2);
    }
}
