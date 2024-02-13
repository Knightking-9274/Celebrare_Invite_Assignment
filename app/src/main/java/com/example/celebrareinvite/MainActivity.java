package com.example.celebrareinvite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class MainActivity extends AppCompatActivity {
    TextView txtView1, txtView2;


    ImageButton imgbtn;
    MaterialButton btnChoose;

    Uri uri;

    ImageView imgView1;

    Bitmap result,makeBitMap;

    boolean framClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView1 = findViewById(R.id.txtView1);
        txtView2 = findViewById(R.id.txtView2);
        btnChoose = findViewById(R.id.btnChoose);
        imgView1 = findViewById(R.id.imgVIew1);

//        Typeface custFont = Typeface.createFromAsset(getAssets(),"font/myfont.otf");
//        txtView1.setTypeface(custFont);
//        txtView2.setTypeface(custFont);
//        imgView1 = findViewById(R.id.imgVIew1);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MainActivity.this);


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){

                uri = result.getUri();
                showCustomDialog(uri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "Crop failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showCustomDialog(Uri imageUri) {
        // Create a custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);



        ImageView fragImgView = dialogView.findViewById(R.id.fragImageView);
        fragImgView.setImageURI(imageUri);


        AlertDialog dialog = builder.create();
        dialog.show();

        ImageButton btnImg = dialogView.findViewById(R.id.btnClose);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView imgView1 = dialogView.findViewById(R.id.fragImgView1);
        imgView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(!framClicked) {
                framClicked = true;
                fragImgView.setImageBitmap(maskingProcess(fragImgView, R.drawable.user_image_frame_1));
                imgView1.setEnabled(false);
            }


            }
        });
        ImageView imgView2 = dialogView.findViewById(R.id.fragImgView2);
        imgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!framClicked) {
                    framClicked = true;
                    fragImgView.setImageBitmap(maskingProcess(fragImgView, R.drawable.user_image_frame_2));
                    imgView2.setEnabled(false);
                }
            }
        });
        ImageView imgView3 = dialogView.findViewById(R.id.fragImgView3);
        imgView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!framClicked) {
                    framClicked = true;
                    fragImgView.setImageBitmap(maskingProcess(fragImgView, R.drawable.user_image_frame_3));
                    imgView3.setEnabled(false);
                }
            }
        });
        ImageView imgView4 = dialogView.findViewById(R.id.fragImgView3);
        imgView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!framClicked) {
                    framClicked = true;
                    fragImgView.setImageBitmap(maskingProcess(fragImgView, R.drawable.user_image_frame_4));
                    imgView4.setEnabled(false);
                }
            }
        });
        Button original = dialogView.findViewById(R.id.btnOriginal);
        original.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!framClicked) {
                    framClicked = true;
                    fragImgView.setImageURI(imageUri);
                    original.setEnabled(false);
                }

            }
        });
        Button useThisImage = dialogView.findViewById(R.id.btnUseImage);
        useThisImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });

    }
    private Bitmap maskingProcess(ImageView imageView, int maskResourceId) {
        try {
            Bitmap original, mask;
            original = getBitmapFromImageView(imageView);
            mask = BitmapFactory.decodeResource(getResources(), maskResourceId);
            if (original != null && mask != null) {
                int width = original.getWidth();
                int height = original.getHeight();

                Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Bitmap makeBitMap = Bitmap.createScaledBitmap(mask, width, height, true);

                Canvas canvas = new Canvas(result);

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

                canvas.drawBitmap(original, 0, 0, null);
                canvas.drawBitmap(makeBitMap, 0, 0, paint);

                paint.setXfermode(null);
                paint.setStyle(Paint.Style.STROKE);

                return result;
            }
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return null;
    }

    private Bitmap getBitmapFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmaps = null;

        if (drawable instanceof BitmapDrawable) {
            bitmaps = ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
            bitmaps = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmaps);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        return bitmaps;
    }

}