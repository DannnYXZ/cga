package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class TextureComponent extends Component {
    @Getter @Setter
    private Sprite sprite;
    @Getter @Setter
    private float x;
    @Getter @Setter
    private float y;
    @Getter
    private final float width;
    @Getter
    private final float height;
}
