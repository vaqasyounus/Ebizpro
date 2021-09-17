package consultant.eyecon.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import consultant.eyecon.models.ImageModel;
import id.zelory.compressor.Compressor;

/**
 * Created by Muhammad on 16-Apr-2017.
 */

public class ImageDownloaderTask extends AsyncTask<Void, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private Context context;
    private byte[] imgdata;

    public ImageDownloaderTask(ImageModel imageModel, Context context) {
        imageViewReference = new WeakReference<ImageView>(imageModel.getImageView());
        this.context = context;
        this.imgdata = imageModel.getBitmap();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        FileOutputStream fos = null;
        if (imgdata != null) {
            File f = new File(context.getCacheDir(), "image");
            try {
                f.createNewFile();
                fos = new FileOutputStream(f);
                fos.write(imgdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap compressedImageBitmap = new Compressor.Builder(context)
                    .setMaxWidth(500)
                    .setMaxHeight(500)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .build()
                    .compressToBitmap(f);
            ;
            return compressedImageBitmap;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
