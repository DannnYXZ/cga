package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class TargetComponent extends Component {
    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;
}
