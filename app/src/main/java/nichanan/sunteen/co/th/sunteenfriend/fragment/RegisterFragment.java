package nichanan.sunteen.co.th.sunteenfriend.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import nichanan.sunteen.co.th.sunteenfriend.MainActivity;
import nichanan.sunteen.co.th.sunteenfriend.R;
import nichanan.sunteen.co.th.sunteenfriend.utinity.MyConstant;
import nichanan.sunteen.co.th.sunteenfriend.utinity.Myalertdialog;
import nichanan.sunteen.co.th.sunteenfriend.utinity.UploadDataToServer;

public class RegisterFragment extends Fragment {
    //    Explicit
    private Uri uri;
    private ImageView imageView;
    private boolean aBoolean = true;
    private  String imageString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Create toolbar
        createToolbar();

        pictureController();

    }//Main method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemUpload) {
            uploadAnupdateValue();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadAnupdateValue() {

        Myalertdialog myalertdialog = new Myalertdialog(getActivity());
        EditText nameEditText = getView().findViewById(R.id.editname);
        EditText userEditText = getView().findViewById(R.id.edtuser);
        EditText passEditText = getView().findViewById(R.id.edtpass);
//        Get Value to String

        String nameString = nameEditText.getText().toString().trim();
        String userString = userEditText.getText().toString().trim();
        String passString = passEditText.getText().toString().trim();

        //check choose image and space
        if (aBoolean) {

            myalertdialog.normalDialog("none choose image",
                    "pleas chose");


        } else if (nameString.isEmpty() || userString.isEmpty() || passString.isEmpty()) {
//         //   Have space
                    myalertdialog.normalDialog("have space",
                            "please fill all bank");
        }
             else {
//            No Space
            uploadImage();
            uploadText(nameString, userString, passString);
        }

    }//upload
private void  uploadText(String nameString, String userStraing, String passwordString){


        MyConstant myConstant = new MyConstant();


        imageString = myConstant.getUrlImage() + imageString;
        try {
            UploadDataToServer uploadDataToServer = new UploadDataToServer(getActivity());
            uploadDataToServer.execute(nameString, userStraing, passwordString, imageString, myConstant.getUrlAddUser());
            if (Boolean.parseBoolean(uploadDataToServer.get())) {
                Toast.makeText(getActivity(), "Success Register", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();

            }
                    else{
                    Toast.makeText(getActivity(), "Cannot Register", Toast.LENGTH_SHORT).show();
                }
//

        }catch (Exception e){
            e.printStackTrace();

        }

}
    private void uploadImage() {
//        find path Image
        String pathString =null;
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, strings, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathString = cursor.getString(index);


    }else{
            pathString = uri.getPath();

        }
        Log.d("8Julyv1", "Path ==>" + pathString);

//        find imageString
        imageString = pathString.substring(pathString.lastIndexOf("/"));
        Log.d("8Julyv1", "imageString ==>" + pathString);
//        Chang Policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Use Library ftp4j
        File file = new File(pathString);
        MyConstant myConstant = new MyConstant();
        FTPClient ftpClient = new FTPClient();

        try {
//            Event A
            ftpClient.connect(myConstant.getHostFTP(), myConstant.getPortFTP());
            ftpClient.login(myConstant.getUserFTP(), myConstant.getPasswordFTP());
            ftpClient.setType(FTPClient.TYPE_BINARY);
            ftpClient.changeDirectory("oil");
            ftpClient.upload(file, new uploadListener());

        }catch (Exception e){
            e.printStackTrace();
            try {
//                event b
                ftpClient.disconnect(true);
            }catch (Exception e1){
                e1.printStackTrace();
            }

//            Evaent B
        }




    }
    public class uploadListener implements FTPDataTransferListener{

        @Override
        public void started() {
            Toast.makeText(getActivity(), "start Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void transferred(int i) {
            Toast.makeText(getActivity(), "Process Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void completed() {
            Toast.makeText(getActivity(), "Complete Upload", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void aborted() {

        }

        @Override
        public void failed() {

        }
    }


    private void pictureController() {
        imageView = getView().findViewById(R.id.imvPicture);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "choose app"), 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == getActivity().RESULT_OK) {

            aBoolean = false;
//            success choose Image
            uri = data.getData();
//            Show Image_on ImageView

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getActivity(), "please Choose Image", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register, menu);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

//        Setup Toolbar
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.register));
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("fill Bill All blank");

//        Set up navigator icon
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
