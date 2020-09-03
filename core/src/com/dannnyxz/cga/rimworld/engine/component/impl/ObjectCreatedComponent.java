package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ObjectCreatedComponent extends Component {
    @Getter
    private final long id;
}
