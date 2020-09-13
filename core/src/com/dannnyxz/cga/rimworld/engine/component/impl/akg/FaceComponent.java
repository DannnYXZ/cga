package com.dannnyxz.cga.rimworld.engine.component.impl.akg;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class FaceComponent extends Component {
    @Getter @Setter
    private Entity x;

    @Getter @Setter
    private Entity y;

    @Getter @Setter
    private Entity z;

}
