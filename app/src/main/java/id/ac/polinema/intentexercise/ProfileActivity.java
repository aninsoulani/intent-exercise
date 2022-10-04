package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameText;
    private TextView emailText;
    private TextView aboutText;
    private TextView webText;
    private Uri imageUri;

    Button bukalink;
    String defaultLink;

    ImageView tampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameText=findViewById(R.id.label_name);
        emailText=findViewById(R.id.label_email);
        aboutText=findViewById(R.id.label_about);
        webText=findViewById(R.id.label_website);
        tampil=findViewById(R.id.image_kedua);
        bukalink=findViewById(R.id.button_buka);

        defaultLink = "http://";

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String destinasi = extras.getString("foto");
            Uri fileUri = Uri.parse(destinasi);
            tampil.setImageURI(fileUri);
            nameText.setText(extras.getString("name"));
            emailText.setText(extras.getString("email"));
            aboutText.setText(extras.getString("about"));
            webText.setText(extras.getString("web"));
        }
        else {
            Toast.makeText(this, "Intent is missing!",Toast.LENGTH_SHORT).show();
        }

        bukalink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String page = webText.getText().toString();
                if (!TextUtils.isEmpty(page)){
                    Uri uri = Uri.parse(defaultLink+page);
                    Intent buka = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(buka);
                }else {
                    Toast.makeText(ProfileActivity.this, "Please enter website link!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
