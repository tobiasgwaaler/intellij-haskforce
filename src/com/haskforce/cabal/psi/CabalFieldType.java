package com.haskforce.cabal.psi;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for Cabal field elements.
 */
public class CabalFieldType extends CabalElementType {
    public CabalFieldType(@NotNull @NonNls String debugName) {
        super(debugName);
    }
}
