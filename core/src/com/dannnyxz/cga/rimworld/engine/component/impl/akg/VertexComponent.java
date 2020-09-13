package com.dannnyxz.cga.rimworld.engine.component.impl.akg;

import com.badlogic.gdx.math.Vector3;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class VertexComponent extends Component {
    @Getter @Setter
    private Vector3 v;

    @Getter @Setter
    private Vector3 d;
}
