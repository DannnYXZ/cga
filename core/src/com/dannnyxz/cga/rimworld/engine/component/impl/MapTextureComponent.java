package com.dannnyxz.cga.rimworld.engine.component.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MapTextureComponent extends Component {
    @Getter
    private TextureRegion texture;
    @Getter
    private String id;
}
