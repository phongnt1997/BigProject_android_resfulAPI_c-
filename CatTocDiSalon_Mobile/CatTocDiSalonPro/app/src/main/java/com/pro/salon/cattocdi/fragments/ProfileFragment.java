package com.pro.salon.cattocdi.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.ReviewProfileActivity;
import com.pro.salon.cattocdi.adapter.ProfileTabAdapter;
import com.pro.salon.cattocdi.models.FirebaseModel;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.FirebaseClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.AlertError;
import com.pro.salon.cattocdi.utils.MyContants;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    private TextView tvPreview;
    private boolean isAtEditPage = true;
    private TextView tvTitle;
    private ImageView icFavorite;
    private Salon salon;
    private TextView tvNameSalon;
    private ImageView imageView, icUpload;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private FirebaseAuth mAuth;
    private UploadTask.TaskSnapshot downloadUri;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ProfileFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.detail_pager);
        tvNameSalon = view.findViewById(R.id.fg_name_salon);

        tabLayout = (TabLayout) view.findViewById(R.id.detail_tab_layout);
        loadSalon();

        tvTitle = view.findViewById(R.id.fg_profile_title_tv);
        imageView = view.findViewById(R.id.header_cover_image);
        mAuth = FirebaseAuth.getInstance();

        icUpload = view.findViewById(R.id.upload_photo);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        icUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImg();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        FirebaseUser currentuser = mAuth.getCurrentUser();
        mAuth.signInAnonymously()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("Success Asign", "Sucess");
                        }
                    }
                });
        super.onStart();
    }


    private void loadSalon(){
        ApiClient.getInstance()
                .create(SalonClient.class)
                .getSalonProfile("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<Salon>() {
                    @Override
                    public void onResponse(Call<Salon> call, Response<Salon> response) {
                        if (response.code() == 200){
                           salon = response.body();
                           tvNameSalon.setText(salon.getName());
                            Picasso.get().load(salon.getImageUrl()).into(imageView);
                            ProfileTabAdapter adapter = new ProfileTabAdapter(getChildFragmentManager(), false, salon);
                            viewPager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(viewPager);
                        }
                        else
                        {
                            AlertError.showDialogLoginFail(getContext(), "Có lỗi xảy ra vui lòng xem lại kết nối");
                            Log.d("FAILED","Failed 200");
                        }
                    }

                    @Override
                    public void onFailure(Call<Salon> call, Throwable t) {
                        Log.d("FAILED","Failed");
                    }
                });
    }

    private void changeToEdit(){
        isAtEditPage = true;
        tvPreview.setText("Preview");
        tvTitle.setText("Chỉnh sửa thông tin");

    }
    private void changeToPreiview(){
        isAtEditPage = false;
        tvPreview.setText("Sửa");
        tvTitle.setText("Xem trước thông tin");

    }
    private void chooseImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                uploadImage();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUri = uri;
                                    Log.d(salon.getName(), uri.getPath().toString());
                                    saveImage(uri.getPath().toString());
                                   /*Uri url = taskSnapshot.getDownloadUrl();
                                   Log.d(salon.getName(), url.toString());*/

                                }
                            });
//                            Log.d(salon.getName(), taskSnapshot.getMetadata().getReference().getDownloadUrl().getResult().getPath().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                //Log.d(salon.getName(), task.getResult().getMetadata().getReference().getDownloadUrl().getResult().getPath());
                            /*   downloadUri = task.getResult();
                                if (downloadUri == null)
                                    return ;*/
                            }
                        }
                    });

        }
    }

    private void saveImage(String path){
        final String filename = path.substring(path.lastIndexOf("/") + 1);
        final String url = "https://firebasestorage.googleapis.com/v0/b/cattocdipro.appspot.com/o/"  ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(FirebaseClient.class)
                .getToken(filename)
                .enqueue(new Callback<FirebaseModel>() {
                    @Override
                    public void onResponse(Call<FirebaseModel> call, Response<FirebaseModel> response) {

                        String realPath = url + filename + "?alt=media&token=" + response.body().getToken();
                        ApiClient.getInstance().create(SalonClient.class)
                                .updateImage("Bearer " + MyContants.TOKEN, realPath)
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if(response.code() == 200){
                                            Log.d("SaveImage", "Success");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        AlertError.showDialogLoginFail(getActivity(), "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                                    }
                                });



                    }

                    @Override
                    public void onFailure(Call<FirebaseModel> call, Throwable t) {
                        AlertError.showDialogLoginFail(getActivity(), "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                    }
                });
    }

}
