package com.zalando.paintshop.app;

import com.zalando.utils.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class PaintShopTest {

    private PaintShop paintShop;

    @Before
    public void before() throws Exception {
        paintShop = PaintShop.create();
    }

    @Test
    public void whenExecutePaintShopWithInputFromSpecification_thenOk() throws Exception {
        List<String> outputs = paintShop.execute(TestHelper.SUCCESS_FROM_SPEC_INPUT_FILE);
        assertThat(outputs, contains(TestHelper.OUTPUTS_FROM_SPEC.toArray()));
    }

    @Test
    public void whenExecutePaintShop_thenOk() throws Exception {
        List<String> outputs = paintShop.execute(TestHelper.SUCCESS_INPUT_FILE);
        assertThat(outputs, contains(TestHelper.OUTPUTS.toArray()));
    }
} 
