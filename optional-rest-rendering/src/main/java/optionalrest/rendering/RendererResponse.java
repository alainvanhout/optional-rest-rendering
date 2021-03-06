package optionalrest.rendering;

import optionalrest.core.response.BasicResponse;
import renderering.core.Renderer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RendererResponse extends BasicResponse {

    private Renderer renderer;

    private String render() {
        if (renderer == null){
            return "";
        }
        return renderer.render();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public RendererResponse renderer(Renderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public RendererResponse setRenderer(Renderer renderer) {
        this.renderer = renderer;
        return this;
    }

    @Override
    public InputStream toStream() {
        return new ByteArrayInputStream(render().getBytes());
    }
}
