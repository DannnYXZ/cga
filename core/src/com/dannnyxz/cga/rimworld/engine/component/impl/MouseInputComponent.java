package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MouseInputComponent extends Component {
    @Getter
    private int button;
    @Getter
    private int x;
    @Getter
    private int y;
}
