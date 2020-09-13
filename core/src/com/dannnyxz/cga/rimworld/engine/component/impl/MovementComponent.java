package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MovementComponent extends Component {
    @Getter @Setter
    private int deltaX;
    @Getter @Setter
    private int deltaY;
    @Getter @Setter
    private float delta;
}
