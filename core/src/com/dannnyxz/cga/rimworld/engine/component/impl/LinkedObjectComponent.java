package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LinkedObjectComponent extends Component {
    @Getter
    private final TextureRegion[][] regions;
    @Getter
    private final String type;
    @Getter @Setter
    private int index;
}
