package com.zalando.utils.hamcrest;

import com.google.common.base.Joiner;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.BitSet;

public class CustomMatchers {

    private CustomMatchers() {
    }

    /**
     * Custom hamcrest matcher for BitSet List.
     * @param one The BitSet array that must be contained.
     * @return A BitSet array matcher.
     */
    public static Matcher<BitSet[]> bitSetContains(final BitSet[] one) {
        return new BaseMatcher<BitSet[]>() {
            @Override
            public boolean matches(final Object object) {
                if (object == one)
                    return true;
                BitSet[] other = (BitSet[]) object;
                return areEqual(one, other);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("BitSet List ")
                        .appendValue(bitSetListToString(one));
            }
        };
    }

    private static String bitSetListToString(BitSet[] bitSets) {
        return "<[" + Joiner.on(", ").useForNull("null").join(bitSets) + "]>";
    }

    private static boolean areEqual(BitSet[] oneArray, BitSet[] otherArray) {
        if (oneArray.length != otherArray.length)
            return false;
        for (int i = 0; i < oneArray.length; i++) {
            BitSet one = oneArray[i];
            BitSet other = otherArray[i];
            if(!areEqual(one, other)) {
                return false;
            }
        }
        return true;
    }

    private static boolean areEqual(BitSet one, BitSet other) {
        if (one == other)
            return true;
        if (one == null || other == null)
            return false;
        one = (BitSet) one.clone();
        one.xor(other);
        return one.cardinality() == 0;
    }
}
