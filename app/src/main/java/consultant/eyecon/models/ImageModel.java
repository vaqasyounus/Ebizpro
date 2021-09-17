package consultant.eyecon.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Muhammad on 16-Apr-2017.
 */

public class ImageModel {
    ImageView imageView;
    byte[] bytes;

    public ImageModel(ImageView imageView, byte[] bitmap) {
        this.imageView = imageView;
        this.bytes = bitmap;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public byte[] getBitmap() {
        return bytes;
    }

    public void setBitmap(byte[] bitmap) {
        this.bytes = bitmap;
    }
}
