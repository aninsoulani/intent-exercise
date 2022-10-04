package id.ac.polinema.intentexercise;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameInput, emailInput, webInput, aboutInput;
    private CircleImageView image;
    private ImageButton tambahfoto;
    private Button kirimBtn;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    public static final String FOTO_KEY = "foto";
    public static final String WEB_KEY = "web";
    public static final String ABOUT_KEY = "about";

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        image = findViewById(R.id.image_profile);
        nameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        webInput = findViewById(R.id.text_website);
        aboutInput = findViewById(R.id.text_about);
        kirimBtn = findViewById(R.id.button_ok);

        tambahfoto = findViewById(R.id.imagetambah);
        tambahfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
                /*boolean pick=true;
                if(pick){
                    if (!checkCameraPermission()){
                        requestCameraPermission();

                    }else PickImage();

                }else{
                    if (!checkStoragePermission()){
                        requestStoragePermission();

                    }else PickImage();
                }*/
            }
        });


        kirimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String about = aboutInput.getText().toString();
                String web = webInput.getText().toString();

                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                intent.putExtra(FOTO_KEY, imageUri.toString());
                intent.putExtra(NAME_KEY, name);
                intent.putExtra(EMAIL_KEY, email);
                intent.putExtra(ABOUT_KEY, about);
                intent.putExtra(WEB_KEY, web);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null){
                try {
                    imageUri = data.getData();
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    image.setImageBitmap(bmp);
                }
                catch (IOException e){
                    Toast.makeText(this, "gabisa ke load", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
    /*
    private void PickImage() {
        CropImage.activity().start(this);
    }

    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

    private boolean checkStoragePermission() {
        boolean res2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private boolean checkCameraPermission() {
        boolean res1= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean res2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.get().load(resultUri).fit().into(image);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }*/
