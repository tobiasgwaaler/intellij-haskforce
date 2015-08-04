package com.haskforce.cabal.psi;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for Cabal value elements.
 */
public class CabalValueType extends CabalElementType {
    public CabalValueType(@NotNull @NonNls String debugName) {
        super(debugName);
    }
}
