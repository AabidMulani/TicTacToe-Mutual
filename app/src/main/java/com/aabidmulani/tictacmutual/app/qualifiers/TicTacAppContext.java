package com.aabidmulani.tictacmutual.app.qualifiers;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface TicTacAppContext {
}
