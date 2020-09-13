package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class FloatPositionComponent extends Component {
    @Getter
    @Setter
    private float x;

    @Getter @Setter
    private float y;
}
