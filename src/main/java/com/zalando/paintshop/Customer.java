package com.zalando.paintshop;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.BitSet;

/**
 * Holds customer glossy and matte preferences.
 */
public class Customer {
    private final BitSet glossies;
    private Integer matte = null;

    public Customer(int numColors) {
        glossies = new BitSet(numColors);
    }

    /**
     * Set the correspondent color index to glossy in the user preferences.
     * @param bitIndex index of the bit to set to glossy.
     */
    public void setGlossy(int bitIndex) {
        glossies.set(bitIndex);
    }

    /**
     * Set the correspondent color index to matte in the user preferences.
     * A user can only choose a single matte color.
     * @param bitIndex index of the bit to set to mate.
     */
    public void setMatte(int bitIndex) {
        matte = bitIndex;
    }

    public int getMatte() {
        return matte;
    }

    public boolean hasMatte() {
        return matte != null;
    }

    /**
     * Checks if the input batches satisfies the user preferences.
     * @param batches batches of colors satisfying user preferences.
     * @return true if the batches is satisfactory, false otherwise.
     */
    public boolean isBatchesSatisfactory(BitSet batches) {
        return satisfiedColors(batches) > 0;
    }

    private int satisfiedColors(BitSet batches) {
        int satisfiedColors = satisfiedGlossyColors(batches);
        if (isMatteColorSatisfied(batches)) satisfiedColors++;
        return satisfiedColors;
    }

    private int satisfiedGlossyColors(BitSet batches) {
        BitSet satisfiedColorBits = (BitSet) batches.clone();
        satisfiedColorBits.and(glossies);
        satisfiedColorBits.xor(glossies);
        return satisfiedColorBits.cardinality();
    }

    private boolean isMatteColorSatisfied(BitSet batches) {
        return matte != null && batches.get(matte);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;
        final Customer other = (Customer) object;
        return Objects.equal(this.glossies, other.glossies)
                && Objects.equal(this.matte, other.matte);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("glossies", glossies)
                .add("matte", matte)
                .toString();

    }
}
