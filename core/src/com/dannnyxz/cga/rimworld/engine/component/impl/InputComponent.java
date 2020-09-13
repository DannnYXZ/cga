package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InputComponent extends Component {
    @Getter
    private int keyCode;
}
