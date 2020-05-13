package com.avengers.ironman.largeimage.aop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.avengers.ironman.IronMan;
import com.avengers.ironman.largeimage.LargeImageManager;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.request.Postprocessor;

import static com.facebook.imagepipeline.request.BasePostprocessor.FALLBACK_BITMAP_CONFIGURATION;

public class FrescoPostprocessor implements Postprocessor {

    @Nullable
    private Postprocessor mOriginalPostprocessor;
    private Uri mUri;

    public FrescoPostprocessor(Uri uri, Postprocessor postprocessor) {
        this.mOriginalPostprocessor = postprocessor;
        this.mUri = uri;
    }

    @Override
    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        try {
            if (IronMan.get().largeImageConfig().isLargeImgOpen()) {
                LargeImageManager.getInstance().transform(mUri.toString(), sourceBitmap, "Fresco");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mOriginalPostprocessor != null) {
            return mOriginalPostprocessor.process(sourceBitmap, bitmapFactory);
        }

        final Bitmap.Config sourceBitmapConfig = sourceBitmap.getConfig();
        CloseableReference<Bitmap> destBitmapRef =
                bitmapFactory.createBitmapInternal(
                        sourceBitmap.getWidth(),
                        sourceBitmap.getHeight(),
                        sourceBitmapConfig != null ? sourceBitmapConfig : FALLBACK_BITMAP_CONFIGURATION);
        try {
            process(destBitmapRef.get(), sourceBitmap);
            return CloseableReference.cloneOrNull(destBitmapRef);
        } finally {
            CloseableReference.closeSafely(destBitmapRef);
        }
    }

    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        internalCopyBitmap(destBitmap, sourceBitmap);
        process(destBitmap);
    }

    public void process(Bitmap bitmap) {
    }

    /**
     * Copies the content of {@code sourceBitmap} to {@code destBitmap}. Both bitmaps must have the
     * same width and height. If their {@link Bitmap.Config} are identical, the memory is directly
     * copied. Otherwise, the {@code sourceBitmap} is drawn into {@code destBitmap}.
     */
    private static void internalCopyBitmap(Bitmap destBitmap, Bitmap sourceBitmap) {
        if (destBitmap.getConfig() == sourceBitmap.getConfig()) {
            Bitmaps.copyBitmap(destBitmap, sourceBitmap);
        } else {
            // The bitmap configurations might be different when the source bitmap's configuration is
            // null, because it uses an internal configuration and the destination bitmap's configuration
            // is the FALLBACK_BITMAP_CONFIGURATION. This is the case for static images for animated GIFs.
            Canvas canvas = new Canvas(destBitmap);
            canvas.drawBitmap(sourceBitmap, 0, 0, null);
        }
    }

    @Override
    public String getName() {
        if (mOriginalPostprocessor != null) {
            return mOriginalPostprocessor.getName();
        }
        return "IronMan&Fresco&FrescoPostprocessor";
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        if (mOriginalPostprocessor != null) {
            return mOriginalPostprocessor.getPostprocessorCacheKey();
        }
        return null;
    }
}
