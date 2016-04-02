package com.zalando.paintshop.app;

import com.google.common.base.Joiner;
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
        paintShop = new PaintShop();
    }

    @Test
    public void whenExecutePaintShopWithInputFromSpecification_thenOk() throws Exception {
        List<String> outputs = paintShop.execute(TestHelper.SUCCESS_FROM_SPEC_INPUT_FILE);
        assertThat(outputs, contains(TestHelper.OUTPUTS_FROM_SPEC.toArray()));
    }

    @Test
    public void whenExecutePaintShop_thenOk() throws Exception {
        List<String> outputs = paintShop.execute(TestHelper.SUCCESS_INPUT_FILE);
        System.out.println(Joiner.on("\n").join(outputs));
        assertThat(outputs, contains(TestHelper.OUTPUTS.toArray()));
    }
} 
